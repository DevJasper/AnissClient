package com.farelands.aniss.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farelands.aniss.R
import kotlinx.android.synthetic.main.rv_dashboard_card.view.*

class DashboardAdapter(
    private val dashboardItems: ArrayList<DashboardCardItem>,
    private val clickListener: (DashboardCardItem) -> Unit
) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_dashboard_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dashboardItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dashboardItems[position], clickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: DashboardCardItem, clickListener: (DashboardCardItem) -> Unit) {
            itemView.rv_card_title.text = item.title
            itemView.rv_card_image.setImageResource(item.image)
            itemView.setOnClickListener { clickListener(item) }
        }
    }

}