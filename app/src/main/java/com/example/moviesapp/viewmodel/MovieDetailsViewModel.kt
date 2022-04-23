package com.example.moviesapp.viewmodel

import androidx.lifecycle.*
import com.example.moviesapp.Constant
import com.example.moviesapp.network.model.Cast
import com.example.moviesapp.network.model.Movie
import com.example.moviesapp.network.model.Trailer
import com.example.moviesapp.network.Api
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val movieId: Int): ViewModel(), ViewModelProvider.Factory {
    private var _movie = MutableLiveData<Movie>()
    private var _trailer = MutableLiveData<Trailer>()
    private var _casts = MutableLiveData<List<Cast>>()
    private var _similarMovies = MutableLiveData<List<Movie>>()

    val movie: LiveData<Movie> = _movie
    val trailer: LiveData<Trailer> = _trailer
    val casts: LiveData<List<Cast>> = _casts
    val similarMovies: LiveData<List<Movie>> = _similarMovies

    init {
        getMovieDetails()
        getCastsMovie()
        getSimilarMovie()
        getTrailerMovie()
    }

    // Tại đây chứa 2 phương thức get MovieDetails và Get TrailerMovie
    private fun getMovieDetails(){
        try{
            viewModelScope.launch {
                _movie.value =
                    Api.retrofitService.getMovieDetails(movieId, Constant.API_KEY, Constant.LANGUAGE)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    private fun getTrailerMovie(){
        try{
            viewModelScope.launch {
                _trailer.value =
                    Api.retrofitService.getTrailerMovie(movieId, Constant.API_KEY).trailers?.get(0)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    private fun getCastsMovie(){
        try{
            viewModelScope.launch {
                _casts.value =
                    Api.retrofitService.getCastMovie(movieId, Constant.API_KEY, Constant.LANGUAGE).cast ?: listOf()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    private fun getSimilarMovie(){
        try{
            viewModelScope.launch {
                _similarMovies.value =
                    Api.retrofitService.getSimilarMovie(movieId, Constant.API_KEY, Constant.LANGUAGE).results
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)){
            return MovieDetailsViewModel(movieId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}