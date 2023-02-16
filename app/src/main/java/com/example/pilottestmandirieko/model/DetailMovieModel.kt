package com.example.pilottestmandirieko.model

import com.google.gson.annotations.SerializedName

data class DetailMovieModel(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any?,
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("genres")
    val genres: ArrayList<DetailMovieGenres>,
    @SerializedName("homepage")
    val homepage: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val original_language: String,
    @SerializedName("original_title")
    val original_title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Float,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("production_companies")
    val productionCompanies: ArrayList<ProductionCompanies>,
    @SerializedName("production_countries")
    val productionCountries: ArrayList<ProductionCountries>,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("revenue")
    val revenue: Int,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("spoken_languages")
    val spokenLanguages: ArrayList<SpokenLanguages>,
    @SerializedName("status")
    val status: String,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("vote_count")
    val voteCount: Int
) {
    data class DetailMovieGenres(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )

    data class ProductionCompanies(
        @SerializedName("id")
        val id: Int,
        @SerializedName("logo_path")
        val logoPath: String?,
        @SerializedName("name")
        val name: String,
        @SerializedName("origin_country")
        val originCountry: String
    )

    data class ProductionCountries(
        @SerializedName("iso_3166_1")
        val iso_3166_1: String,
        @SerializedName("name")
        val name: String
    )

    data class SpokenLanguages(
        @SerializedName("english_name")
        val englishName: String,
        @SerializedName("iso_639_1")
        val iso_639_1: String,
        @SerializedName("name")
        val name: String
    )

}