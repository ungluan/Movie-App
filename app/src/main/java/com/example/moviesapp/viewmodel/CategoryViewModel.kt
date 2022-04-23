package com.example.moviesapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.Constant
import com.example.moviesapp.network.model.*
import com.example.moviesapp.network.Api
import kotlinx.coroutines.*

private const val TAG = "CATEGORY VIEW MODEL"
//fun randomPage(): Int = (1..1).random()


class CategoryViewModel : ViewModel() {
    // Chứa data

    private var _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories


    init {
        // Khi khởi tạo ViewModel này thì thực thi những function nào?
        loadCategories()
    }

    // Chứa function getData

    private fun getGenres(): List<Genre> {
        var genres: List<Genre>? = null
        try {
            viewModelScope.launch(Dispatchers.IO) {
                Log.i(TAG, "START LOAD GENRES")
                val data: GenresResponse =
                    Api.retrofitService.getGenres(Constant.API_KEY, Constant.LANGUAGE)
                genres = data.genres
                Log.i(TAG, "GENRES: +$data")
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return genres ?: listOf()
    }

    private fun getMoviesByGenre(genreId: Int): List<Movie> {
        var moviesResponse: MoviesResponse? = null
        try {
            viewModelScope.launch(Dispatchers.IO) {
                Log.i(TAG, "START LOAD MOVIE_RESPONSE")
                moviesResponse = Api.retrofitService.getMoviesByGenre(Constant.API_KEY, genreId, 1)
                Log.i(TAG, "MOVIE RESPONSE:" + moviesResponse.toString())

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return moviesResponse?.results ?: listOf()
    }

    /*
        !!! Giải quyết vấn đề await data
        - Hiện tại giải quyết trong 1 function:
            + Sau khi get được genres thì khởi tạo 1 list Category với length bằng với genres.size
            + genres.forEach() : với mỗi genres thì ta tiếp tục chạy vòng for để lấy về từng List<Movie>
            cụ thể
            ==> Ta đã có List<Category>
    */
//    private fun loadCategories() {
//        val categories = mutableListOf<Category>()
//
//        runBlocking {
//
//            val genres = Api.retrofitService.getGenres(Constant.API_KEY, Constant.LANGUAGE).genres
//            for (i in genres.indices) {
//                val page = randomPage()
//                val movies = Api.retrofitService.getMoviesByGenre(
//                    Constant.API_KEY, genres[i].id, page
//                ).results
//                categories.add(Category(genres[i], movies))
//            }
//
//        }
//        _categories.postValue(categories)
//
//    }

    private fun loadCategories() {
        val categories = mutableListOf<Category>()

        viewModelScope.launch(Dispatchers.IO) {
            val genres =
                Api.retrofitService.getGenres(Constant.API_KEY, Constant.LANGUAGE).genres
            for (i in genres.indices) {
                val movies = Api.retrofitService.getMoviesByGenre(
                    Constant.API_KEY, genres[i].id, 1
                ).results
                categories.add(Category(genres[i], movies))
            }
            withContext(Dispatchers.Main){
                _categories.postValue(categories)
            }
        }


    }


    fun refresh() {
        val categories = mutableListOf<Category>()

        try {
//            viewModelScope.launch(Dispatchers.IO) {
//                withContext(Dispatchers.IO) {
//                    val genres =
//                        Api.retrofitService.getGenres(Constant.API_KEY, Constant.LANGUAGE).genres
//                    for (i in genres.indices) {
//                        val movies = Api.retrofitService.getMoviesByGenre(
//                            Constant.API_KEY, genres[i].id, 1
//                        ).results
//                        categories.add(Category(genres[i], movies))
//                    }
//                    withContext(Dispatchers.Main) {
//                        _categories.postValue(categories)
//                    }
//                }
//            }
            runBlocking {
                withContext(Dispatchers.IO) {
                    val genres =
                        Api.retrofitService.getGenres(Constant.API_KEY, Constant.LANGUAGE).genres
                    for (i in genres.indices) {
                        val movies = Api.retrofitService.getMoviesByGenre(
                            Constant.API_KEY, genres[i].id, 1
                        ).results
                        categories.add(Category(genres[i], movies))
                    }
                    withContext(Dispatchers.Main) {
                        _categories.postValue(categories)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("ERROR", e.printStackTrace().toString())
        }
    }
}
