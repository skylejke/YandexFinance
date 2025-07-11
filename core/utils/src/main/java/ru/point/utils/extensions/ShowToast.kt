package ru.point.utils.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun showToast(
    context: Context,
    @StringRes messageResId: Int,
) {
    Toast.makeText(context, context.getString(messageResId), Toast.LENGTH_SHORT).show()
}