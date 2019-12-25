package com.farelands.aniss.securityinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farelands.aniss.*
import com.farelands.aniss.AppConstants.EXTRAS
import com.farelands.aniss.api.ServiceBuilder
import com.farelands.aniss.api.requests.SecurityInfoRequest
import com.farelands.aniss.api.services.SecurityInfoService
import kotlinx.android.synthetic.main.activity_security_info_details.*
import kotlinx.android.synthetic.main.activity_security_info_details.email
import kotlinx.android.synthetic.main.activity_security_info_details.progressBar
import kotlinx.android.synthetic.main.activity_security_info_details.submit_btn
import kotlinx.android.synthetic.main.activity_security_info_options.toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecurityInfoDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security_info_details)

        toolbar.setNavigationIcon(R.drawable.ic_back)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        var bundleExtra = AppConstants.SecurityInfoCategory.BOMBSOREXPLOSIVES

        var bundleExtra = intent.getIntExtra(EXTRAS, 1)

        submit_btn.setOnClickListener {
            val theFullName = full_name.editText!!.text.toString()
            val theEmail = email.editText!!.text.toString()
            val thePhoneNo = phone_no.editText!!.text.toString()
            val theArea = area.selectedItem!!.toString()
            val theTown = town.editText!!.text.toString()
            val theAddress = address.editText!!.text.toString()
            val theThoseInvolved = thoseInvolved.editText!!.text.toString()
            val theDescription = descriptionOfThreat.editText!!.text.toString()



            if (validForm(
                    theFullName,
                    theEmail,
                    thePhoneNo,
                    theArea,
                    theTown,
                    theAddress,
                    theThoseInvolved,
                    theDescription
                )
            ) {
                submitSecurityInfo(
                    SecurityInfoRequest(
                        securityInfoCategory = bundleExtra,
                        fullName = theFullName,
                        email = theEmail,
                        phoneNumber = thePhoneNo,
                        area = theArea,
                        town = theTown,
                        address = theAddress,
                        thoseInvolved = theThoseInvolved,
                        description = theDescription
                    )
                )
            }
        }

    }


    private fun validForm(
        theFullName: String,
        theEmail: String,
        thePhoneNo: String,
        theArea: String,
        theTown: String,
        theAddress: String,
        theThoseInvolved: String,
        theDescription: String
    ): Boolean {
        var result = true

        if (theFullName.hasExceedCharacterLength(50)) {
            full_name.error = "Max characters is 50"
            result = false

        }

        if (theEmail.hasExceedCharacterLength(50)) {
            email.error = "Max characters is 50"
            result = false

        }

        if (theEmail.isNotEmpty()) {
            if (!email.editText!!.text.toString().isValidEmail()) {
                email.error = "Invalid email"
                result = false

            }
        }

        if (thePhoneNo.hasExceedCharacterLength(11)) {
            phone_no.error = "Max characters is 11"
            result = false

        }

        if (theArea == "Area") {
            showToast("Select an area")
            result = false

        }

        if (theTown.hasExceedCharacterLength(50)) {
            town.error = "Max characters is 50"
            result = false

        }

        if (theAddress.hasExceedCharacterLength(150)) {
            address.error = "Max characters is 150"
            result = false

        }

        if (theThoseInvolved.hasExceedCharacterLength(500)) {
            thoseInvolved.error = "Max characters is 500"
            result = false

        }

        if (theDescription.isEmpty()) {
            descriptionOfThreat.error = "Cannot be empty"
            result = false

        }
        if (theDescription.hasExceedCharacterLength(500)) {
            descriptionOfThreat.error = "Max characters is 500"
            result = false

        }


        return result
    }


    private fun submitSecurityInfo(securityInfoRequest: SecurityInfoRequest) {
        submit_btn.isEnabled = false
        showProgressBar(progressBar)
        val securityInfoService: SecurityInfoService =
            ServiceBuilder.buildService(SecurityInfoService::class.java)
        val request: Call<Void> = securityInfoService.submitSecurityInfo(securityInfoRequest)

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
