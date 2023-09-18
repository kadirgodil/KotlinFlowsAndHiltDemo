package com.example.lahagorapracticaltask.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lahagorapracticaltask.R
import com.example.lahagorapracticaltask.activities.DashboardActivity
import com.example.lahagorapracticaltask.adapters.DetailsFragmentAdapter
import com.example.lahagorapracticaltask.adapters.FiltersAdapter
import com.example.lahagorapracticaltask.databinding.FragmentDetailsBinding
import com.example.lahagorapracticaltask.listners.FilterListner
import com.example.lahagorapracticaltask.model.DashboardModel
import com.example.lahagorapracticaltask.model.FilterModel
import com.example.lahagorapracticaltask.preference.Preferences
import com.example.lahagorapracticaltask.utils.Constants
import com.example.lahagorapracticaltask.utils.LogUtils
import com.example.lahagorapracticaltask.utils.Utils
import com.example.lahagorapracticaltask.viewmodels.DetailsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment(), FilterListner {
    private var binding: FragmentDetailsBinding? = null
    private val detailsFragmentViewModel: DetailsFragmentViewModel by viewModels()
    private var courseList: ArrayList<DashboardModel.Index> = ArrayList()
    private var detailsAdapter: DetailsFragmentAdapter? = null
    private var filtersAdapter: FiltersAdapter? = null
    private var styleTagList: ArrayList<String> = ArrayList()
    private var skillTagList: ArrayList<String> = ArrayList()
    private var seriesTagList: ArrayList<String> = ArrayList()
    private var curriculmTagList: ArrayList<String> = ArrayList()
    private var educatorList: ArrayList<String> = ArrayList()
    private var ownedList: ArrayList<String> = ArrayList()
    private var filterTitles: ArrayList<FilterModel> = ArrayList()
    private var filteredCourseList: ArrayList<DashboardModel.Index> = ArrayList()
    private var title: String? = null

    @Inject
    lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setupBinding(inflater, container)
        initViews()
        setupObservers()
        return binding?.root
    }

    private fun setupObservers() {
        detailsFragmentViewModel.searchQuery.observe(viewLifecycleOwner) {
            Utils.isSearchActive = true
            detailsAdapter?.filter?.filter(it)
        }


        Utils.isListEmpty.observe(viewLifecycleOwner) {
            if (it) {
                if (Utils.isSearchActive) {
                    binding?.rvDetails?.visibility = View.GONE
                    binding?.rvFilters?.visibility = View.GONE
                    binding?.tvDataNotFound?.visibility = View.VISIBLE
                } else {
                    binding?.rvDetails?.visibility = View.GONE
                    binding?.tvDataNotFound?.visibility = View.VISIBLE
                }
            } else {
                if (Utils.isSearchActive) {
                    binding?.rvDetails?.visibility = View.VISIBLE
                    binding?.rvFilters?.visibility = View.VISIBLE
                    binding?.tvDataNotFound?.visibility = View.GONE
                } else {
                    binding?.rvDetails?.visibility = View.VISIBLE
                    binding?.tvDataNotFound?.visibility = View.GONE
                }

            }
        }
    }

    private fun initViews() {
        courseList = arguments?.getParcelableArrayList(Constants.COURSELIST)!!
        title = arguments?.getString(Constants.TITLE)
        setupFilters()
        if (courseList.isNotEmpty()) {
            binding?.rvDetails?.visibility = View.VISIBLE
            binding?.rvFilters?.visibility = View.VISIBLE
            setupAdapter()
            setupFilterLists()
            setupFiltersAdapters()
        } else {
            binding?.rvDetails?.visibility = View.GONE
            binding?.rvFilters?.visibility = View.GONE
            binding?.tvDataNotFound?.visibility = View.VISIBLE
        }
    }

    private fun setupFiltersAdapters() {
        filtersAdapter = FiltersAdapter(
            requireActivity(),
            filterTitles,
            ownedList,
            skillTagList,
            seriesTagList,
            styleTagList,
            curriculmTagList,
            educatorList,
            this, preferences
        )
        binding?.rvFilters?.adapter = filtersAdapter
        filtersAdapter?.notifyDataSetChanged()
    }

    private fun setupFilters() {
        filterTitles.add(FilterModel(Constants.CLEAR_FILTER, 0, false))
        filterTitles.add(FilterModel(Constants.ONLY_SHOW_OWNED, 1, false))
        filterTitles.add(FilterModel(Constants.SKILL, 2, false))
        filterTitles.add(FilterModel(Constants.CIRRICULUM, 3, false))
        filterTitles.add(FilterModel(Constants.STYLE, 4, false))
        filterTitles.add(FilterModel(Constants.SERIES, 5, false))
        filterTitles.add(FilterModel(Constants.EDUCATOR, 6, false))

    }

    private fun setupFilterLists() {
        courseList.forEach { courses ->
            courses.style_tags.forEach { styles ->
                if (!styleTagList.contains(styles)) {
                    styleTagList.add(styles)
                }
            }
            courses.skill_tags.forEach { skills ->
                if (!skillTagList.contains(skills)) {
                    skillTagList.add(skills)
                }
            }
            courses.curriculum_tags.forEach { curriculm ->
                if (!curriculmTagList.contains(curriculm)) {
                    curriculmTagList.add(curriculm)
                }
            }
            courses.series_tags.forEach { series ->
                if (!seriesTagList.contains(series)) {
                    seriesTagList.add(series)
                }
            }
            if (!educatorList.contains(courses.educator)) {
                educatorList.add(courses.educator)
            }

        }
        ownedList.add("Yes")
        ownedList.add("No")

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupAdapter() {
        LogUtils.Print("TAG>>COURSELIST", courseList.size.toString())
        detailsAdapter = DetailsFragmentAdapter(requireActivity(), courseList, preferences)
        binding?.rvDetails?.adapter = detailsAdapter
        detailsAdapter?.notifyDataSetChanged()
    }


    private fun setupBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding?.lifecycleOwner = this
        binding?.mDetailsVm = detailsFragmentViewModel
    }

    private fun setupToolbar() {
        (requireActivity() as DashboardActivity).binding?.toolbar?.layoutToolbar?.visibility =
            View.VISIBLE
        (requireActivity() as DashboardActivity).binding?.toolbar?.tvTitle?.text = title
        (requireActivity() as DashboardActivity).binding?.toolbar?.ivBack?.visibility = View.VISIBLE
        (requireActivity() as DashboardActivity).binding?.toolbar?.ivBack?.setOnClickListener {
            (requireActivity() as DashboardActivity).onBackPressed()
        }

    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun onFilterApplied(type: String) {
        Utils.isSearchActive = false
        when (type) {
            Constants.ONLY_SHOW_OWNED -> {
                detailsAdapter?.filter?.filter(preferences.getOwned())
                if (Utils.isFilterRemoved) {
                    Utils.isFilterRemoved = false
                    filtersAdapter?.updateSelection(1, false)
                } else {
                    Utils.isFilterApplied = true
                    filtersAdapter?.updateSelection(1, true)
                }
                filtersAdapter?.notifyDataSetChanged()
            }

            Constants.SKILL -> {
                detailsAdapter?.filter?.filter(preferences.getSkill())
                if (Utils.isFilterRemoved) {
                    Utils.isFilterRemoved = false
                    filtersAdapter?.updateSelection(2, false)
                } else {
                    Utils.isFilterApplied = true
                    filtersAdapter?.updateSelection(2, true)
                }
                filtersAdapter?.notifyDataSetChanged()
            }

            Constants.STYLE -> {
                detailsAdapter?.filter?.filter(preferences.getStyle())
                if (Utils.isFilterRemoved) {
                    Utils.isFilterRemoved = false
                    filtersAdapter?.updateSelection(4, false)
                } else {
                    Utils.isFilterApplied = true
                    filtersAdapter?.updateSelection(4, true)
                }
                filtersAdapter?.notifyDataSetChanged()
            }

            Constants.SERIES -> {
                detailsAdapter?.filter?.filter(preferences.getSeries())

                if (Utils.isFilterRemoved) {
                    Utils.isFilterRemoved = false
                    filtersAdapter?.updateSelection(5, false)
                } else {
                    Utils.isFilterApplied = true
                    filtersAdapter?.updateSelection(5, true)
                }
                filtersAdapter?.notifyDataSetChanged()
            }

            Constants.CIRRICULUM -> {
                detailsAdapter?.filter?.filter(preferences.getCirriculum())
                if (Utils.isFilterRemoved) {
                    Utils.isFilterRemoved = false
                    filtersAdapter?.updateSelection(3, false)
                } else {
                    Utils.isFilterApplied = true
                    filtersAdapter?.updateSelection(3, true)
                }
                filtersAdapter?.notifyDataSetChanged()
            }

            Constants.EDUCATOR -> {
                detailsAdapter?.filter?.filter(preferences.getEducator())
                if (Utils.isFilterRemoved) {
                    Utils.isFilterRemoved = false
                    filtersAdapter?.updateSelection(6, false)
                } else {
                    Utils.isFilterApplied = true
                    filtersAdapter?.updateSelection(6, true)
                }
                filtersAdapter?.notifyDataSetChanged()
            }

            Constants.CLEAR_FILTER -> {
                preferences.clear()
                filteredCourseList.clear()
                detailsAdapter?.filter?.filter("")
                Utils.isFilterApplied = false
                filtersAdapter?.clearSelection()
                filtersAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        preferences.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        preferences.clear()
    }

}