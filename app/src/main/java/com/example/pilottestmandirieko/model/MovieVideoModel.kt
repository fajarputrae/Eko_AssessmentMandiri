package com.example.pilottestmandirieko.model

import com.google.gson.annotations.SerializedName

data class MovieVideoModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: ArrayList<VideoModel>
) {
    data class VideoModel(
        @SerializedName("iso_639_1")
        val iso_639_1: String,
        @SerializedName("iso_3166_1")
        val iso_3166_1: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("key")
        val key: String,
        @SerializedName("published_at")
        val publishedAt: String,
        @SerializedName("site")
        val site: String,
        @SerializedName("size")
        val size: Int,
        @SerializedName("type")
        val type: String,
        @SerializedName("official")
        val official: String,
        @SerializedName("id")
        val id: String
    )
}