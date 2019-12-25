package com.farelands.aniss.emergencycall

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farelands.aniss.R
import com.farelands.aniss.getEmergencyCallItems
import com.farelands.aniss.showToast
import kotlinx.android.synthetic.main.activity_security_info_options.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class EmergencyCallActivity : AppCompatActivity(){


    companion object {
        const val RC_CALL_PHONE_PERM = 123
    }

    private lateinit var recyclerView: RecyclerView
    private val callIntent = Intent(Intent.ACTION_CALL)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_call)

        requestPermissions()

        toolbar.setNavigationIcon(R.drawable.ic_back)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.overScrollMode = View.OVER_SCROLL_NEVER

        val adapter =
            EmergencyCallAdapter(getEmergencyCallItems()) { item: EmergencyCallItem -> itemClicked(item) }

        recyclerView.adapter = adapter
    }


    private fun itemClicked(item: EmergencyCallItem) {
        requestPermissions()
        when(item.title){
            ""->{showToast("Clicked")}
            else -> {
                callIntent.data = Uri.parse(item.code)
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    return
                }
                startActivity(callIntent)
            }
        }
    }

    private fun hasTelephonePermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE)
    }

    @AfterPermissionGranted(RC_CALL_PHONE_PERM)
    private fun requestPermissions(){
        if (hasTelephonePermission()) {

        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_telephone),
                RC_CALL_PHONE_PERM,
                Manifest.permission.CALL_PHONE)
        }

    }

}
