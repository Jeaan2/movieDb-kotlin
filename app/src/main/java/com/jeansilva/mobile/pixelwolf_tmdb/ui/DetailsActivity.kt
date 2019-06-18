package com.jeansilva.mobile.pixelwolf_tmdb.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.jeansilva.mobile.pixelwolf_tmdb.R
import com.jeansilva.mobile.pixelwolf_tmdb.model.Movie
import com.jeansilva.mobile.pixelwolf_tmdb.model.MovieDetail
import com.jeansilva.mobile.pixelwolf_tmdb.service.MovieService
import kotlinx.android.synthetic.main.activity_details.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DetailsActivity : AppCompatActivity() {

    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(appbar)

        val movieId = intent.getStringExtra("id")

        callMovieDetailApi(movieId)

        //Glide.with(this).load(movie.getPosterUrl()).into(poster)
    }

    private fun callMovieDetailApi(id: String) {
        val api = MovieService()
        api.getMovieDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                movieDetail ->
                movie
            }, {
                e -> e.printStackTrace()
            })
    }
}
