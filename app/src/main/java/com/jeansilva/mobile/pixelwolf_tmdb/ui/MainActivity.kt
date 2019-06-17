package com.jeansilva.mobile.pixelwolf_tmdb.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.jeansilva.mobile.pixelwolf_tmdb.R
import com.jeansilva.mobile.pixelwolf_tmdb.adapters.MovieRecyclerAdapter
import com.jeansilva.mobile.pixelwolf_tmdb.model.Movie
import com.jeansilva.mobile.pixelwolf_tmdb.service.MovieService
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    lateinit var adapter: MovieRecyclerAdapter
    var movies = mutableListOf<Movie>()
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MovieRecyclerAdapter(this, movies) {
            movie ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("Movie", movie.id)
            startActivity(intent)
        }

        list_movie.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        list_movie.layoutManager = layoutManager
        list_movie.setHasFixedSize(true)

        callApi(page.toString())


    }

    private fun callApi(page: String)
    {
        val api = MovieService()
        api.getMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movie ->
                movies.add(movie)
            },{
                    e -> e.printStackTrace()
            }, {
                adapter.notifyDataSetChanged()
            })
    }
}
