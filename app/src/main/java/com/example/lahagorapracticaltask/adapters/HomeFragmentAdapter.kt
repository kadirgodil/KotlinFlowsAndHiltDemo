package com.example.lahagorapracticaltask.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lahagorapracticaltask.R
import com.example.lahagorapracticaltask.model.DashboardModel
import com.example.lahagorapracticaltask.utils.Constants
import com.example.lahagorapracticaltask.utils.Utils

class HomeFragmentAdapter(
    val context: Context,
    val indexList: ArrayList<DashboardModel.Index>,
    val collections: DashboardModel.Collections,
    val navController: NavController
) : RecyclerView.Adapter<HomeFragmentAdapter.HomeFragmentViewHolder>() {
    class HomeFragmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvViewAll: TextView? = itemView.findViewById(R.id.tvViewAll)
        val tvTitle: TextView? = itemView.findViewById(R.id.tvTitle)
        val rvChild: RecyclerView? = itemView.findViewById(R.id.rvChildItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentViewHolder {
        val inflater =
            LayoutInflater.from(context).inflate(R.layout.item_dashboard_main, parent, false)
        return HomeFragmentViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return collections.smart.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: HomeFragmentViewHolder, position: Int) {
        val data = collections.smart[position]
        holder.tvTitle?.visibility = View.VISIBLE
        holder.tvViewAll?.visibility = View.VISIBLE
        holder.tvTitle?.text = data.label
        var list: ArrayList<DashboardModel.Index?> = ArrayList()
        data.courses.forEach { courses ->
            list.add(indexList.find { it.id == courses })
        }
        if (list.isNotEmpty()) {
            holder.rvChild?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            val homeFragmentHorizontalRvAdapter =
                HomeFragmentHorizontalRvAdapter(context, list)
            holder.rvChild?.adapter = homeFragmentHorizontalRvAdapter
            homeFragmentHorizontalRvAdapter.notifyDataSetChanged()
        } else {
            holder.tvTitle?.visibility = View.GONE
            holder.tvViewAll?.visibility = View.GONE
        }

        holder.tvViewAll?.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList(Constants.COURSELIST, list)
            bundle.putString(Constants.TITLE, data.label)
            navController.navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
        }


    }
}