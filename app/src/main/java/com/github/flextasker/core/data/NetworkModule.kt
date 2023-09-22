package com.github.flextasker.core.data

import com.github.flextasker.BuildConfig
import com.github.flextasker.core.api.model.ErrorBody
import com.github.flextasker.core.domain.AccountInfo
import com.github.flextasker.core.domain.exception.ApiException
import com.github.flextasker.core.domain.exception.UnauthorizedException
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .create()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp").d(message)
        }.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Named(ERROR_INTERCEPTOR)
    fun provideErrorInterceptor(gson: Gson): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            if (response.isSuccessful)
                return@Interceptor response

            if (response.code == 401)
                throw UnauthorizedException()

            val body: ErrorBody? = try {
                gson.fromJson(response.body?.string(), ErrorBody::class.java)
            } catch (e: Exception) {
                null
            }

            if (body != null)
                throw ApiException(body.errorText)

            response
        }
    }

    @Provides
    @Named(AUTH_INTERCEPTOR)
    fun provideAuthInterceptor(accountInfo: AccountInfo): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .let {
                    val token = accountInfo.token
                    if (token.isNotEmpty())
                        it.header("Authorization", "Bearer $token")
                    else it
                }
                .method(original.method, original.body)
                .build()

            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @Named(AUTH_INTERCEPTOR) authInterceptor: Interceptor,
        errorInterceptor: ErrorInterceptor,
    ): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .apply {
                val naiveTrustManager = object : X509TrustManager {
                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                    override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
                    override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
                }

                val insecureSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
                    val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
                    init(null, trustAllCerts, SecureRandom())
                }.socketFactory

                sslSocketFactory(insecureSocketFactory, naiveTrustManager)
                hostnameVerifier { _, _ -> true }
            }
            .addInterceptor(authInterceptor)
            .addInterceptor(errorInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        gson: Gson,
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BuildConfig.API_BASE_URL)
        .client(client)
        .build()

    companion object {
        private const val AUTH_INTERCEPTOR = "AUTH_INTERCEPTOR"
        private const val ERROR_INTERCEPTOR = "ERROR_INTERCEPTOR"
    }
}