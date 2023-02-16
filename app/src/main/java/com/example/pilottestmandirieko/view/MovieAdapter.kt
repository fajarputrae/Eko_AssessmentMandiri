package com.example.pilottestmandirieko.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.pilottestmandirieko.R
import com.example.pilottestmandirieko.helper.loadImage
import com.example.pilottestmandirieko.helper.textOrNull
import com.example.pilottestmandirieko.model.ListMovieModel
import com.example.pilottestmandirieko.util.Constant.IMG_POSTER_BASE_URL_DEFAULT_SIZE


class MovieAdapter(
    context: Context,
    list: ArrayList<ListMovieModel.MovieResults?>,
    private val listener: Listener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val contexts = context
    private val itemList = list

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    interface Listener {
        fun onItemClicked(data: ListMovieModel.MovieResults?)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view: View =
                LayoutInflater.from(contexts).inflate(R.layout.item_movie, parent, false)
            MovieViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(contexts).inflate(R.layout.item_loading, parent, false)
            LoadMoreMovieViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is MovieViewHolder) {
            populateItemRows(viewHolder, position)
        } else if (viewHolder is LoadMoreMovieViewHolder) {
            showLoadingView(viewHolder, position)
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivMoviePoster: ImageView = itemView.findViewById(R.id.ivMoviePoster)
        val tvMovieName: TextView = itemView.findViewById(R.id.tvMovieName)
        val tvMovieDesc: TextView = itemView.findViewById(R.id.tvMovieDesc)
    }

    inner class LoadMoreMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pbMovie: ProgressBar = itemView.findViewById(R.id.pbMovie)
    }

    private fun showLoadingView(viewHolder: LoadMoreMovieViewHolder, position: Int) {
        //ProgressBar would be displayed
        viewHolder.pbMovie.isVisible = true
    }

    private fun populateItemRows(viewHolder: MovieViewHolder, position: Int) {
        if (itemList[position]?.posterPath == null)
            viewHolder.ivMoviePoster.setImageResource(R.drawable.ic_no_image)
        else
            viewHolder.ivMoviePoster.loadImage(
                IMG_POSTER_BASE_URL_DEFAULT_SIZE +
                        itemList[position]?.posterPath
            )

        viewHolder.tvMovieName.textOrNull = itemList[position]?.name
        viewHolder.tvMovieDesc.textOrNull = itemList[position]?.description

        viewHolder.itemView.setOnClickListener {
            listener.onItemClicked(itemList[position])
        }
    }
}