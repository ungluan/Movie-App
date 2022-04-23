package com.example.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.adapter.CastAdapter
import com.example.moviesapp.adapter.MovieAdapter
import com.example.moviesapp.databinding.ActivityMovieDetailBinding
import com.example.moviesapp.network.model.Genre
import com.example.moviesapp.viewmodel.MovieDetailsViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class MovieDetailActivity : AppCompatActivity() {

//    val movieDetailsViewModel: MovieDetailsViewModel by viewModels()

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels<MovieDetailsViewModel>{
        val movieId = intent?.extras?.getInt("movie_id")
        MovieDetailsViewModel( movieId!!   )
    }
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var chipGroup: ChipGroup
    private lateinit var recyclerViewCast: RecyclerView
    private lateinit var recyclerViewMovieSimilar: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_movie_detail)
        binding.movieDetailsViewModel = movieDetailsViewModel
        chipGroup = binding.chipGroupGenres
        binding.lifecycleOwner = this
        // Listen movie
        movieDetailsViewModel.movie.observe(this){
            it.genres?.forEach { genre ->
                addChips(genre)
            }
        }

//        movieDetailsViewModel.casts.observe(this){
//            bindCastRecyclerView(recyclerViewCast, it)
//        }

        recyclerViewCast = binding.recyclerViewCast
        recyclerViewCast.adapter = CastAdapter()
        val layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        recyclerViewCast.layoutManager = layoutManager

//        movieDetailsViewModel.similarMovies.observe(this){
//            bindRecyclerView(recyclerViewMovieSimilar,it)
//        }

        recyclerViewMovieSimilar = binding.recyclerViewSimilarMovie
        recyclerViewMovieSimilar.adapter = MovieAdapter()
        val layoutManagerSimilarMovie = LinearLayoutManager(this,RecyclerView.HORIZONTAL, false)
        recyclerViewMovieSimilar.layoutManager = layoutManagerSimilarMovie

        binding.btnCirclePlay.setOnClickListener {
            BottomSheetFragment(movieDetailsViewModel.trailer.value?.key!!)
                .show(supportFragmentManager,"BottomSheetFragment")
        }
    }
    private fun addChips(genre: Genre){
        val chip: Chip = this.layoutInflater.inflate(R.layout.item_chip_genre,null,false) as Chip
        chip.text = genre.name
        chipGroup.addView(chip)
    }
}