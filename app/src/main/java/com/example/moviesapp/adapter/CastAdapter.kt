package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.ItemCastBinding
import com.example.moviesapp.network.model.Cast

class CastAdapter: ListAdapter<Cast, CastAdapter.CastViewHolder>(DiffCallback) {
    class CastViewHolder(private val binding: ItemCastBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(cast: Cast){
            binding.cast = cast
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(
            ItemCastBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = getItem(position)
        holder.bind(cast)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Cast>(){
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.name == newItem.name
        }

    }
}