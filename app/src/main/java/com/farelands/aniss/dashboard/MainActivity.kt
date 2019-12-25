package com.farelands.aniss.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farelands.aniss.R
import com.farelands.aniss.emergencycall.EmergencyCallActivity
import com.farelands.aniss.emergencyvideocall.base.EmergencyVideoCallOptionsActivity
import com.farelands.aniss.feedback.FeedbackActivity
import com.farelands.aniss.getDashboardItems
import com.farelands.aniss.securityinfo.SecurityInfoOptionsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.overScrollMode = View.OVER_SCROLL_NEVER

        val adapter =
            DashboardAdapter(getDashboardItems()) { item: DashboardCardItem ->
                itemClicked(
                    item
                )
            }

        recyclerView.adapter = adapter

    }

    private fun itemClicked(item: DashboardCardItem) {
        when (item.title){
            "Emergency Call" ->{startActivity(Intent(this, EmergencyCallActivity::class.java))}
            "Give Security Info" ->{startActivity(Intent(this, SecurityInfoOptionsActivity::class.java))}
            "Emergency Video Call" ->{startActivity(Intent(this, EmergencyVideoCallOptionsActivity::class.java))}
            "Feedback" ->{startActivity(Intent(this, FeedbackActivity::class.java))}
            else ->{Toast.makeText(this, "Clicked: ${item.title}", Toast.LENGTH_SHORT).show()}
        }
    }

}
