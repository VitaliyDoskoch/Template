package com.extensions.android.components.location.simplified

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

interface SimpleLocationListener : LocationListener {

    override fun onLocationChanged(location: Location) = Unit

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) = Unit

    override fun onProviderEnabled(provider: String) = Unit

    override fun onProviderDisabled(provider: String) = Unit

}