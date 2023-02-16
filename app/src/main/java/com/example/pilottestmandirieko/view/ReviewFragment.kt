package com.example.pilottestmandirieko.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pilottestmandirieko.R
import com.example.pilottestmandirieko.databinding.FragmentReviewBinding
import com.example.pilottestmandirieko.helper.UtilityHelper.Companion.toArrayList
import com.example.pilottestmandirieko.helper.emptyInt
import com.example.pilottestmandirieko.helper.orEmpty
import com.example.pilottestmandirieko.model.MovieReviewModel
import com.example.pilottestmandirieko.view.base.BaseFragment
import org.koin.android.ext.android.inject

class ReviewFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentReviewBinding
    private val viewModel by inject<MovieViewModel>()

    private var reviewPages = 1
    private var reviewTotalPages = emptyInt
    private var isLoadingData = false
    private lateinit var rvReviewAdapter: ReviewAdapter
    private var listReview = arrayListOf<MovieReviewModel.MovieReviews?>()

    companion object {
        @JvmStatic
        fun newInstance() = ReviewFragment()
        var movieId = emptyInt
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_review, container, false)
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
            movieReview.observe(viewLifecycleOwner) {
                reviewTotalPages = it?.totalPages.orEmpty
                setReviewList(it?.results.orEmpty().toArrayList())
            }
        }

        setView()
        setOnClick()
    }

    private fun setReviewList(reviewListData: ArrayList<MovieReviewModel.MovieReviews>) {
        dataBinding.apply {
            if (reviewPages == 1) {
                listReview = reviewListData.toArrayList()
                rvReview.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = ReviewAdapter(context, listReview)
                }
            } else {
                listReview.addAll(reviewListData)
                rvReview.scrollToPosition(listReview.size - reviewListData.size)
                rvReviewAdapter.notifyDataSetChanged()
                isLoadingData = false
            }
        }
        reviewPages += 1
    }

    private fun initAdapter() {
        dataBinding.apply {
            rvReviewAdapter = ReviewAdapter(requireContext(), listReview)
            rvReview.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = rvReviewAdapter
            }
        }
    }

    private fun initScrollListener() {
        dataBinding.apply {
            rvReview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    if (!isLoadingData) {
                        if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listReview.size - 1) {
                            //Bottom of list
                            loadMore()
                            isLoadingData = true
                        }
                    }
                }
            })
        }
    }

    private fun loadMore() {
        dataBinding.apply {
            listReview.add(null)
            rvReviewAdapter.notifyItemInserted(listReview.size - 1)
            Handler(Looper.getMainLooper()).postDelayed({
                listReview.removeAt(listReview.size - 1)
                rvReviewAdapter.notifyItemRemoved(listReview.size - 1)

                if (reviewPages <= reviewTotalPages) viewModel.getMovieReview(
                    movieId = movieId.toString(), page = reviewPages.toString()
                )
            }, 3000)
        }
    }

    private fun setView() {
        viewModel.getMovieReview(movieId = movieId.toString(), page = reviewPages.toString())
        initAdapter()
        initScrollListener()
    }

    private fun setOnClick() {
        dataBinding.apply {
            ivBack.setOnClickListener(onClickCallback)
        }
    }

    private val onClickCallback = View.OnClickListener { view ->
        when (view) {
            dataBinding.ivBack -> {
                dataBinding.apply {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

}