package ru.point.utils.extensions

import java.time.format.DateTimeFormatter


val dateFormatter: DateTimeFormatter? = DateTimeFormatter.ofPattern("dd.MM.yyyy")

val timeFormatter: DateTimeFormatter? = DateTimeFormatter.ofPattern("HH:mm")