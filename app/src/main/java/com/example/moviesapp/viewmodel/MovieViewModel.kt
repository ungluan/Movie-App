package com.example.moviesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.Constant
import com.example.moviesapp.network.model.Movie
import com.example.moviesapp.network.model.MoviesResponse
import com.example.moviesapp.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class MovieViewModel : ViewModel() {

//    private var _movieTrendingResponse = MutableLiveData<MoviesResponse>()
    private var _movies = MutableLiveData<List<Movie>>()
//    val moviesResponse: LiveData<MoviesResponse> = _movieTrendingResponse
//    val movies: LiveData<List<Movie>> = _movies

    init {
        getMoviesTrending()
    }

    private fun getMoviesTrending() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val dataResponse: MoviesResponse =
                    Api.retrofitService.getTrendingMovies(Constant.API_KEY, Constant.LANGUAGE, 1)
                _movies.value = dataResponse.results

            }
        } catch (e: Exception) {
            _movies.value = listOf()
            e.printStackTrace()
        }
    }

}