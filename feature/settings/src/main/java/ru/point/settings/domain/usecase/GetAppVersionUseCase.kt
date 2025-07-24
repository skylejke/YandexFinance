package ru.point.settings.domain.usecase

import ru.point.api.repository.SettingsRepository
import javax.inject.Inject

internal class GetAppVersionUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {

    operator fun invoke() = settingsRepository.getAppVersion()
}