package com.jeansilva.mobile.pixelwolf_tmdb.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AlertDialog
import android.view.View
import com.bumptech.glide.Glide
import com.jeansilva.mobile.pixelwolf_tmdb.R
import com.jeansilva.mobile.pixelwolf_tmdb.adapters.GenreAdapter
import com.jeansilva.mobile.pixelwolf_tmdb.model.MovieDetail
import com.jeansilva.mobile.pixelwolf_tmdb.service.MovieService
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.movie_info.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DetailsActivity : AppCompatActivity() {

    lateinit var movie: MovieDetail
    lateinit var movieId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(appbar)
        collapsing.setExpandedTitleMargin(50, 50, 250, 50)

            genre_list.adapter = GenreAdapter()
        val layoutManager = LinearLayoutManager(this,  LinearLayoutManager.HORIZONTAL, false)
        genre_list.layoutManager = layoutManager


         movieId = intent.getStringExtra("id")

        callMovieDetailApi(movieId)
    }

    private fun callMovieDetailApi(id: String) {
        val api = MovieService()
        api.getMovieDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                movieDetail -> ongetMovieDetailsSuccess(movieDetail)
            }, {
                e -> e.printStackTrace()
                showDialog()
            })
    }

    private fun ongetMovieDetailsSuccess(movieDetail: MovieDetail?) {
        loading.visibility = View.GONE
        collapsing.title = movieDetail?.title
        overview?.text = movieDetail?.overview
        vote_average?.text = movieDetail?.voteAverage.toString()
        release_date?.text = movieDetail?.releaseDate
        runtime?.text = "${movieDetail?.runtime.toString()}min"
        budget?.text = movieDetail?.budget.toString()
        revenue?.text = movieDetail?.revenue.toString()
        Glide.with(this)
            .load(movieDetail?.getPosterUrl())
            .into(poster)
        (genre_list.adapter as GenreAdapter).addGenre(movieDetail?.genres)

    }

    fun showDialog() {

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Atenção")
        builder.setMessage("Não foi posssível conectar ao servidor. Por favor, tente novamente")
        builder.setPositiveButton("Tentar Novamente") {
                dialog, which ->

            callMovieDetailApi(movieId)
        }

        builder.setNegativeButton("Cancelar") {dialog, which ->
            dialog.dismiss()
        }

        val dialog : AlertDialog = builder.create()
        dialog.show()

    }
}
