package com.github.flextasker.core.api

import com.github.flextasker.core.api.model.NetworkTask
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApi {

    @POST("getTasks")
    suspend fun getTasks(): List<NetworkTask>

    @POST("createTask")
    suspend fun create(@Body task: NetworkTask): NetworkTask

    @GET("getTask/{id}")
    suspend fun read(@Path("id") id: Long): NetworkTask

    @GET("getTaskList/{id}")
    suspend fun getTaskList(@Path("id") listId: Long): List<NetworkTask>

    @GET("getStarredTasks")
    suspend fun getStarredTasks(): List<NetworkTask>

    @PUT("updateTask/{id}")
    suspend fun update(
        @Body task: NetworkTask,
        @Path("id") id: Long = task.id,
    ): Response<Unit>

    @DELETE("deleteTask/{id}")
    suspend fun delete(@Path("id") id: Long): Response<Unit>
}