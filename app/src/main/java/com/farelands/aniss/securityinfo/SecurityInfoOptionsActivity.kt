package com.farelands.aniss.securityinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farelands.aniss.AppConstants
import com.farelands.aniss.AppConstants.EXTRAS
import com.farelands.aniss.R
import com.farelands.aniss.dashboard.DashboardAdapter
import com.farelands.aniss.dashboard.DashboardCardItem
import com.farelands.aniss.emergencycall.EmergencyCallActivity
import com.farelands.aniss.emergencyvideocall.base.EmergencyVideoCallOptionsActivity
import com.farelands.aniss.feedback.FeedbackActivity
import com.farelands.aniss.getSecurityInfoItems
import kotlinx.android.synthetic.main.activity_security_info_options.*

class SecurityInfoOptionsActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security_info_options)

        toolbar.setNavigationIcon(R.drawable.ic_back)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.overScrollMode = View.OVER_SCROLL_NEVER

        val adapter =
            SecurityInfoAdapter(getSecurityInfoItems()) { item: SecurityInfoCardItem ->
                itemClicked(
                    item
                )
            }

        recyclerView.adapter = adapter
    }


    private fun itemClicked(item: SecurityInfoCardItem) {
        val intent: Intent = Intent(this, SecurityInfoDetailsActivity::class.java)

        when (item.type){
            AppConstants.SecurityInfoCategory.KIDNAP ->{
                intent.putExtra(EXTRAS, AppConstants.SecurityInfoCategory.KIDNAP.v)
                startActivity(intent)
                }

            AppConstants.SecurityInfoCategory.ROBBERY ->{
                intent.putExtra(EXTRAS, AppConstants.SecurityInfoCategory.ROBBERY.v)
                startActivity(intent)
            }

            AppConstants.SecurityInfoCategory.FIREARMSPOSSESSIONORASSULT ->{
                intent.putExtra(EXTRAS, AppConstants.SecurityInfoCategory.FIREARMSPOSSESSIONORASSULT.v)
                startActivity(intent)
            }

            AppConstants.SecurityInfoCategory.GANGTHREAT ->{
                intent.putExtra(EXTRAS, AppConstants.SecurityInfoCategory.GANGTHREAT.v)
                startActivity(intent)
            }

            AppConstants.SecurityInfoCategory.HUMANTRAFFICKING ->{
                intent.putExtra(EXTRAS, AppConstants.SecurityInfoCategory.HUMANTRAFFICKING.v)
                startActivity(intent)
            }

            AppConstants.SecurityInfoCategory.RIOT ->{
                intent.putExtra(EXTRAS, AppConstants.SecurityInfoCategory.RIOT.v)
                startActivity(intent)
            }

            AppConstants.SecurityInfoCategory.SUSPICIOUSGATHERINGORMOVEMENT ->{
                intent.putExtra(EXTRAS, AppConstants.SecurityInfoCategory.SUSPICIOUSGATHERINGORMOVEMENT.v)
                startActivity(intent)
            }

            AppConstants.SecurityInfoCategory.DISEASEEPIDEMIC ->{
                intent.putExtra(EXTRAS, AppConstants.SecurityInfoCategory.DISEASEEPIDEMIC.v)
                startActivity(intent)
            }

            AppConstants.SecurityInfoCategory.TOXICWASTE ->{
                intent.putExtra(EXTRAS, AppConstants.SecurityInfoCategory.TOXICWASTE.v)
                startActivity(intent)
            }

            AppConstants.SecurityInfoCategory.DRUGS ->{
                intent.putExtra(EXTRAS, AppConstants.SecurityInfoCategory.DRUGS.v)
                startActivity(intent)
            }

            AppConstants.SecurityInfoCategory.BOMBSOREXPLOSIVES ->{
                intent.putExtra(EXTRAS, AppConstants.SecurityInfoCategory.BOMBSOREXPLOSIVES.v)
                startActivity(intent)
            }

            AppConstants.SecurityInfoCategory.TERRORISM ->{
                intent.putExtra(EXTRAS, AppConstants.SecurityInfoCategory.TERRORISM.v)
                startActivity(intent)
            }

            AppConstants.SecurityInfoCategory.FIRE ->{
                intent.putExtra(EXTRAS, AppConstants.SecurityInfoCategory.FIRE.v)
                startActivity(intent)
            }

            AppConstants.SecurityInfoCategory.OTHERS ->{
                intent.putExtra(EXTRAS, AppConstants.SecurityInfoCategory.OTHERS.v)
                startActivity(intent)
            }
        }
    }


}
