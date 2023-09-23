package com.github.flextasker.core.api.model

import com.google.gson.annotations.SerializedName

data class NetworkTask(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("isComplete")
    val isComplete: Boolean,

    @SerializedName("isStarred")
    val isStarred: Boolean,

    @SerializedName("listId")
    val listId: Long,
)
