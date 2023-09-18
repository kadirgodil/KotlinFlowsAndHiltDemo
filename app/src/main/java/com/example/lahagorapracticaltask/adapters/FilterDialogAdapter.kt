package com.example.lahagorapracticaltask.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.lahagorapracticaltask.R
import com.example.lahagorapracticaltask.dialog.FilterDialogFragment
import com.example.lahagorapracticaltask.listners.FilterListner
import com.example.lahagorapracticaltask.preference.Preferences
import com.example.lahagorapracticaltask.utils.Constants
import com.example.lahagorapracticaltask.utils.Utils

class FilterDialogAdapter(
    val context: Context,
    val filterList: ArrayList<String>,
    val type: String,
    val filterListner: FilterListner,
    val preferences: Preferences,
    val dialog: FilterDialogFragment
) : RecyclerView.Adapter<FilterDialogAdapter.FilterViewHolder>() {
    class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView? = itemView.findViewById(R.id.tvFilter)
        val layoutParent: ConstraintLayout? = itemView.findViewById(R.id.layoutParent)
        val ivFilterApplied: ImageView? = itemView.findViewById(R.id.ivFilterApplied)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val inflater =
            LayoutInflater.from(context).inflate(R.layout.item_dialog_filters, parent, false)
        return FilterViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val item = filterList[position]
        if (item.isNullOrEmpty() || item.isNullOrBlank()) {
            holder.tvTitle?.visibility = View.GONE
        } else {
            holder.tvTitle?.visibility = View.VISIBLE
        }
        holder.tvTitle?.text = item
        holder.ivFilterApplied?.visibility = View.GONE
        when (type) {
            Constants.ONLY_SHOW_OWNED -> {
                if (item == preferences.getOwned()) {
                    holder.ivFilterApplied?.visibility = View.VISIBLE
                }
            }

            Constants.SKILL -> {
                if (item == preferences.getSkill()) {
                    holder.ivFilterApplied?.visibility = View.VISIBLE
                }
            }

            Constants.SERIES -> {
                if (item == preferences.getSeries()) {
                    holder.ivFilterApplied?.visibility = View.VISIBLE
                }
            }

            Constants.STYLE -> {
                if (item == preferences.getStyle()) {
                    holder.ivFilterApplied?.visibility = View.VISIBLE
                }
            }

            Constants.CIRRICULUM -> {
                if (item == preferences.getCirriculum()) {
                    holder.ivFilterApplied?.visibility = View.VISIBLE
                }
            }

            Constants.EDUCATOR -> {
                if (item == preferences.getEducator()) {
                    holder.ivFilterApplied?.visibility = View.VISIBLE
                }
            }
        }
        holder.layoutParent?.setOnClickListener {
            when (type) {
                Constants.ONLY_SHOW_OWNED -> {
                    if (item == preferences.getOwned()) {
                        preferences.remove(Constants.PREF_ONLY_SHOW_OWNED)
                        Utils.isFilterRemoved = true
                    } else {
                        preferences.putOwned(item)
                    }
                    filterListner.onFilterApplied(type)
                    dialog.dismiss()
                }

                Constants.SKILL -> {
                    if (item == preferences.getSkill()) {
                        preferences.remove(Constants.SKILL)
                        Utils.isFilterRemoved = true
                    } else {
                        preferences.putSkill(item)
                    }
                    filterListner.onFilterApplied(type)
                    dialog.dismiss()
                }

                Constants.STYLE -> {
                    if (item == preferences.getStyle()) {
                        preferences.remove(Constants.STYLE)
                        Utils.isFilterRemoved = true
                    } else {
                        preferences.putStyle(item)
                    }
                    filterListner.onFilterApplied(type)
                    dialog.dismiss()
                }

                Constants.SERIES -> {
                    if (item == preferences.getSeries()) {
                        preferences.remove(Constants.SERIES)
                        Utils.isFilterRemoved = true
                    } else {
                        preferences.putSeries(item)
                    }
                    filterListner.onFilterApplied(type)
                    dialog.dismiss()
                }

                Constants.CIRRICULUM -> {
                    if (item == preferences.getCirriculum()) {
                        preferences.remove(Constants.CIRRICULUM)
                        Utils.isFilterRemoved = true
                    } else {
                        preferences.putCirriculum(item)
                    }
                    filterListner.onFilterApplied(type)
                    dialog.dismiss()
                }

                Constants.EDUCATOR -> {
                    if (item == preferences.getEducator()) {
                        preferences.remove(Constants.EDUCATOR)
                        Utils.isFilterRemoved = true
                    } else {
                        preferences.putEducator(item)
                    }
                    filterListner.onFilterApplied(type)
                    dialog.dismiss()
                }
            }
        }


    }
}