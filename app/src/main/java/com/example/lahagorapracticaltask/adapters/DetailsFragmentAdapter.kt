package com.example.lahagorapracticaltask.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lahagorapracticaltask.R
import com.example.lahagorapracticaltask.model.DashboardModel
import com.example.lahagorapracticaltask.preference.Preferences
import com.example.lahagorapracticaltask.utils.Constants
import com.example.lahagorapracticaltask.utils.LogUtils
import com.example.lahagorapracticaltask.utils.Utils
import com.squareup.picasso.Picasso

class DetailsFragmentAdapter(
    val context: Context,
    var courselist: ArrayList<DashboardModel.Index>,
    val preferences: Preferences
) : RecyclerView.Adapter<DetailsFragmentAdapter.DetailsFragmentAdap>(), Filterable {
    private var listFull: ArrayList<DashboardModel.Index> = ArrayList()

    init {
        listFull = courselist
    }

    class DetailsFragmentAdap(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView? = itemView.findViewById(R.id.tvTitle)
        val tvEducator: TextView? = itemView.findViewById(R.id.tvEducator)
        val ivCourse: ImageView? = itemView.findViewById(R.id.ivCourseDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsFragmentAdap {
        val inflater =
            LayoutInflater.from(context).inflate(R.layout.item_details_fragment, parent, false)
        return DetailsFragmentAdap(inflater)
    }

    override fun getItemCount(): Int {
        return courselist.size
    }

    override fun onBindViewHolder(holder: DetailsFragmentAdap, position: Int) {
        val course = courselist[position]
        holder.tvTitle?.text = course.title
        holder.tvEducator?.text = course.educator
        val url = getCourseImageUrl(course.id.toString())
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.logo_placeholder)
            .into(holder.ivCourse)
    }

    private fun getCourseImageUrl(courseId: String): String? {
        val baseUrl = "http://d2xkd1fof6iiv9.cloudfront.net/images/courses/{course_id}/169_820.jpg"
        return baseUrl.replace("{course_id}", courseId)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filteredList = ArrayList<DashboardModel.Index>()

                if (constraint.isEmpty() || constraint.isBlank()) {
                    if (Utils.isFilterApplied) {
                        // Filters are applied, so search in the filtered list.
                        filteredList.addAll(getFilteredList())
                    } else {
                        // No filters are applied, so use the original list.
                        filteredList.addAll(listFull)
                    }
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    if (Utils.isSearchActive) {
                        if (Utils.isFilterApplied) {
                            for (item in getFilteredList()) {
                                if (item.title.toLowerCase().contains(filterPattern)) {
                                    filteredList.add(item)
                                }
                            }
                        } else {
                            for (item in listFull) {
                                if (item.title.toLowerCase().contains(filterPattern)) {
                                    filteredList.add(item)
                                }
                            }
                        }

                    } else {
                        val desiredOwned: String? =
                            if (preferences.getOwned().takeIf { it != "0" } == "Yes") {
                                "1"
                            } else if (preferences.getOwned().takeIf { it != "0" } == "No") {
                                "0"
                            } else {
                                null
                            }
                        val desiredSkill = preferences.getSkill().takeIf { it != "0" }
                        val desiredSeries = preferences.getSeries().takeIf { it != "0" }
                        val desiredStyle = preferences.getStyle().takeIf { it != "0" }
                        val desiredCirriculum = preferences.getCirriculum().takeIf { it != "0" }
                        val desiredEducator = preferences.getEducator().takeIf { it != "0" }

                        Utils.isFilterApplied =
                            desiredSkill != null ||
                                    desiredSeries != null ||
                                    desiredCirriculum != null ||
                                    desiredStyle != null ||
                                    desiredEducator != null ||
                                    desiredOwned != null

                        filteredList.addAll(getFilteredList())

                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                courselist = results.values as ArrayList<DashboardModel.Index>
                Utils.isListEmpty.value = courselist.isEmpty()
                notifyDataSetChanged()
            }
        }

    }

    private fun getFilteredList(): List<DashboardModel.Index> {
        val desiredOwned: String? =
            if (preferences.getOwned().takeIf { it != "0" } == "Yes") {
                "1"
            } else if (preferences.getOwned().takeIf { it != "0" } == "No") {
                "0"
            } else {
                null
            }
        val desiredSkill = preferences.getSkill().takeIf { it != "0" }
        val desiredSeries = preferences.getSeries().takeIf { it != "0" }
        val desiredStyle = preferences.getStyle().takeIf { it != "0" }
        val desiredCirriculum = preferences.getCirriculum().takeIf { it != "0" }
        val desiredEducator = preferences.getEducator().takeIf { it != "0" }

        val desiredList = listFull.filter { item ->
            (desiredOwned == null || item.owned == desiredOwned.toInt()) &&
                    (desiredSkill == null || item.skill_tags.contains(desiredSkill)) &&
                    (desiredSeries == null || item.series_tags.contains(
                        desiredSeries
                    )) &&
                    (desiredStyle == null || item.style_tags.contains(desiredStyle)) &&
                    (desiredCirriculum == null || item.curriculum_tags.contains(
                        desiredCirriculum
                    )) &&
                    (desiredEducator == null || item.educator == desiredEducator)
        }
        return desiredList
    }

}