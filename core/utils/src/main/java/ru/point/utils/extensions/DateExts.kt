package ru.point.utils.extensions

import java.time.format.DateTimeFormatter


val fullDateFormatter: DateTimeFormatter? = DateTimeFormatter.ofPattern("dd.MM.yyyy")

val timeFormatter: DateTimeFormatter? = DateTimeFormatter.ofPattern("HH:mm")

val isoDateFormatter: DateTimeFormatter? = DateTimeFormatter.ISO_DATE

val monthDayDateFormatter: DateTimeFormatter? = DateTimeFormatter.ofPattern("MM.dd")