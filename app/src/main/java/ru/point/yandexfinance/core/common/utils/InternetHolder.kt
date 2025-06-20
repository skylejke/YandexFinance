package ru.point.yandexfinance.core.common.utils

import android.annotation.SuppressLint
import android.content.Context

object InternetHolder {
    @SuppressLint("StaticFieldLeak")
    lateinit var tracker: InternetTracker
        private set

    fun init(context: Context) {
        tracker = InternetTracker(context.applicationContext)
    }
}