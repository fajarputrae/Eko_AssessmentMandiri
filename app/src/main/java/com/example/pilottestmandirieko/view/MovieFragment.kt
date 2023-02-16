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
import com.example.pilottestmandirieko.databinding.FragmentMovieBinding
import com.example.pilottestmandirieko.helper.UtilityHelper.Companion.toArrayList
import com.example.pilottestmandirieko.helper.emptyBoolean
import com.example.pilottestmandirieko.helper.emptyInt
import com.example.pilottestmandirieko.helper.emptyString
import com.example.pilottestmandirieko.helper.orEmpty
import com.example.pilottestmandirieko.model.ListMovieModel
import com.example.pilottestmandirieko.view.base.BaseFragment
import org.koin.android.ext.android.inject


class MovieFragment : BaseFragment(), MovieAdapter.Listener {

    private lateinit var dataBinding: FragmentMovieBinding
    private val viewModel by inject<MovieViewModel>()

    private var moviePages = 1
    private var movieTotalPages = emptyInt
    private var isLoadingData = false
    private lateinit var rvMovieAdapter: MovieAdapter
    private var listMovie = arrayListOf<ListMovieModel.MovieResults?>()

    companion object {
        @JvmStatic
        fun newInstance() = MovieFragment()
        var genreName = emptyString
        var movieId = emptyInt
        var isBackFromDetail = emptyBoolean
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
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
            listMovie.observe(viewLifecycleOwner) {
                movieTotalPages = it?.totalPages.orEmpty
                setMovieList(it?.results.orEmpty().toArrayList())
            }
        }

        if(savedInstanceState == null){
            isLoadingData = emptyBoolean
            moviePages = 1
            setView()
            setOnClick()
        }
    }

    private fun setMovieList(movieListData: ArrayList<ListMovieModel.MovieResults>) {
        dataBinding.apply {
            if (moviePages == 1) {
                listMovie = movieListData.toArrayList()
                rvMovieAdapter = MovieAdapter(requireContext(), listMovie, this@MovieFragment)
                rvMovie.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = rvMovieAdapter
                }
            } else {
                listMovie.addAll(movieListData)
                rvMovie.scrollToPosition(listMovie.size - movieListData.size)
                rvMovieAdapter.notifyDataSetChanged()
                isLoadingData = false
            }
        }
        moviePages += 1
    }

    private fun initScrollListener() {
        dataBinding.apply {
            rvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    if (!isLoadingData) {
                        if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listMovie.size - 1) {
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
            listMovie.add(null)
            rvMovieAdapter.notifyItemInserted(listMovie.size - 1)
            Handler(Looper.getMainLooper()).postDelayed({
                listMovie.removeAt(listMovie.size - 1)
                rvMovieAdapter.notifyItemRemoved(listMovie.size - 1)

                if (moviePages <= movieTotalPages)
                    viewModel.getAllMovie(
                        movieId = movieId.toString(),
                        page = moviePages.toString()
                    )
            }, 3000)
        }
    }

    override fun onItemClicked(data: ListMovieModel.MovieResults?) {
        MovieDetailFragment.movieId = data?.id.orEmpty
        addFragment(MovieDetailFragment.newInstance())
    }

    private fun setView() {
        dataBinding.apply {
            tvTitle.text = genreName
        }
        if(!isBackFromDetail)
            viewModel.getAllMovie(movieId = movieId.toString(), page = moviePages.toString())
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