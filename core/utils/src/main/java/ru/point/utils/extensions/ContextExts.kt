package ru.point.utils.extensions

import android.content.Context

fun Context.getAppVersionName(): String {
    return packageManager.getPackageInfo(packageName, 0).versionName ?: "unknown"
}