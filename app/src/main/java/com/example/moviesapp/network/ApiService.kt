package com.example.moviesapp.network

import com.example.moviesapp.network.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org"

//public const val API_KEY = "35bd901e4dbc4216b94aa0b5ecca9808"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// This interface how Retrofit talks to the web server using HTTP requests
interface ApiService {
//    @GET("/3/movie/top_rated")
//    suspend fun getTopRateMovies(
//        @Query("api_key") api_key: String,
//        @Query("language") language: String,
//        @Query("page") page: Int
//    ): String

    @GET("/3/genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
    ): GenresResponse

    @GET("/3/trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MoviesResponse

    @GET("/3/discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") api_key: String,
        @Query("with_genres") genre_id: Int,
        @Query("page") page: Int
    ): MoviesResponse

    // movie/{movie_id}?api_key=<<api_key>>&language=en-US
    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Movie

    @GET("/3/movie/{movie_id}/videos")
    suspend fun getTrailerMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String,
    ): TrailerMovieResponse

    @GET("/3/movie/{movie_id}/credits")
    suspend fun getCastMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): CastMovieResponse

    @GET("/3/movie/{movie_id}/similar")
    suspend fun getSimilarMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): MoviesResponse

}

object Api {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}