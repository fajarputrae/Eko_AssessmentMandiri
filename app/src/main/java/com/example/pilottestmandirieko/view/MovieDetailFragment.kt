package com.example.pilottestmandirieko.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.pilottestmandirieko.R
import com.example.pilottestmandirieko.databinding.FragmentMovieDetailBinding
import com.example.pilottestmandirieko.helper.*
import com.example.pilottestmandirieko.model.DetailMovieModel
import com.example.pilottestmandirieko.model.MovieReviewModel
import com.example.pilottestmandirieko.util.Constant
import com.example.pilottestmandirieko.util.Constant.IMG_AVATAR_BASE_URL
import com.example.pilottestmandirieko.util.Constant.IMG_POSTER_BASE_URL_DEFAULT_SIZE
import com.example.pilottestmandirieko.view.base.BaseFragment
import org.koin.android.ext.android.inject


class MovieDetailFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentMovieDetailBinding
    private val viewModel by inject<MovieViewModel>()
    private var youtubeLink = emptyString
    private var movieReviewList = arrayListOf<MovieReviewModel.MovieReviews>()

    companion object {
        @JvmStatic
        fun newInstance() = MovieDetailFragment()
        var movieId = emptyInt
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.vm = viewModel
        dataBinding.lifecycleOwner = this.viewLifecycleOwner

        with(viewModel) {
            isLoading.observe(viewLifecycleOwner) { bool ->
                bool.let { loading ->
                    if (loading) {
                        showWaitingDialog()
                    } else {
                        hideWaitingDialog()
                    }
                }
            }

            responseError.observe(viewLifecycleOwner) {
                setEmptyData()
            }

            detailMovie.observe(viewLifecycleOwner) {
                //Use movieId = 3469 for test purposes
                viewModel.getMovieVideo(
                    movieId = movieId.toString()
                )
                //Use movieId = 64690 for test purposes
                viewModel.getMovieReview(
                    movieId = movieId.toString(),
                    page = "1"
                )
                setMovieDetail(it)
            }
            movieVideo.observe(viewLifecycleOwner) { videoData ->
                videoData?.forEach {
                    if (it.site == Constant.YtData.NAME)
                        youtubeLink = Constant.YtData.BASE_URL + it.key
                }
                setMovieVideo()
            }
            movieReview.observe(viewLifecycleOwner) { it ->
                it?.results?.forEach {
                    movieReviewList.add(it)
                }
                setMovieReview(movieReviewList)
            }
        }

        setView()
        setOnClick()
    }

    private fun setEmptyData() {
        dataBinding.apply {
            clMainContent.isVisible = false
            tvNoData.isVisible = true
        }
    }

    private fun setMovieDetail(movieData: DetailMovieModel?) {
        dataBinding.apply {
            if (movieData?.posterPath == null)
                ivPoster.setImageResource(R.drawable.ic_no_image)
            else
                ivPoster.loadImage(
                    IMG_POSTER_BASE_URL_DEFAULT_SIZE +
                            movieData.posterPath
                )
            if (movieData?.backdropPath == null)
                ivBackdrop.setImageResource(R.drawable.ic_no_image)
            else
                ivBackdrop.loadImage(
                    IMG_POSTER_BASE_URL_DEFAULT_SIZE +
                            movieData.backdropPath
                )

            tvMovieName.textOrNull = movieData?.title

            tvMovieReleaseDate.textOrNull = movieData?.releaseDate?.formatDate()
            val movieGenres = arrayListOf<String>()
            movieData?.genres?.forEach {
                movieGenres.add(it.name)
            }
            for (i in 0 until movieGenres.size) {
                tvMovieGenre.append(movieGenres[i])
                if (i != movieGenres.size - 1)
                    tvMovieGenre.append(", ")
            }

            tvMovieAvgRating.textOrNull = movieData?.voteAverage.toString()

            if (movieData?.overview == emptyString)
                tvMovieDesc.textOrNull = "No Overview provided yet"
            else
                tvMovieDesc.textOrNull = movieData?.overview
        }
    }

    private fun setMovieVideo() {
        dataBinding.apply {
            if (youtubeLink == emptyString) {
                tvLabelNoVideo.isVisible = true
                wvVideoPlayer.isVisible = false
            } else {
                tvLabelNoVideo.isVisible = false
                wvVideoPlayer.apply {
                    isVisible = true
                    settings.javaScriptEnabled = true
                    loadUrl(youtubeLink)
                }
            }
        }
    }

    private fun setMovieReview(movieReviewData: ArrayList<MovieReviewModel.MovieReviews>) {
        dataBinding.apply {
            if (movieReviewData.isEmpty()) {
                tvLabelNoReview.isVisible = true
                cvTopReview.isVisible = false
                tvSeeMoreReview.isVisible = false
            } else {
                tvLabelNoReview.isVisible = false
                cvTopReview.isVisible = true
                tvSeeMoreReview.isVisible = true
                ivAvatar.loadImage(
                    IMG_AVATAR_BASE_URL +
                            movieReviewData[0].authorDetails.avatarPath,
                    ImageCornerOptions.ROUNDED,
                    100
                )
                tvReviewTitle.textOrNull = "Review By " + movieReviewData[0].authorDetails.username
                tvReviewMeta.textOrNull =
                    "Written by " + movieReviewData[0].authorDetails.username + " on " + movieReviewData[0].updatedAt.formatDate()
                tvReviewContent.textOrNull = movieReviewData[0].content
            }
        }
    }

    private fun setView() {
        viewModel.getDetailMovie(
            movieId = movieId.toString()
        )
    }

    private fun setOnClick() {
        dataBinding.apply {
            ivBack.setOnClickListener(onClickCallback)
            tvSeeMoreReview.setOnClickListener(onClickCallback)
        }
    }

    private val onClickCallback = View.OnClickListener { view ->
        when (view) {
            dataBinding.ivBack -> {
                dataBinding.apply {
                    MovieFragment.isBackFromDetail = true
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
            dataBinding.tvSeeMoreReview -> {
                dataBinding.apply {
                    //Use movieId = 64690 for test purposes
                    ReviewFragment.movieId = movieId
                    addFragment(ReviewFragment.newInstance())
                }
            }
        }
    }

}