package com.github.flextasker.core.api

import com.github.flextasker.core.api.model.NetworkTaskList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskListApi {

    @POST("createTaskList")
    suspend fun create(@Body list: NetworkTaskList): NetworkTaskList

    @PUT("updateTaskList/{id}")
    suspend fun update(
        @Body list: NetworkTaskList,
        @Path("id") id: Long = list.id
    ): Response<Unit>

    @DELETE("deleteTaskList/{id}")
    suspend fun delete(@Path("id") id: Long): Response<Unit>

    @GET("getTaskLists")
    suspend fun getAll(): List<NetworkTaskList>
}