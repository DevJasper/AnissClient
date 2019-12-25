package com.farelands.aniss.feedback

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farelands.aniss.*
import com.farelands.aniss.api.ServiceBuilder
import com.farelands.aniss.api.requests.FeedbackRequest
import com.farelands.aniss.api.services.FeedbackService
import kotlinx.android.synthetic.main.activity_feedback.*
import kotlinx.android.synthetic.main.activity_security_info_details.email
import kotlinx.android.synthetic.main.activity_security_info_details.progressBar
import kotlinx.android.synthetic.main.activity_security_info_details.submit_btn
import kotlinx.android.synthetic.main.activity_security_info_options.toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedbackActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        toolbar.setNavigationIcon(R.drawable.ic_back)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        submit_btn.setOnClickListener {
            var validForm = validForm()
            val theEmail = email.editText!!.text.toString()
            val theMessage = feedback.editText!!.text.toString()

            if(validForm){
                submitFeedback(FeedbackRequest(email = theEmail, message = theMessage))
            }
        }


    }

    private fun validForm(): Boolean{
        var result = true

        if (email.editText!!.text.toString().isNotEmpty()){
            if (!email.editText!!.text.toString().isValidEmail()){
                email.error ="Invalid email"
                result = false

            }
        }

        if (feedback.editText!!.text.toString().isEmpty()){
            feedback.error = "Message cannot be empty"
            result =  false

        }


        if (feedback.editText!!.text.toString().hasExceedCharacterLength(500)){
            feedback.error = "Max characters is 500"
            result =  false

        }

        return  result
    }



    private fun submitFeedback(feedbackRequest: FeedbackRequest){
        submit_btn.isEnabled = false
        showProgressBar(progressBar)
        val feedbackService: FeedbackService =
            ServiceBuilder.buildService(FeedbackService::class.java)
        val request: Call<Void> = feedbackService.submitFeedback(feedbackRequest)

        request.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                submit_btn.isEnabled = true
                showToast("Failed to submit " + t.localizedMessage)
                hideProgressBar(progressBar)

            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                submit_btn.isEnabled = true
                if (response.code() == 200){
                    showToast("Sumbited")
                    hideProgressBar(progressBar)
                    onBackPressed()
                }else{
                    showToast(response.message())
                }

            }

        })
    }

}
