package com.farelands.aniss.emergencyvideocall

import android.Manifest
import android.content.IntentSender
import android.location.Location
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.farelands.aniss.AppConstants.EXTRAS
import com.farelands.aniss.AppConstants.LOG_TAG
import com.farelands.aniss.R
import com.farelands.aniss.api.responce.CallResponse
import com.farelands.aniss.api.models.LocationModel
import com.farelands.aniss.sendToMainActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.opentok.android.*
import dmax.dialog.SpotsDialog
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.text.DateFormat
import java.util.*

class VideoCallActivity : AppCompatActivity(), Session.SessionListener,
    PublisherKit.PublisherListener {

    companion object {
        const val RC_VIDEO_APP_PERM = 124

        // location updates intervals
        const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
        const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = 20000
        const val REQUEST_CHECK_SETTINGS = 100
    }


    override fun onError(p0: PublisherKit?, p1: OpentokError?) {
    }

    override fun onStreamCreated(p0: PublisherKit?, p1: Stream?) {

    }

    override fun onStreamDestroyed(p0: PublisherKit?, p1: Stream?) {
    }


    override fun onStreamReceived(session: Session, stream: Stream) {
        Log.i(LOG_TAG, "Stream Received")

        if (mSubscriber == null) {
            mSubscriber = Subscriber.Builder(this, stream).build()
            mSession!!.subscribe(mSubscriber)
            mSubscriberViewContainer!!.addView(mSubscriber!!.view)
        }
    }

    override fun onStreamDropped(session: Session, stream: Stream) {
        Log.i(LOG_TAG, "Stream Dropped")

        if (mSubscriber != null) {
            mSubscriber = null
            mSubscriberViewContainer!!.removeAllViews()
        }
    }


    override fun onConnected(session: Session) {
        Log.i(LOG_TAG, "Session Connected")

        mPublisher = Publisher.Builder(this).build()
        mPublisher!!.setPublisherListener(this)

        mPublisherViewContainer!!.addView(mPublisher!!.view)

        if(mPublisher!!.view is GLSurfaceView){
            (mPublisher!!.view as GLSurfaceView).setZOrderOnTop(true)
        }

        mSession!!.publish(mPublisher)
        dialog.dismiss()
    }


    override fun onDisconnected(p0: Session?) {
    }

    override fun onError(p0: Session?, p1: OpentokError?) {
    }

    // Object from intent
    private lateinit var callResponse: CallResponse

    // views
    private var mPublisherViewContainer: FrameLayout? = null
    private var mSubscriberViewContainer: FrameLayout? = null

    // video call object
    private var mSession: Session? = null
    private var mPublisher: Publisher? = null
    private var mSubscriber: Subscriber? = null


    // location boolean flag to for lifecycle
    private var mRequestingLocationUpdates: Boolean = false

    // location update time
    private var mLastUpdateTime: String? = null


    // location related apis
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mSettingsClient: SettingsClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var mCurrentLocation: Location? = null


    private var lat = "9"
    private var lng = "6"

    // database reference
    private var myRef: DatabaseReference? = null

    // progress dialog
    private lateinit var  dialog: android.app.AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_call)
        myRef = FirebaseDatabase.getInstance().getReference("calls")
        callResponse = intent.getParcelableExtra(EXTRAS)!!

         dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Processing")
            .setCancelable(false).build()
            .apply {
                show()
            }

        initLocationService()
        requestPermissionsAndStartVideoCall()

    }


    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private fun requestPermissionsAndStartVideoCall() {
        val perms = arrayOf(
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (EasyPermissions.hasPermissions(this, *perms)) {

            // initialize the location service
//            initLocationService()

            // initialize view objects from your layout
            mPublisherViewContainer = findViewById(R.id.publisher_container)
            mSubscriberViewContainer = findViewById(R.id.subscriber_container)

            // initialize and connect to the session
            mSession = Session.Builder(
                this@VideoCallActivity,
                callResponse.video.apiKey,
                callResponse.video.sessionId
            ).build()
            mSession!!.setSessionListener(this@VideoCallActivity)
            mSession!!.connect(callResponse.video.token)

            mRequestingLocationUpdates = true
            startLocationUpdates()

        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your camera and mic to make video calls",
                RC_VIDEO_APP_PERM,
                *perms
            )
        }
    }


    private fun initLocationService() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mSettingsClient = LocationServices.getSettingsClient(this)


        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                // location is received
                mCurrentLocation = locationResult!!.lastLocation
                mLastUpdateTime = DateFormat.getTimeInstance().format(Date())

                updateLocationInDb()

            }
        }

        mRequestingLocationUpdates = false

        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest!!.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        mLocationSettingsRequest = builder.build()
    }


    private fun startLocationUpdates() {
        mSettingsClient!!
            .checkLocationSettings(mLocationSettingsRequest)
            .addOnSuccessListener(this) {
                Log.d(LOG_TAG, "All location settings are satisfied.")

//                Toast.makeText(applicationContext, "Started location updates!", Toast.LENGTH_SHORT)
//                    .show()

                mFusedLocationClient!!.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback, Looper.myLooper()
                )

                updateLocationInDb()
            }
            .addOnFailureListener(this) {e ->
                when ((e as ApiException).statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        Log.d(
                            LOG_TAG,
                            "Location settings are not satisfied. Attempting to upgrade " + "location settings "
                        )
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the
                            // result in onActivityResult().
                            val rae = e as ResolvableApiException
                            rae.startResolutionForResult(
                                this@VideoCallActivity,
                                REQUEST_CHECK_SETTINGS
                            )
                        } catch (sie: IntentSender.SendIntentException) {
                            Log.d(LOG_TAG, "PendingIntent unable to execute request.")
                        }

                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        val errorMessage =
                            "Location settings are inadequate, and cannot be " + "fixed here. Fix in Settings."
                        Log.d(LOG_TAG, errorMessage)

                        Toast.makeText(this@VideoCallActivity, errorMessage, Toast.LENGTH_LONG)
                            .show()
                    }
                }

                updateLocationInDb()
            }
    }

    private fun updateLocationInDb() {
        Log.d(LOG_TAG, "Updating")

        if (mCurrentLocation != null) {
            lat = mCurrentLocation!!.latitude.toString()
            lng = mCurrentLocation!!.longitude.toString()

            val location =
                LocationModel(Latitude = lat, Longitude = lng)

            Log.d(LOG_TAG, "Lat $lat Lng $lng")

            myRef!!.child(callResponse.ref!!).child("Location").updateChildren(location.toMap())

        }
    }


    override fun onResume() {
        super.onResume()
        if (mRequestingLocationUpdates) {
            startLocationUpdates()
        }
        updateLocationInDb()
    }

    override fun onPause() {
        super.onPause()

        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates()
        }
    }


    private fun stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient!!
            .removeLocationUpdates(mLocationCallback)
            .addOnCompleteListener(this) { }
    }


    override fun onBackPressed() {
        super.onBackPressed()

        startLocationUpdates()
        mSubscriberViewContainer!!.removeAllViews()
        mSession!!.disconnect()
        sendToMainActivity()
        finish()

    }

}
