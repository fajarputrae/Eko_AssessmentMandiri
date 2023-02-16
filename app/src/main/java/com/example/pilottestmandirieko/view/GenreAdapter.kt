package com.example.pilottestmandirieko.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pilottestmandirieko.R
import com.example.pilottestmandirieko.model.ListGenreModel

class GenreAdapter(
    context: Context,
    list: ArrayList<ListGenreModel.Genre>,
    private val listener: Listener
) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    private val contexts = context
    private val itemList = list

    interface Listener {
        fun onItemClicked(data: ListGenreModel.Genre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view =
            LayoutInflater.from(contexts).inflate(R.layout.item_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.tvGenreName.text = itemList[position].name

        holder.itemView.setOnClickListener {
            listener.onItemClicked(itemList[position])
        }
    }

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvGenreName: TextView = itemView.findViewById(R.id.tvGenreName)

    }
}