package com.jeansilva.mobile.pixelwolf_tmdb.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Movie(
    val id: String,
    val title: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("poster_path") val posterPath: String)  {

    fun getPosterUrl(): String
    {
        return "https://image.tmdb.org/t/p/w154/$posterPath"
    }
}