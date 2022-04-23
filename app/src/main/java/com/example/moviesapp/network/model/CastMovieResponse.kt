package com.example.moviesapp.network.model

data class CastMovieResponse(
    val cast: List<Cast>?,
    val crew: List<Crew>?,
    val id: Int?
)