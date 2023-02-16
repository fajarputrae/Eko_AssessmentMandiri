package com.example.pilottestmandirieko.model

import com.google.gson.annotations.SerializedName

class ListMovieModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: ArrayList<MovieResults>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
) {
    data class MovieResults(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("poster_path")
        val posterPath: String? = null
    )
}