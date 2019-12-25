package com.farelands.aniss.splashscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.farelands.aniss.sendToMainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendToMainActivity()
        finish()
    }
}