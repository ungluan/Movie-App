package com.example.moviesapp.network.model

data class MoviesResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

/*

@Json(name="page") val page: Int,
    @Json(name="results") val results: List<Movie>,
    @Json(name="total_pages") val total_pages: Int,
    @Json(name="total_results") val total_results: Int

*/