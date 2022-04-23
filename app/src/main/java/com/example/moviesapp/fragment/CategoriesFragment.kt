//package com.example.moviesapp.fragment
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.recyclerview.widget.RecyclerView
//import com.example.moviesapp.adapter.CategoriesAdapter
//import com.example.moviesapp.databinding.ActivityMainBinding
//import com.example.moviesapp.databinding.FragmentCategoriesBinding
//import com.example.moviesapp.viewmodel.CategoryViewModel
//
//class CategoriesFragment : Fragment() {
//    /*
//        - Ôn tập lại?
//        - Trong Fragment sẽ chứa những gì?
//        - Hiện tại trong fragment này cần chứa 1 CategoryViewModel và 1 recyclerView
//        - Catgeory dùng để hiển thị data lên view
//        - recyclerView dùng để cập nhật adapter khi get được data về
//    */
//    private lateinit var _binding: ActivityMainBinding
//    private val categoryViewModel: CategoryViewModel by viewModels()
//    private lateinit var recyclerView: RecyclerView
//
//    // Thực hiện inflate view
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = ActivityMainBinding.inflate(inflater)
//        _binding.categoryViewModel = categoryViewModel.
//        return _binding.root
//    }
//    // Thực hiện binding view
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        recyclerView = _binding.recyclerView
//        recyclerView.adapter = CategoriesAdapter()
//    }
//}