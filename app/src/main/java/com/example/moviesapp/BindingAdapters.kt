package com.example.moviesapp

import android.view.View
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviesapp.adapter.CastAdapter
import com.example.moviesapp.adapter.CategoryAdapter
import com.example.moviesapp.adapter.MovieAdapter
import com.example.moviesapp.network.model.Cast
import com.example.moviesapp.network.model.Category
import com.example.moviesapp.network.model.Movie
import com.makeramen.roundedimageview.RoundedImageView

@BindingAdapter("imageUrl")
fun bindingImage(imgView: RoundedImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri){
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_connection_error)
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
    if (data?.size != 0) {
        val adapter: MovieAdapter = recyclerView.adapter as MovieAdapter
        adapter.submitList(data)
    }
}

@BindingAdapter("listCategory")
fun bindCategoryRecyclerView(recyclerView: RecyclerView, data: List<Category>?) {
    if (data != null && data.isNotEmpty()) {
        val adapter: CategoryAdapter = recyclerView.adapter as CategoryAdapter
        adapter.submitList(data)
    }
}
@BindingAdapter("listCast")
fun bindCastRecyclerView(recyclerView: RecyclerView, data: List<Cast>?){
    if(data!=null && data.isNotEmpty()){
        val adapter: CastAdapter = recyclerView.adapter as CastAdapter
        adapter.submitList(data)
    }
}
@BindingAdapter("visibleState")
fun bindingVisibleProgressBar(progressBar: ProgressBar, isLoading: Boolean = false ){
    var visibility = View.INVISIBLE
    if(isLoading) visibility = View.VISIBLE
    progressBar.visibility = visibility
}