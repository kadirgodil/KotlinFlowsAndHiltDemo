package com.example.lahagorapracticaltask.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.lahagorapracticaltask.R
import com.example.lahagorapracticaltask.activities.DashboardActivity
import com.example.lahagorapracticaltask.dialog.FilterDialogFragment
import com.example.lahagorapracticaltask.listners.FilterListner
import com.example.lahagorapracticaltask.model.FilterModel
import com.example.lahagorapracticaltask.preference.Preferences
import com.example.lahagorapracticaltask.utils.Constants
import com.example.lahagorapracticaltask.utils.Utils

class FiltersAdapter(
    val context: Context,
    val filterList: ArrayList<FilterModel>,
    val ownedList: ArrayList<String>,
    val skillTags: ArrayList<String>,
    val seriesTags: ArrayList<String>,
    val styleTags: ArrayList<String>,
    val cirriculumTags: ArrayList<String>,
    val educatorsList: ArrayList<String>,
    val filterListner: FilterListner,
    val preferences: Preferences
) : RecyclerView.Adapter<FiltersAdapter.FilterViewHolder>() {
    class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView? = itemView.findViewById(R.id.tvFilter)
        val layoutParent: ConstraintLayout? = itemView.findViewById(R.id.layoutParent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val inflater =
            LayoutInflater.from(context).inflate(R.layout.item_filters, parent, false)
        return FilterViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filter = filterList[position]

        holder.tvTitle?.text = "${filter.title}"
        if (holder.tvTitle?.text == Constants.CLEAR_FILTER) {
            if (Utils.isFilterApplied) {
                holder.layoutParent?.visibility = View.VISIBLE
                holder.tvTitle?.visibility = View.VISIBLE
            } else {
                holder.layoutParent?.visibility = View.GONE
                holder.tvTitle?.visibility = View.GONE
            }
        }
        if (filter.isSelected) {
            holder.tvTitle?.background =
                context.resources.getDrawable(R.drawable.selector_filters_selected)
            holder.tvTitle?.setTextColor(context.resources.getColor(R.color.black))
            holder.tvTitle?.text = "${filter.title}"
        } else {
            holder.tvTitle?.background =
                context.resources.getDrawable(R.drawable.selector_filters)
            holder.tvTitle?.setTextColor(context.resources.getColor(R.color.white))
        }
        holder.layoutParent?.setOnClickListener {
            showBottomSheetDialog(filter.title)
        }

    }

    fun updateSelection(position: Int, value: Boolean) {
        filterList[position].isSelected = value
        notifyDataSetChanged()
    }

    fun clearSelection() {
        filterList.forEach {
            it.isSelected = false
        }
        notifyDataSetChanged()
    }

    private fun showBottomSheetDialog(type: String) {
        when (type) {
            Constants.ONLY_SHOW_OWNED -> {
                setupDialog(type, ownedList)
            }

            Constants.SKILL -> {
                setupDialog(type, skillTags)
            }

            Constants.STYLE -> {
                setupDialog(type, styleTags)
            }

            Constants.SERIES -> {
                setupDialog(type, seriesTags)
            }

            Constants.CIRRICULUM -> {
                setupDialog(type, cirriculumTags)
            }

            Constants.EDUCATOR -> {
                setupDialog(type, educatorsList)
            }

            Constants.CLEAR_FILTER -> {
                filterListner.onFilterApplied(type)
            }
        }
    }

    private fun setupDialog(type: String, filterlist: ArrayList<String>) {
        val fragInfo = FilterDialogFragment()
        val bundle = Bundle()
        bundle.putString(Constants.TYPE, type)
        bundle.putStringArrayList(Constants.FILTER_LIST, filterlist)
        fragInfo.arguments = bundle
        fragInfo.setListner(filterListner)
        (context as DashboardActivity).supportFragmentManager.beginTransaction()
            .add(fragInfo, FilterDialogFragment.TAG).commit()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}