package com.example.moviesapp.network.model

import java.io.Serializable

// Get Trailer
// https://api.themoviedb.org/3/movie/414906/videos?api_key=35bd901e4dbc4216b94aa0b5ecca9808
// Get Details Movie
// https://api.themoviedb.org/3/movie/{movie_id}?api_key=<<api_key>>&language=en-US
data class Movie(
    val adult: Boolean?,
    val backdrop_path: String?,
    val genres: List<Genre>?,
    val budget: Long?,
    val runtime: Int?,
    // "budget": 185000000,
    /*
    "genres": [
        {
            "id": 28,
            "name": "Action"
        },
        {
            "id": 80,
            "name": "Crime"
        },
        {
            "id": 18,
            "name": "Drama"
        }
    ],
    */
    // runtime
    val genre_ids: List<Int>?,
    val id: Int?,
    val media_type: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
) : Serializable
// Tại sao phải cho dataClass này kế thừa Serializable?
// --> Serializable là cơ chế chuyển đổi trạng thái của 1 đối tượng thành 1 chuổi byte sao cho chuổi byte
// này có thể chuyển đổi ngược lại thành đối tượng
// Tại sao phải cần Serializable?
// --> Trong Java khi trao đổi dữ liệu giữa các module cùng viết bằng java, thì dữ liệu được thể hiện
// dưới dạng byte chứ không phải đối tượng

/*
    @Json(name="adult") val adult: Boolean,
    @Json(name="backdrop_path") val backdrop_path: String,
    @Json(name="genre_ids") val genre_ids: List<Int>,
    @Json(name="id") val id: Int,
    @Json(name="media_type") val media_type: String,
    @Json(name="original_language") val original_language: String,
    @Json(name="original_title") val original_title: String,
    @Json(name="overview") val overview: String,
    @Json(name="popularity") val popularity: Double,
    @Json(name="poster_path") val poster_path: String,
    @Json(name="release_date") val release_date: String,
    @Json(name="title") val title: String,
    @Json(name="video") val video: Boolean,
    @Json(name="vote_average") val vote_average: Double,
    @Json(name="vote_count") val vote_count: Int
*/