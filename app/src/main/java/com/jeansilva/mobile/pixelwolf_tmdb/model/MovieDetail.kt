package com.jeansilva.mobile.pixelwolf_tmdb.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("backdrop_path")
    private val posterPath: String,
    val id: String,
    val overview: String,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("release_date")
    val releaseDate: String,
    val runtime: Int,
    @SerializedName("genres")
    val genres: List<Genre>)  {


    fun getPosterUrl(): String
    {
        return "https://image.tmdb.org/t/p/w154$posterPath"
    }
}