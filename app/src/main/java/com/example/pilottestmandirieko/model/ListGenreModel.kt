package com.example.pilottestmandirieko.model

import com.google.gson.annotations.SerializedName

data class ListGenreModel(
    @SerializedName("genres")
    val genres: ArrayList<Genre>
) {
    data class Genre(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )
}