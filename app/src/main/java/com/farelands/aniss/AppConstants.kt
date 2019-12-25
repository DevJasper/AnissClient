package com.farelands.aniss

object AppConstants {
    const val KIDNAP = "KIDNAP"
    const val ROBBERY = "ROBBERY"

    const val FIRE_ARMS_POSSESSION_ASSAULT = "FIRE_ARMS_POSSESSION_ASSAULT"
    const val GANG_THREAT = "GANG_THREAT"

    const val HUMAN_TRAFFICKING = "HUMAN_TRAFFICKING"
    const val RIOT = "RIOT"

    const val SUSPICIOUS_GATHERING_MOVEMENT = "SUSPICIOUS_GATHERING_MOVEMENT"
    const val DISEASE_EPIDEMIC = "DISEASE_EPIDEMIC"

    const val TOXIC_WASTE = "TOXIC_WASTE"
    const val DRUGS = "DRUGS"

    const val BOMBS_EXPLOSIVES = "BOMBS_EXPLOSIVES"
    const val TERRORISM = "TERRORISM"

    const val OTHER = "OTHER"


    const val LOG_TAG = "obkm"

    const val CALL_TYPE = "CALL_TYPE"
    const val EXTRAS = "EXTRAS"

    const val FIRE_RESCUE_AGENCY = "Anambra Fire and Rescue Agency (ANFRA)"
    const val POLICE = "POLICE"
    const val ACCIDENT = "ACCIDENT"
    const val MEDICAL_EMERGENCY = "MEDICAL EMERGENCY"


    enum class SecurityInfoCategory(val v: Int) {

        KIDNAP(1),
        ROBBERY(2),
        FIREARMSPOSSESSIONORASSULT(3),
        GANGTHREAT(4),
        HUMANTRAFFICKING(5),
        RIOT(6),
        SUSPICIOUSGATHERINGORMOVEMENT(7),
        DISEASEEPIDEMIC(8),
        TOXICWASTE(9),
        DRUGS(10),
        BOMBSOREXPLOSIVES(11),
        TERRORISM(12),
        FIRE(13),
        OTHERS(14)

    }


}