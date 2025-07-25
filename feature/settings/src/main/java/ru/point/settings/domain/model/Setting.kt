package ru.point.settings.domain.model

import androidx.annotation.StringRes

@JvmInline
internal value class Setting(
    @param:StringRes val settingTitleResId: Int
)