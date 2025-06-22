package ru.point.yandexfinance.core.common.model

import retrofit2.HttpException

sealed interface AppError {
    data class Http(val code: Int, val body: String?) : AppError
    data object UnknownHost : AppError
    data object Unauthorized : AppError
    data object NotFound : AppError
    data object BadRequest : AppError
    data object ServerError : AppError
    data class Unknown(val t: Throwable) : AppError
}

fun Throwable.toAppError() = when (this) {
    is HttpException -> {
        val body = response()?.errorBody()?.string().orEmpty()
        when (code()) {
            400 -> AppError.BadRequest
            401 -> AppError.Unauthorized
            404 -> AppError.NotFound
            in 500..599 -> AppError.ServerError
            else -> AppError.Http(code(), body)
        }
    }

    else -> AppError.Unknown(this)
}

fun AppError.toUserMessage() = when (this) {
    AppError.BadRequest -> "Неправильный запрос к серверу"
    AppError.Unauthorized -> "Требуется авторизация"
    AppError.NotFound -> "Данных не найдено"
    AppError.ServerError -> "Сервер временно недоступен, попробуйте позже"
    is AppError.Http -> "Ошибка ${this.code}: ${this.body}"
    AppError.UnknownHost -> "Неверный адрес сервера"
    is AppError.Unknown -> "Что-то пошло не так: ${this.t.localizedMessage}"
}