package com.example.lahagorapracticaltask.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lahagorapracticaltask.R
import com.example.lahagorapracticaltask.activities.DashboardActivity
import com.example.lahagorapracticaltask.adapters.HomeFragmentAdapter
import com.example.lahagorapracticaltask.databinding.FragmentHomeBinding
import com.example.lahagorapracticaltask.model.DashboardModel
import com.example.lahagorapracticaltask.model.Resource
import com.example.lahagorapracticaltask.model.Status
import com.example.lahagorapracticaltask.viewmodels.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private val homeFragmentViewModel: HomeFragmentViewModel by viewModels()
    private var homeFragmentAdapter: HomeFragmentAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setupbinding(inflater, container)
        initViews()
        setupObservers()
        return binding?.root
    }

    private fun setupObservers() {
        homeFragmentViewModel.dashboardApiResponse.observe(viewLifecycleOwner) {
            handleDashboardResponse(it)
        }
        homeFragmentViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViews() {
        homeFragmentViewModel.getDashboardList()
    }

    private fun setupbinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding?.lifecycleOwner = this
    }

    private fun handleDashboardResponse(it: Resource<DashboardModel>) {
        when (it.status) {
            Status.SUCCESS -> {
                if (it.data?.record != null) {
                    stopShimmerAnimation()
                    binding?.rvDashboard?.visibility = View.VISIBLE
                    val indexList = it.data.record.result.index
                    val collection = it.data.record.result.collections

                    setupAdapter(indexList, collection)
                } else {
                    stopShimmerAnimation()
                }
            }

            Status.ERROR -> {
                stopShimmerAnimation()
                binding?.rvDashboard?.visibility = View.GONE
            }

            Status.LOADING -> {
                startShimmerAnimation()
                binding?.rvDashboard?.visibility = View.GONE
            }


        }

    }

    private fun setupAdapter(
        list: List<DashboardModel.Index>,
        collection: DashboardModel.Collections
    ) {
        homeFragmentAdapter =
            HomeFragmentAdapter(
                requireActivity(),
                list as ArrayList<DashboardModel.Index>, collection, findNavController()
            )
        binding?.rvDashboard?.adapter = homeFragmentAdapter
        homeFragmentAdapter?.notifyDataSetChanged()
    }

    private fun startShimmerAnimation() {
        binding?.shimmerFrameLayout?.visibility = View.VISIBLE
        binding?.shimmerFrameLayout?.startShimmerAnimation()
    }

    private fun stopShimmerAnimation() {
        binding?.shimmerFrameLayout?.visibility = View.GONE
        binding?.shimmerFrameLayout?.stopShimmerAnimation()
    }

    private fun setupToolbar() {
        (requireActivity() as DashboardActivity).binding?.toolbar?.layoutToolbar?.visibility =
            View.GONE
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }
}