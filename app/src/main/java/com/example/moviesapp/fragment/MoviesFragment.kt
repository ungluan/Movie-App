//package com.example.moviesapp.fragment
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ProgressBar
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.viewModels
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.moviesapp.R
//import com.example.moviesapp.adapter.MovieAdapter
//import com.example.moviesapp.databinding.FragmentMoviesBinding
//import com.example.moviesapp.viewmodel.MovieViewModel
//
//
//class MoviesFragment : Fragment() {
//    private lateinit var _binding: FragmentMoviesBinding
//    private val binding get() = _binding
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var progressBar: ProgressBar
//    private val movieViewModel: MovieViewModel by viewModels()
//
//    /*
//        Status STARTED
//        Có 3 funtion onCreate - onCreateView - onViewCreated
//        - onCreate: Call hàm này với mục đích khởi tạo instance của FragmentMoviesBinding
//        - onCreateView: Được gọi sau hàm onCreate dùng để inflate layout
//        - onViewCreated: Được gọi sau khi view đã được tạo, dùng để bind specific views
//    */
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        // Call Get API in Here then??
//        setHasOptionsMenu(true)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = DataBindingUtil.inflate<FragmentMoviesBinding>(
//            inflater,
//            R.layout.fragment_movies,
//            container,
//            false
//        )
//        _binding.lifecycleOwner = this
//        _binding.movieViewModel = movieViewModel
//        // or Call In here
//        return binding.root
//    }
//
//    // Sau khi view tạo thành công
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        recyclerView = _binding.recyclerView
//        progressBar = _binding.progressBar
//        // Dùng sai context -> Lỗi
//        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
//        recyclerView.layoutManager = layoutManager
//        recyclerView.adapter = MovieAdapter()
//
////        recyclerView.setHasFixedSize(true)
////        movieViewModel.moviesResponse.observe(viewLifecycleOwner) {
////            progressBar.visibility = ProgressBar.INVISIBLE
////        }
//    }
//
//}