package com.jeansilva.mobile.pixelwolf_tmdb.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jeansilva.mobile.pixelwolf_tmdb.R
import com.jeansilva.mobile.pixelwolf_tmdb.model.Genre
import kotlinx.android.synthetic.main.genre_item.view.*
import java.util.ArrayList

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    private var genres: List<Genre> = ArrayList()
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val root = (LayoutInflater.from(parent?.context).inflate(R.layout.genre_item, parent, false))
            return ViewHolder(root)

    }
    fun addGenre(genres: List<Genre>?) {
        if (genres != null) {
            this.genres = genres
            notifyDataSetChanged()
        }
    }
    inner class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {

        fun bind(genre: Genre) = with(itemView) {
            genre_name?.text = genre.name
        }
    }

}