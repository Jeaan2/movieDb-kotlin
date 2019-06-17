package com.jeansilva.mobile.pixelwolf_tmdb.service

import com.google.gson.GsonBuilder
import com.jeansilva.mobile.pixelwolf_tmdb.model.Movie
import com.jeansilva.mobile.pixelwolf_tmdb.model.MovieResult
import com.jeansilva.mobile.pixelwolf_tmdb.utils.API_KEY
import com.jeansilva.mobile.pixelwolf_tmdb.utils.LANG
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable


class MovieService  {

    val service: TMDbApi

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()

        service = retrofit.create<TMDbApi>(TMDbApi::class.java)
    }


    fun getMovies(page: String) : Observable<Movie> {
        return service.getMovies(API_KEY, page, LANG)
            .flatMap { movieResults -> Observable.from(movieResults.movies)  }
            .map { movie ->
                Movie(
                    movie.id,
                    movie.title,
                    movie.voteAverage,
                    movie.releaseDate,
                    movie.posterPath
                )
            }
    }
}


