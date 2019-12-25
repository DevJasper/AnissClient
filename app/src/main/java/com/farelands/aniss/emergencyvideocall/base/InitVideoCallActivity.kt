package com.farelands.aniss.emergencyvideocall.base

import android.Manifest
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.farelands.aniss.AppConstants.ACCIDENT
import com.farelands.aniss.AppConstants.CALL_TYPE
import com.farelands.aniss.AppConstants.EXTRAS
import com.farelands.aniss.AppConstants.FIRE_RESCUE_AGENCY
import com.farelands.aniss.AppConstants.MEDICAL_EMERGENCY
import com.farelands.aniss.AppConstants.POLICE
import com.farelands.aniss.R
import com.farelands.aniss.api.*
import com.farelands.aniss.api.models.LocationModel
import com.farelands.aniss.api.requests.CallRequest
import com.farelands.aniss.api.responce.CallResponse
import com.farelands.aniss.api.services.CallService
import com.farelands.aniss.emergencyvideocall.VideoCallActivity
import com.farelands.aniss.hideProgressBar
import com.farelands.aniss.showToast
import com.google.firebase.database.*
import com.opentok.android.Session
import kotlinx.android.synthetic.main.activity_init_video_call.*
import kotlinx.android.synthetic.main.activity_security_info_details.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class InitVideoCallActivity : AppCompatActivity() {

    companion object{
        const val RC_VIDEO_APP_PERM = 124

    }

    private val policeUrl = 3
    private val fireserviceUrl = 4
    private val accidentUrl = 5
    private val medicalEmergencyUrl = 6

    var callState = 0
    private var myRef: DatabaseReference? = null
    private lateinit var myRefListener: ValueEventListener

    var mp: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_video_call)
        myRef = FirebaseDatabase.getInstance().getReference("calls")

        requestPermissions()

        mp = MediaPlayer.create(this, R.raw.phone_ringing_8x)
        mp?.setOnCompletionListener {
            mp!!.release()
            mp = MediaPlayer.create(this, R.raw.phone_ringing_8x)
            mp!!.release()
        }

        val bundle: Bundle = intent.extras!!

        when (bundle.getString(CALL_TYPE)) {
            FIRE_RESCUE_AGENCY -> {
                image.setImageResource(R.drawable.ic_fireservice)
                makeCall(fireserviceUrl)
            }
            POLICE -> {
                image.setImageResource(R.drawable.ic_police)
                makeCall(policeUrl)
            }
            ACCIDENT -> {
                image.setImageResource(R.drawable.ic_accident)
                makeCall(accidentUrl)
            }
            MEDICAL_EMERGENCY -> {
                image.setImageResource(R.drawable.ic_medicalemergency)
                makeCall(medicalEmergencyUrl)
            }
        }
        val endCall = findViewById<ImageView>(R.id.end_call)
        endCall.setOnClickListener {
            onBackPressed()
        }


    }



    @AfterPermissionGranted(VideoCallActivity.RC_VIDEO_APP_PERM)
    private fun requestPermissions() {
        val perms = arrayOf(
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (EasyPermissions.hasPermissions(this, *perms)) {

        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your camera and mic to make video calls",
                VideoCallActivity.RC_VIDEO_APP_PERM,
                *perms
            )
        }
    }

    private fun makeCall(callType: Int) {
//        showToast(callType.toString())

        val newCallRequest = CallRequest(
            callType, LocationModel(
                "6.2758", "7.0068"
            )
        )
        val callService: CallService =
            ServiceBuilder.buildService(CallService::class.java)
        

        val requestCall: Call<CallResponse> = callService.call(newCallRequest)

        requestCall.enqueue(object : Callback<CallResponse> {
            override fun onFailure(call: Call<CallResponse>, t: Throwable) {
//                 showToast("Network Request failed")
//                 onBackPressed()

                makeCall(callType);

            }

            override fun onResponse(call: Call<CallResponse>, response: Response<CallResponse>) {
                if (response.code() == 200) {
                    processResponse(response)
                } else {
                    showToast(response.message())
                    onBackPressed()
                }

            }

        })


    }

    private fun processResponse(r: Response<CallResponse>) {
        val response: CallResponse = r.body()!!
        when (response.status) {
            1 -> {
                text.text = getString(R.string.ringing)
                playTone()
                listenToCallState(response)
            }
            2 -> {
                showToast("All our agents are busy, pls try again in few minutes")
                endTone()
                onBackPressed()
            }

            else ->{

            }
        }

    }

    private fun listenToCallState(response: CallResponse) {
        val intent = Intent(this, VideoCallActivity::class.java)

        myRef = myRef!!.child(response.ref.toString())
            .child("CallDetails").child("CallStatus")


        myRefListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated

                callState = dataSnapshot.value.toString().toInt()

                when (callState) {
                    2 -> {

                    }
                    4 -> {
                        endTone()
                        intent.putExtra(EXTRAS, response)
                        startActivity(intent)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                showToast("An error occurred")
            }
        }

        myRef!!.addValueEventListener(myRefListener)

    }

    private fun playTone() {
        mp!!.start()
    }

    private fun endTone() {
        try {
            mp!!.stop()
            mp!!.release()


        }catch (e : Exception){
            e.printStackTrace()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        when (callState) {
            2 -> {
                myRef!!.setValue(3)
            }
            4 -> {
                myRef!!.setValue(5)
            }
        }
    }


    override fun onDestroy() {
        try {
            myRef!!.removeEventListener(myRefListener)
            mp!!.stop()
            mp!!.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        super.onDestroy()
    }
}
