package com.jeansilva.mobile.pixelwolf_tmdb.service


import com.jeansilva.mobile.pixelwolf_tmdb.model.MovieDetail
import com.jeansilva.mobile.pixelwolf_tmdb.model.MovieResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import rx.Observable

interface TMDbApi {

    @GET("movie/upcoming")
    fun getMovies(@Query("api_key") apiKey: String, @Query("page") page: String,
                  @Query( "language") language : String) : Observable<MovieResult>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: String): Observable<MovieDetail>
}

