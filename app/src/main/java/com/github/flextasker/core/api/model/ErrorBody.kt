package com.github.flextasker.core.api.model

import com.google.gson.annotations.SerializedName

data class ErrorBody(
    @SerializedName("errorText")
    val errorText: String,
)