package com.example.pilottestmandirieko.model

import com.google.gson.annotations.SerializedName

data class ResponseError(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String,
    @SerializedName("success")
    val success: Boolean
)