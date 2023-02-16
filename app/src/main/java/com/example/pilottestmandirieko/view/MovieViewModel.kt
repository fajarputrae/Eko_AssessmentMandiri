package com.example.pilottestmandirieko.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pilottestmandirieko.model.*
import com.example.pilottestmandirieko.repository.MovieRepository
import com.example.pilottestmandirieko.util.SingleLiveEvent
import com.example.pilottestmandirieko.view.base.BaseViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {

    val responseError = SingleLiveEvent<Unit?>()

    val listGenre = MutableLiveData<ArrayList<ListGenreModel.Genre>>()
    val listMovie = MutableLiveData<ListMovieModel?>()
    val detailMovie = MutableLiveData<DetailMovieModel?>()
    val movieVideo = MutableLiveData<ArrayList<MovieVideoModel.VideoModel>>()
    val movieReview = MutableLiveData<MovieReviewModel?>()

    fun getAllGenre() {
        isLoading.value = true
        viewModelScope.launch {
            when (val response = movieRepository.getAllMovieGenre()) {
                is NetworkResponse.Success -> {
                    isLoading.value = false
                    listGenre.value = response.body.genres
                }
                is NetworkResponse.ServerError -> {
                    isLoading.value = false
                    snackbarMessage.value = response.error?.message.toString()
                }
                is NetworkResponse.NetworkError -> {
                    isLoading.value = false
                    networkError.value = response.error.message.toString()
                    snackbarMessage.value = response.error.message.toString()
                }
                else -> {}
            }
        }
    }

    fun getAllMovie(movieId: String, page: String) {
        isLoading.value = true
        viewModelScope.launch {
            when (val response = movieRepository.getAllMovie(movieId = movieId, page = page)) {
                is NetworkResponse.Success -> {
                    isLoading.value = false
                    listMovie.value = response.body
                }
                is NetworkResponse.ServerError -> {
                    isLoading.value = false
                    snackbarMessage.value = response.error?.message.toString()
                }
                is NetworkResponse.NetworkError -> {
                    isLoading.value = false
                    networkError.value = response.error.message.toString()
                    snackbarMessage.value = response.error.message.toString()
                }
                else -> {}
            }
        }
    }

    fun getDetailMovie(movieId: String) {
        isLoading.value = true
        viewModelScope.launch {
            when (val response = movieRepository.getDetailMovie(movieId = movieId)) {
                is NetworkResponse.Success -> {
                    isLoading.value = false
                    detailMovie.value = response.body
                }
                is NetworkResponse.ServerError -> {
                    isLoading.value = false
                    snackbarMessage.value = response.error?.message.toString()
                    responseError.call()
                }
                is NetworkResponse.NetworkError -> {
                    isLoading.value = false
                    networkError.value = response.error.message.toString()
                    snackbarMessage.value = response.error.message.toString()
                    responseError.call()
                }
                else -> {}
            }
        }
    }

    fun getMovieVideo(movieId: String) {
        isLoading.value = true
        viewModelScope.launch {
            when (val response = movieRepository.getMovieVideo(movieId = movieId)) {
                is NetworkResponse.Success -> {
                    isLoading.value = false
                    movieVideo.value = response.body.results
                }
                is NetworkResponse.ServerError -> {
                    isLoading.value = false
                    snackbarMessage.value = response.error?.message.toString()
                }
                is NetworkResponse.NetworkError -> {
                    isLoading.value = false
                    networkError.value = response.error.message.toString()
                    snackbarMessage.value = response.error.message.toString()
                }
                else -> {}
            }
        }
    }

    fun getMovieReview(movieId: String, page: String) {
        isLoading.value = true
        viewModelScope.launch {
            when (val response = movieRepository.getMovieReview(movieId = movieId, page = page)) {
                is NetworkResponse.Success -> {
                    isLoading.value = false
                    movieReview.value = response.body
                }
                is NetworkResponse.ServerError -> {
                    isLoading.value = false
                    snackbarMessage.value = response.error?.message.toString()
                }
                is NetworkResponse.NetworkError -> {
                    isLoading.value = false
                    networkError.value = response.error.message.toString()
                    snackbarMessage.value = response.error.message.toString()
                }
                else -> {}
            }
        }
    }
}