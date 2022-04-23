package com.example.moviesapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.MovieDetailActivity
import com.example.moviesapp.databinding.ItemMovieBinding
import com.example.moviesapp.network.model.Movie

//private const val IMAGE_URL = "https://image.tmdb.org/t/p/original"


class MovieAdapter : ListAdapter<Movie,MovieAdapter.TrendingMovieViewHolder>(DiffCallback){

    class TrendingMovieViewHolder(private var binding: ItemMovieBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(movie: Movie){
                binding.movie = movie
                binding.executePendingBindings()
                // Call Navigation
                binding.itemPoster.setOnClickListener {
                    navigateToDetails(movie)
                }
            }
            private fun navigateToDetails(movie: Movie){
//                Toast.makeText(binding.root.context, "movie_id", Toast.LENGTH_SHORT ).show()
                val intent = Intent(binding.root.context, MovieDetailActivity::class.java)
                intent.putExtra("movie_id",movie.id)
                binding.root.context.startActivities(arrayOf(intent))
            }
        }
    // Inflate Item_View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMovieViewHolder {
        return TrendingMovieViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context)))
    }
    // Bind Item_View
    override fun onBindViewHolder(holder: TrendingMovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
}

// Movie -> Genre
//class TrendingMovieAdapter(private val context: Context, private var movies: List<Movie>) :
//    RecyclerView.Adapter<TrendingMovieAdapter.TrendingMovieViewHolder>() {
//    private lateinit var _binding: ItemLayoutBinding
//    /*
//        - Câu hỏi đặt ra là TrendingMovieAdapter có chứa những gì?
//        - Công việc của nó là chuyển đổi 1 data Movie thành 1 Item.
//        - 1 Class Adapter có chứa 1 Class con là ViewHolder là nơi chứa Item, nhằm mục đích
//        tái sử dụng tăng performance
//    */
//    class TrendingMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val itemTitle: TextView = itemView.findViewById(R.id.item_title)
//        val item_poster = itemView.findViewById<RoundedImageView>(R.id.item_poster)
//    }
//    //  - Sau khi extend RecyclerView.Adapter thì ta cần phải Implement 3 func dưới đây
//
//    // Fun này dùng để Inflate View và sau đó tạo 1 ViewHolder(VIEW)
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMovieViewHolder {
//        val view = LayoutInflater.from(context)
//            .inflate(R.layout.item_layout ,parent ,false)
//        _binding = ItemLayoutBinding.bind(view)
//        return TrendingMovieViewHolder(view)
//    }
//
//    // Sau khi Inflate xong thì ta tiến hành binding các view trong
//    override fun onBindViewHolder(holder: TrendingMovieViewHolder, position: Int) {
//        val movie = movies[position]
//        holder.itemTitle.text = movie.title
//        _binding.viewModel = movie
//    }
//
//    override fun getItemCount(): Int = movies.size
//
//    fun updateMovies(newMovies: List<Movie>){
//        movies = newMovies
//    }
//}