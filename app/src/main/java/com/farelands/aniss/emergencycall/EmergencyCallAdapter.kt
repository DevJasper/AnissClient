package com.farelands.aniss.emergencycall

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farelands.aniss.R
import kotlinx.android.synthetic.main.rv_dashboard_card.view.*

class EmergencyCallAdapter (
    private val emergencyCallItem: ArrayList<EmergencyCallItem>,
    private val clickListener: (EmergencyCallItem) -> Unit
):
    RecyclerView.Adapter<EmergencyCallAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_emergency_call_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return emergencyCallItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(emergencyCallItem[position], clickListener)
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item: EmergencyCallItem, clickListener: (EmergencyCallItem) -> Unit) {
            itemView.rv_card_title.text = item.title
            itemView.setOnClickListener { clickListener(item) }
        }
    }


}