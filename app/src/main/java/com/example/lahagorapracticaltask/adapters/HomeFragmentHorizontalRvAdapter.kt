package com.example.lahagorapracticaltask.adapters

import android.R.attr.bitmap
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lahagorapracticaltask.R
import com.example.lahagorapracticaltask.model.DashboardModel
import com.squareup.picasso.Picasso
import java.net.URL


class HomeFragmentHorizontalRvAdapter(
    val context: Context,
    val list: ArrayList<DashboardModel.Index?>
) : RecyclerView.Adapter<HomeFragmentHorizontalRvAdapter.HomeFragmentHorizontalRvViewHolder>() {

    class HomeFragmentHorizontalRvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView? = itemView.findViewById(R.id.tvTitle)
        val tvEducator: TextView? = itemView.findViewById(R.id.tvEducator)
        val ivCourse: ImageView? = itemView.findViewById(R.id.ivCourse)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeFragmentHorizontalRvViewHolder {
        val inflater =
            LayoutInflater.from(context)
                .inflate(R.layout.item_dashboard_rv_horizontal, parent, false)
        return HomeFragmentHorizontalRvViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return if (list.size >= 4) {
            4
        } else {
            list.size
        }

    }

    override fun onBindViewHolder(holder: HomeFragmentHorizontalRvViewHolder, position: Int) {
        val course = list[position]
        holder.tvTitle?.text = course?.title
        holder.tvEducator?.text = course?.educator
        val url = getCourseImageUrl(course?.id.toString())
        val uri = Uri.parse(url)
        Picasso.get()
            .load(uri)
            .placeholder(R.drawable.logo_placeholder)
            .into(holder.ivCourse)
    }

    private fun getCourseImageUrl(courseId: String): String? {
        val baseUrl = "http://d2xkd1fof6iiv9.cloudfront.net/images/courses/{course_id}/169_820.jpg"
        return baseUrl.replace("{course_id}", courseId)
    }
}