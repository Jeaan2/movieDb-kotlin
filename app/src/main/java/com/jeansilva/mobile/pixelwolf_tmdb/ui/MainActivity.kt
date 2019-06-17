package com.jeansilva.mobile.pixelwolf_tmdb.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
    var isBottomReached = false

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

        list_movie.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                isBottomReached = !recyclerView.canScrollVertically(1)

                if (isBottomReached)
                {
                        page++
                        callApi(page.toString())

                }
            }
        })

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
