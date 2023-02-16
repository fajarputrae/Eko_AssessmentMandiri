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
import com.example.pilottestmandirieko.helper.ImageCornerOptions
import com.example.pilottestmandirieko.helper.formatDate
import com.example.pilottestmandirieko.helper.loadImage
import com.example.pilottestmandirieko.helper.textOrNull
import com.example.pilottestmandirieko.model.MovieReviewModel
import com.example.pilottestmandirieko.util.Constant

class ReviewAdapter(
    context: Context,
    list: ArrayList<MovieReviewModel.MovieReviews?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val contexts = context
    private val itemList = list

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view: View =
                LayoutInflater.from(contexts).inflate(R.layout.item_review, parent, false)
            ReviewViewHolder(view)
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
        if (viewHolder is ReviewViewHolder) {
            populateItemRows(viewHolder, position)
        } else if (viewHolder is LoadMoreMovieViewHolder) {
            showLoadingView(viewHolder, position)
        }
    }

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val tvReviewTitle: TextView = itemView.findViewById(R.id.tvReviewTitle)
        val tvReviewMeta: TextView = itemView.findViewById(R.id.tvReviewMeta)
        val tvReviewContent: TextView = itemView.findViewById(R.id.tvReviewContent)
    }

    inner class LoadMoreMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pbMovie: ProgressBar = itemView.findViewById(R.id.pbMovie)
    }

    private fun showLoadingView(viewHolder: LoadMoreMovieViewHolder, position: Int) {
        //ProgressBar would be displayed
        viewHolder.pbMovie.isVisible = true
    }

    private fun populateItemRows(viewHolder: ReviewViewHolder, position: Int) {
        if (itemList[position]?.authorDetails?.avatarPath == null)
            viewHolder.ivAvatar.setImageResource(R.drawable.ic_no_image)
        else
            viewHolder.ivAvatar.loadImage(
                Constant.IMG_AVATAR_BASE_URL +
                        itemList[position]?.authorDetails?.avatarPath,
                ImageCornerOptions.ROUNDED,
                100
            )

        viewHolder.tvReviewTitle.textOrNull =
            "Review By " + itemList[position]?.authorDetails?.username
        viewHolder.tvReviewMeta.textOrNull =
            "Written by " + itemList[position]?.authorDetails?.username + " on " + itemList[position]?.updatedAt?.formatDate()
        viewHolder.tvReviewContent.textOrNull = itemList[position]?.content
    }
}