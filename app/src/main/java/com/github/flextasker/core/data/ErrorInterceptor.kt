package com.github.flextasker.core.data

import com.github.flextasker.core.api.model.ErrorBody
import com.github.flextasker.core.domain.exception.ApiException
import com.github.flextasker.core.domain.exception.UnauthorizedException
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ErrorInterceptor @Inject constructor(
    private val gson: Gson
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.isSuccessful)
            return response

        if (response.code == 401)
            throw UnauthorizedException()

        val body: ErrorBody? = try {
            gson.fromJson(response.body?.string(), ErrorBody::class.java)
        } catch (e: Exception) {
            null
        }

        if (body != null)
            throw ApiException(body.errorText)

        return response
    }
}