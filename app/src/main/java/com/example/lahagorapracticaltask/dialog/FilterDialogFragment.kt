package com.example.lahagorapracticaltask.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.lahagorapracticaltask.R
import com.example.lahagorapracticaltask.adapters.FilterDialogAdapter
import com.example.lahagorapracticaltask.databinding.DialogFilterBinding
import com.example.lahagorapracticaltask.listners.FilterListner
import com.example.lahagorapracticaltask.preference.Preferences
import com.example.lahagorapracticaltask.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilterDialogFragment() : BottomSheetDialogFragment() {
    @Inject
    lateinit var preferences: Preferences
    private var binding: DialogFilterBinding? = null
    private var filterlist: ArrayList<String> = ArrayList()
    private var filterListner: FilterListner? = null
    private var type: String? = null
    private var filterDialogAdapter: FilterDialogAdapter? = null

    companion object {
        val TAG = FilterDialogFragment::class.java.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupBinding(inflater, container)
        return binding?.root
    }

    private fun setupBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_filter, container, false)
        binding?.lifecycleOwner = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        filterlist = arguments?.getStringArrayList(Constants.FILTER_LIST) as ArrayList<String>
        type = arguments?.getString(Constants.TYPE)
        if (filterlist.isNotEmpty()) {
            binding?.rvFilterItemList?.visibility = View.VISIBLE
            binding?.tvDataNotFound?.visibility = View.GONE
            setupAdapter()
        } else {
            binding?.rvFilterItemList?.visibility = View.GONE
            binding?.tvDataNotFound?.visibility = View.VISIBLE
        }
    }

    fun setListner(filterListner: FilterListner) {
        this.filterListner = filterListner
    }

    private fun setupAdapter() {
        filterDialogAdapter = filterListner?.let {
            type?.let { it1 ->
                FilterDialogAdapter(
                    requireActivity(),
                    filterlist,
                    it1,
                    it,
                    preferences,
                    this
                )
            }
        }
        binding?.rvFilterItemList?.adapter = filterDialogAdapter
        filterDialogAdapter?.notifyDataSetChanged()
    }


}