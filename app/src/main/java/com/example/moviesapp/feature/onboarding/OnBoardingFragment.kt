package com.example.moviesapp.feature.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesapp.Constant
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentOnBoardingBinding
import com.example.moviesapp.feature.onboarding.adapter.OnBoardingAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding
    private var positionCurrent = 0
    private lateinit var listFragment: List<Fragment>
    private lateinit var viewPager: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = binding.viewPage2

        setUpViewPager(viewPager)
        setUpTabLayout(viewPager)
        setUpBtnOnBoarding(viewPager)
        setUpTxtSkip()
    }

    private fun setUpViewPager(viewPager: ViewPager2) {
        listFragment = mutableListOf(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment(),
        )
        val onBoardingAdapter = OnBoardingAdapter(
            listFragment,
            requireActivity().supportFragmentManager,
            this.lifecycle
        )
        viewPager.adapter = onBoardingAdapter
    }

    private fun setUpTabLayout(viewPager: ViewPager2) {
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { _, _ ->
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position == listFragment.size - 1) {
                    binding.btnNext.text = getString(R.string.on_boarding_button_get_started)
                    binding.txtSkip.visibility = View.INVISIBLE
                } else {
                    binding.btnNext.text = getString(R.string.on_boarding_button_next)
                    binding.txtSkip.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setUpTxtSkip() {
        binding.txtSkip.setOnClickListener {
            viewPager.currentItem = listFragment.size - 1
        }
    }

    private fun setUpBtnOnBoarding(viewPager: ViewPager2) {
        binding.btnNext.setOnClickListener {
            if (viewPager.currentItem != listFragment.size - 1) {
                positionCurrent = viewPager.currentItem++
            } else {
                val action =
                    OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment(null, null)
                findNavController().navigate(action)
                // Call Save Data SharedPreferences here
                savedStateOnBinding()
            }
        }
    }

    private fun savedStateOnBinding() {
        val sharedPref = requireActivity().getSharedPreferences(
            Constant.ON_BOARDING, Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.putBoolean(Constant.ON_BOARDING_FINISHED, true)
        editor.apply()
    }
}