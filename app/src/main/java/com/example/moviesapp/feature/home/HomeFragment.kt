package com.example.moviesapp.feature.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moviesapp.R
import com.example.moviesapp.adapter.CategoryAdapter
import com.example.moviesapp.bindCategoryRecyclerView
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.viewmodel.CategoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private val categoryViewModel: CategoryViewModel by viewModels()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recycler View
        recyclerView = binding.recyclerView
        recyclerView.adapter = CategoryAdapter()
        val linearLayoutManager = LinearLayoutManager(
            requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager

        categoryViewModel.categories.observe(viewLifecycleOwner){
            bindCategoryRecyclerView(recyclerView,it)
            binding.progressBar.visibility = View.INVISIBLE
            binding.swipeRefreshLayout.isRefreshing = false
        }

        // SwipeRefreshLayout
        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            // Ta cần chạy 1 luồng khác đợi nó reload data rồi cho recycler view Update
            lifecycleScope.launch(Dispatchers.IO) {
                categoryViewModel.refresh()
                bindCategoryRecyclerView(recyclerView,categoryViewModel.categories.value)
            }
        }

    }
}
