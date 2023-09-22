package com.github.flextasker.core.api.model

import com.google.gson.annotations.SerializedName

data class NetworkAccountInfo(
    @SerializedName("access_token")
    val token: String,

    @SerializedName("username")
    val userName: String,
)
