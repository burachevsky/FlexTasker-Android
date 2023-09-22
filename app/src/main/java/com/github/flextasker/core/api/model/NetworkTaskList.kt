package com.github.flextasker.core.api.model

import com.google.gson.annotations.SerializedName

data class NetworkTaskList(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("listType")
    val type: Int,
) {
    companion object {
        const val TYPE_DEFAULT = 0
        const val TYPE_USER = 1
    }
}
