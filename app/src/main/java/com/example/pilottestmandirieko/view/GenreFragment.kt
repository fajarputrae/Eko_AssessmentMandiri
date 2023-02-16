package com.example.pilottestmandirieko.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pilottestmandirieko.R
import com.example.pilottestmandirieko.databinding.FragmentGenreBinding
import com.example.pilottestmandirieko.helper.UtilityHelper.Companion.toArrayList
import com.example.pilottestmandirieko.helper.emptyBoolean
import com.example.pilottestmandirieko.model.ListGenreModel
import com.example.pilottestmandirieko.view.base.BaseFragment
import org.koin.android.ext.android.inject

class GenreFragment : BaseFragment(), GenreAdapter.Listener {

    private lateinit var dataBinding: FragmentGenreBinding
    private val viewModel by inject<MovieViewModel>()

    companion object {
        @JvmStatic
        fun newInstance() = GenreFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_genre, container, false)
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
            listGenre.observe(viewLifecycleOwner) {
                setGenreList(it.orEmpty().toArrayList())
            }
        }

        setView()
    }

    private fun setGenreList(list: ArrayList<ListGenreModel.Genre>) {
        dataBinding.apply {
            rvGenre.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = GenreAdapter(context, list, this@GenreFragment)
            }
        }


    }

    override fun onItemClicked(data: ListGenreModel.Genre) {
        MovieFragment.movieId = data.id
        MovieFragment.genreName = data.name
        MovieFragment.isBackFromDetail = emptyBoolean
        addFragment(MovieFragment.newInstance())
    }

    private fun setView() {
        viewModel.getAllGenre()
        onBackPressed()
    }

    private fun onBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

}