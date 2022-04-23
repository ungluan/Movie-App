package com.example.moviesapp.network.model

import com.squareup.moshi.Json

data class TrailerMovieResponse(
    val id: Int?,
    @Json(name="results")
    val trailers: List<Trailer>?
)