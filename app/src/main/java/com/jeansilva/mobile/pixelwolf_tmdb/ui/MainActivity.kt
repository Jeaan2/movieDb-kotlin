package com.jeansilva.mobile.pixelwolf_tmdb.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import com.jeansilva.mobile.pixelwolf_tmdb.R
import com.jeansilva.mobile.pixelwolf_tmdb.adapters.MovieRecyclerAdapter
import com.jeansilva.mobile.pixelwolf_tmdb.model.Movie
import com.jeansilva.mobile.pixelwolf_tmdb.service.MovieService
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    lateinit var adapter: MovieRecyclerAdapter
    lateinit var searchView: SearchView
    var movies = mutableListOf<Movie>()
    var page = 1
    var isBottomReached = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        adapter = MovieRecyclerAdapter(this, movies) {
            movie ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("id", movie.id)
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
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        searchView = searchItem?.actionView as SearchView
        searchView.queryHint = ("Buscar")
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
               adapter.filter.filter(newText)
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }
        })
        return true
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
                showDialog()

            }, {
                adapter.notifyDataSetChanged()
            })
    }

    fun showDialog() {

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Atenção")
        builder.setMessage("Não foi posssível conectar ao servidor. Por favor, tente novamente")
        builder.setPositiveButton("Tentar Novamente") {
            dialog, which ->

            callApi("1")
        }

        builder.setNegativeButton("Cancelar") {dialog, which ->
            dialog.dismiss()
        }

        val dialog : AlertDialog = builder.create()
        dialog.show()

    }

}
