package ru.point.utils.network

import android.net.ConnectivityManager

object InternetHolder {
    private var _tracker: InternetTracker? = null
    val tracker get() = requireNotNull(_tracker){
        "Tracker must be initialized"
    }

    fun init(connectivityManager: ConnectivityManager) {
        _tracker = InternetTracker(connectivityManager = connectivityManager)
    }
}