package com.example.pilottestmandirieko.model

import com.google.gson.annotations.SerializedName

data class ResponseSuccess<T>(
    @SerializedName("data")
    val data: T
)