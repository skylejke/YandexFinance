package ru.point.yandexfinance.ui

import android.app.Application
import ru.point.yandexfinance.core.common.utils.InternetHolder

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        InternetHolder.init(this)
    }

    override fun onTerminate() {
        InternetHolder.tracker.release()
        super.onTerminate()
    }
}