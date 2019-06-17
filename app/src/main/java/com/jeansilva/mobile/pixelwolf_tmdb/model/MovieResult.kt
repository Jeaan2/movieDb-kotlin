package com.jeansilva.mobile.pixelwolf_tmdb.model

import com.google.gson.annotations.SerializedName


data class MovieResult (
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var movies: List<Movie>
)