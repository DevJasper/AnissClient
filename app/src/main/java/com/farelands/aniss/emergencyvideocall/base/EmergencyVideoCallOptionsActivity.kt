package com.farelands.aniss.emergencyvideocall.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farelands.aniss.AppConstants.CALL_TYPE
import com.farelands.aniss.R
import com.farelands.aniss.dashboard.DashboardAdapter
import com.farelands.aniss.dashboard.DashboardCardItem
import com.farelands.aniss.getEmergencyVideoCallItems
import kotlinx.android.synthetic.main.activity_security_info_options.*

class EmergencyVideoCallOptionsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_video_call_options)

        toolbar.setNavigationIcon(R.drawable.ic_back)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.rv)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.overScrollMode = View.OVER_SCROLL_NEVER

        val adapter =
            DashboardAdapter(getEmergencyVideoCallItems()) { item: DashboardCardItem ->
                itemClicked(
                    item
                )
            }

        recyclerView.adapter = adapter

    }

    private fun itemClicked(item: DashboardCardItem) {
        val intent = Intent(this, InitVideoCallActivity::class.java)
        intent.putExtra(CALL_TYPE, item.title)
        startActivity(intent)
    }

}
