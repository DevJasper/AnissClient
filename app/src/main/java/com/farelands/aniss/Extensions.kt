package com.farelands.aniss

import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.farelands.aniss.AppConstants.ACCIDENT
import com.farelands.aniss.AppConstants.FIRE_RESCUE_AGENCY
import com.farelands.aniss.AppConstants.MEDICAL_EMERGENCY
import com.farelands.aniss.AppConstants.POLICE
import com.farelands.aniss.dashboard.DashboardCardItem
import com.farelands.aniss.dashboard.MainActivity
import com.farelands.aniss.emergencycall.EmergencyCallItem
import com.farelands.aniss.securityinfo.SecurityInfoCardItem

fun Context.sendToMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    this.startActivity(intent)
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun getDashboardItems(): ArrayList<DashboardCardItem> {
    val items = ArrayList<DashboardCardItem>()
    items.add(DashboardCardItem("Emergency Call", R.drawable.ic_call))
    items.add(
        DashboardCardItem(
            "Emergency Video Call",
            R.drawable.ic_video_camera
        )
    )
    items.add(
        DashboardCardItem(
            "Give Security Info",
            R.drawable.ic_detective
        )
    )
    items.add(DashboardCardItem("Feedback", R.drawable.ic_rating))
    return items
}

fun getSecurityInfoItems(): ArrayList<SecurityInfoCardItem> {
    val items = ArrayList<SecurityInfoCardItem>()
    items.add(SecurityInfoCardItem("FIRE", R.drawable.ic_fireservice, AppConstants.SecurityInfoCategory.FIRE))
    items.add(SecurityInfoCardItem("KIDNAP", R.drawable.ic_kidnapping, AppConstants.SecurityInfoCategory.KIDNAP))

    items.add(SecurityInfoCardItem("ROBBERY", R.drawable.ic_robbery, AppConstants.SecurityInfoCategory.ROBBERY))
    items.add(
        SecurityInfoCardItem(
            "FIRE ARMS POSSESSION / ASSAULT",
            R.drawable.ic_fire_arms, AppConstants.SecurityInfoCategory.FIREARMSPOSSESSIONORASSULT
        )
    )

    items.add(
        SecurityInfoCardItem(
            "GANG THREAT",
            R.drawable.ic_gang_threats, AppConstants.SecurityInfoCategory.GANGTHREAT
        )
    )
    items.add(
        SecurityInfoCardItem(
            "HUMAN TRAFFICKING",
            R.drawable.ic_human_trafficking, AppConstants.SecurityInfoCategory.HUMANTRAFFICKING
        )
    )

    items.add(SecurityInfoCardItem("RIOT", R.drawable.ic_riot, AppConstants.SecurityInfoCategory.RIOT))
    items.add(
        SecurityInfoCardItem(
            "SUSPICIOUS GATHERING / MOVEMENT",
            R.drawable.ic_suspicious_gathering, AppConstants.SecurityInfoCategory.SUSPICIOUSGATHERINGORMOVEMENT
        )
    )

    items.add(
        SecurityInfoCardItem(
            "DISEASE EPIDEMIC",
            R.drawable.ic_detective, AppConstants.SecurityInfoCategory.DISEASEEPIDEMIC
        )
    )
    items.add(
        SecurityInfoCardItem(
            "TOXIC WASTE",
            R.drawable.ic_toxic_waste, AppConstants.SecurityInfoCategory.TOXICWASTE
        )
    )

    items.add(SecurityInfoCardItem("DRUGS", R.drawable.ic_drugs, AppConstants.SecurityInfoCategory.DRUGS))
    items.add(
        SecurityInfoCardItem(
            "BOMBS / EXPLOSIVES",
            R.drawable.ic_bomb, AppConstants.SecurityInfoCategory.BOMBSOREXPLOSIVES
        )
    )

    items.add(SecurityInfoCardItem("TERRORISM", R.drawable.ic_terrorism , AppConstants.SecurityInfoCategory.TERRORISM))
    items.add(SecurityInfoCardItem("OTHER", R.drawable.ic_other, AppConstants.SecurityInfoCategory.OTHERS))
    return items
}

fun getEmergencyVideoCallItems(): ArrayList<DashboardCardItem> {
    val items = ArrayList<DashboardCardItem>()
    items.add(
        DashboardCardItem(
            FIRE_RESCUE_AGENCY,
            R.drawable.ic_fireservice
        )
    )
    items.add(DashboardCardItem(POLICE, R.drawable.ic_police))
    items.add(DashboardCardItem(ACCIDENT, R.drawable.ic_accident))
    items.add(
        DashboardCardItem(
            MEDICAL_EMERGENCY,
            R.drawable.ic_medicalemergency
        )
    )

    return items
}


fun getEmergencyCallItems(): ArrayList<EmergencyCallItem> {
    val items = ArrayList<EmergencyCallItem>()
    items.add(EmergencyCallItem("Emergency(Toll Free)", "tel:112"))
    items.add(EmergencyCallItem("Security (Police)", "tel:07039194332"))
    items.add(EmergencyCallItem("Anambra Fire Rescue Agency (1)", "tel:08162280025"))
    items.add(EmergencyCallItem("Anambra Fire Rescue Agency (2)", "tel:07080727941"))
    items.add(EmergencyCallItem("Anambra Vigilante Group (North Senatorial District)", "tel:08035883777"))
    items.add(EmergencyCallItem("Anambra Vigilante Group (Central Senatorial District)", "tel:08033249180"))
    items.add(EmergencyCallItem("Anambra Vigilante Group (South Senatorial District)", "tel:08066569194"))
    
    // items.add(EmergencyCallItem("Anambra Emergency(Toll Free)", "tel:112"))
    // items.add(EmergencyCallItem("Emergency Call (Awka Base)", "tel:08086675053"))
    // items.add(EmergencyCallItem("Emergency Call (Onitsha Base)", "tel:09092469137"))
    // items.add(EmergencyCallItem("Emergency Call (Ekwulobia Base)", "tel:081622800025"))
    // items.add(EmergencyCallItem("Emergency Call (Abagana Base)", "tel:07080727941"))
    return items
}


fun showProgressBar(progressBar: ProgressBar){
    if(progressBar.visibility == View.GONE) {
        progressBar.visibility = View.VISIBLE
        return
    }

    if(progressBar.visibility == View.INVISIBLE) {
        progressBar.visibility = View.VISIBLE
        return
    }

}


fun hideProgressBar(progressBar: ProgressBar){
    if(progressBar.visibility == View.VISIBLE) {
        progressBar.visibility = View.GONE
        return
    }

    if(progressBar.visibility == View.GONE) {
        return
    }
}

fun String.isValidEmail(): Boolean
        = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.hasExceedCharacterLength(maxChars: Int): Boolean{
    if (this.length > maxChars){
        return  true
    }
    return false
}




