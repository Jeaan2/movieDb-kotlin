package com.jeansilva.mobile.pixelwolf_tmdb.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jeansilva.mobile.pixelwolf_tmdb.R
import com.jeansilva.mobile.pixelwolf_tmdb.model.Movie
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieRecyclerAdapter(
    private val context : Context,
    private val movies: List<Movie>,
    private val itemClick: (Movie) -> Unit) :
        RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRecyclerAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return ViewHolder (view, itemClick)
    }

    override fun getItemCount(): Int {
        return movies.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMovie(movies[position], context)
    }

    inner class ViewHolder(itemView: View, val itemClick: (Movie) -> Unit) :
            RecyclerView.ViewHolder(itemView) {


        val movieTitle = itemView.findViewById<TextView>(R.id.lblTitle)
        val moviePoster = itemView.findViewById<ImageView>(R.id.movieImage)
        val movieVoteAverage = itemView.findViewById<TextView>(R.id.lblVoteAverage)
        val movieReleaseDate = itemView.findViewById<TextView>(R.id.lblRelease)

        fun bindMovie(movie: Movie, context: Context) {

            //use Glide for imageLoading
            Glide.with(context).load(movie.getPosterUrl()).into(moviePoster)

            movieTitle.text = movie.title
            movieVoteAverage.text = movie.voteAverage.toString()
            movieReleaseDate.text =  movie.releaseDate

            itemView.setOnClickListener { itemClick(movie) }

        }
    }
}
