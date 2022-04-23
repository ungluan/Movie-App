package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.ItemCategoryBinding
import com.example.moviesapp.network.model.Category

class CategoryAdapter :
    ListAdapter<Category, CategoryAdapter.CategoriesViewHolder>(DiffCallback) {

    class CategoriesViewHolder(private var binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.category = category
            val linearLayoutManager = LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL,false)
            binding.recyclerView.adapter = MovieAdapter()
            binding.recyclerView.layoutManager = linearLayoutManager
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoriesViewHolder {
        return CategoriesViewHolder(
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoriesViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.movies == newItem.movies
        }

    }

}