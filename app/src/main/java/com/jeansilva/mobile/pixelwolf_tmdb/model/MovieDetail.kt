package com.jeansilva.mobile.pixelwolf_tmdb.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("backdrop_path")
    val posterPath: String,
    val budget: Long,
    @SerializedName("genres")
    val genres: List<Genre>,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int)  {


    fun getPosterUrl(): String
    {
        return "https://image.tmdb.org/t/p/w154$posterPath"
    }
}