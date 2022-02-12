package com.doskoch.legacy.android.functions

import android.Manifest

/**
 * Creates request code for dangerous permissions. See [Manifest.permission].
 * @param permission target dangerous permission..
 * @return request code for permission.
 * @throws [IllegalArgumentException] when passed permission argument is not a dangerous permission.
 */
fun requestCode(permission: String): Int {
    return when (permission) {
        /* Manifest.permission_group.CALENDAR */
        Manifest.permission.READ_CALENDAR -> 1000
        Manifest.permission.WRITE_CALENDAR -> 1001
        /* Manifest.permission_group.CALL_LOG */
        Manifest.permission.READ_CALL_LOG -> 1100
        Manifest.permission.WRITE_CALL_LOG -> 1101
        Manifest.permission.PROCESS_OUTGOING_CALLS -> 1103
        /* Manifest.permission_group.CAMERA */
        Manifest.permission.CAMERA -> 1200
        /* Manifest.permission_group.CONTACTS */
        Manifest.permission.READ_CONTACTS -> 1300
        Manifest.permission.WRITE_CONTACTS -> 1301
        Manifest.permission.GET_ACCOUNTS -> 1302
        /* Manifest.permission_group.LOCATION */
        Manifest.permission.ACCESS_FINE_LOCATION -> 1400
        Manifest.permission.ACCESS_COARSE_LOCATION -> 1401
        /* Manifest.permission_group.MICROPHONE */
        Manifest.permission.RECORD_AUDIO -> 1500
        /* Manifest.permission_group.PHONE */
        Manifest.permission.READ_PHONE_STATE -> 1600
        Manifest.permission.READ_PHONE_NUMBERS -> 1601
        Manifest.permission.CALL_PHONE -> 1602
        Manifest.permission.ANSWER_PHONE_CALLS -> 1603
        Manifest.permission.ADD_VOICEMAIL -> 1604
        Manifest.permission.USE_SIP -> 1605
        /* Manifest.permission_group.SENSORS */
        Manifest.permission.BODY_SENSORS -> 1700
        /* Manifest.permission_group.SMS */
        Manifest.permission.SEND_SMS -> 1800
        Manifest.permission.RECEIVE_SMS -> 1801
        Manifest.permission.READ_SMS -> 1802
        Manifest.permission.RECEIVE_WAP_PUSH -> 1803
        Manifest.permission.RECEIVE_MMS -> 1804
        /* Manifest.permission_group.STORAGE */
        Manifest.permission.READ_EXTERNAL_STORAGE -> 1900
        Manifest.permission.WRITE_EXTERNAL_STORAGE -> 1901
        else -> throw IllegalArgumentException("There is no request code for permission: $permission")
    }
}