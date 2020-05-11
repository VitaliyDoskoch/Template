package com.extensions.kotlin.functions

import java.util.*
import java.util.Calendar.APRIL
import java.util.Calendar.AUGUST
import java.util.Calendar.DATE
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.DAY_OF_WEEK
import java.util.Calendar.DAY_OF_YEAR
import java.util.Calendar.DECEMBER
import java.util.Calendar.ERA
import java.util.Calendar.FEBRUARY
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.JANUARY
import java.util.Calendar.JULY
import java.util.Calendar.JUNE
import java.util.Calendar.MARCH
import java.util.Calendar.MAY
import java.util.Calendar.MILLISECOND
import java.util.Calendar.MINUTE
import java.util.Calendar.MONTH
import java.util.Calendar.NOVEMBER
import java.util.Calendar.OCTOBER
import java.util.Calendar.SECOND
import java.util.Calendar.SEPTEMBER
import java.util.Calendar.WEEK_OF_YEAR
import java.util.Calendar.YEAR

val utcCalendar: Calendar
    get() = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

/**
 * @return [Calendar] with the beginning of current day (00:00).
 */
fun Calendar.beginningOfDay(): Calendar {
    return apply {
        set(MILLISECOND, 0)
        set(SECOND, 0)
        set(MINUTE, 0)
        set(HOUR_OF_DAY, 0)
    }
}

/**
 * @return [Calendar] with non-inclusive ending of current day (23:59:59:999).
 */
fun Calendar.endingOfDay(): Calendar {
    return beginningOfDay().apply {
        add(DATE, +1)
        add(MILLISECOND, -1)
    }
}

/**
 * @return [Calendar] with the beginning of day, which is the first day of current week.
 */
fun Calendar.beginningOfWeek(): Calendar {
    return beginningOfDay().apply {
        set(DAY_OF_WEEK, Calendar.getInstance().firstDayOfWeek)
    }
}

/**
 * @return [Calendar] with the beginning of day, which is the first day of current month.
 */
fun Calendar.beginningOfMonth(): Calendar {
    return beginningOfDay().apply {
        set(DAY_OF_MONTH, 1)
    }
}

/**
 * @return [Calendar] with the beginning of day, which is the first day of current quarter.
 */
fun Calendar.beginningOfQuarter(): Calendar {
    return beginningOfMonth().apply {
        set(
            MONTH, when (get(MONTH)) {
                JANUARY, FEBRUARY, MARCH -> JANUARY
                APRIL, MAY, JUNE -> APRIL
                JULY, AUGUST, SEPTEMBER -> JULY
                OCTOBER, NOVEMBER, DECEMBER -> OCTOBER
                else -> throw IllegalArgumentException("unexpected month: ${get(MONTH)}")
            }
        )
    }
}

/**
 * @return [Calendar] with the beginning of day, which is the first day of previous quarter.
 */
fun Calendar.beginningOfPreviousQuarter(): Calendar {
    return beginningOfMonth().apply {
        when (get(MONTH)) {
            JANUARY, FEBRUARY, MARCH -> add(YEAR, -1)
        }
        set(
            MONTH, when (get(MONTH)) {
                JANUARY, FEBRUARY, MARCH -> OCTOBER
                APRIL, MAY, JUNE -> JANUARY
                JULY, AUGUST, SEPTEMBER -> APRIL
                OCTOBER, NOVEMBER, DECEMBER -> JULY
                else -> throw IllegalArgumentException("unexpected month: ${get(MONTH)}")
            }
        )
    }
}

/**
 * @return current quarter number, starting from 1.
 */
fun Calendar.currentQuarterNumber(): Int {
    return when (get(MONTH)) {
        JANUARY, FEBRUARY, MARCH -> 1
        APRIL, MAY, JUNE -> 2
        JULY, AUGUST, SEPTEMBER -> 3
        OCTOBER, NOVEMBER, DECEMBER -> 4
        else -> throw IllegalArgumentException("unexpected month: ${get(MONTH)}")
    }
}

/**
 * @return [Calendar] with the beginning of day, which is the first day of current year.
 */
fun Calendar.beginningOfYear(): Calendar {
    return beginningOfMonth().apply {
        set(MONTH, 0)
    }
}

/**
 * @return whether [date1] and [date2] point to the same day.
 */
fun isSameDay(date1: Long, date2: Long): Boolean {
    return isSameDay(Date(date1), Date(date2))
}

/**
 * @return whether [date1] and [date2] point to the same day.
 */
fun isSameDay(date1: Date, date2: Date): Boolean {
    val calendar1 = Calendar.getInstance()
    calendar1.time = date1

    val calendar2 = Calendar.getInstance()
    calendar2.time = date2

    return calendar1.get(ERA) == calendar2.get(ERA) &&
        calendar1.get(YEAR) == calendar2.get(YEAR) &&
        calendar1.get(DAY_OF_YEAR) == calendar2.get(DAY_OF_YEAR)
}

/**
 * @return whether [date1] and [date2] points to the same week.
 */
fun isSameWeek(date1: Long, date2: Long): Boolean {
    return isSameWeek(Date(date1), Date(date2))
}

/**
 * @return whether [date1] and [date2] points to the same week.
 */
fun isSameWeek(date1: Date, date2: Date): Boolean {
    val calendar1 = Calendar.getInstance()
    calendar1.time = date1

    val calendar2 = Calendar.getInstance()
    calendar2.time = date2

    return calendar1.get(ERA) == calendar2.get(ERA) &&
        calendar1.get(YEAR) == calendar2.get(YEAR) &&
        calendar1.get(WEEK_OF_YEAR) == calendar2.get(WEEK_OF_YEAR)
}

/**
 * @return whether [date1] and [date2] points to the same month.
 */
fun isSameMonth(date1: Long, date2: Long): Boolean {
    return isSameMonth(Date(date1), Date(date2))
}

/**
 * @return whether [date1] and [date2] points to the same month.
 */
fun isSameMonth(date1: Date, date2: Date): Boolean {
    val calendar1 = Calendar.getInstance()
    calendar1.time = date1

    val calendar2 = Calendar.getInstance()
    calendar2.time = date2

    return calendar1.get(ERA) == calendar2.get(ERA) &&
        calendar1.get(YEAR) == calendar2.get(YEAR) &&
        calendar1.get(MONTH) == calendar2.get(MONTH)
}

/**
 * @return whether [date1] and [date2] points to the same year.
 */
fun isSameYear(date1: Long, date2: Long): Boolean {
    return isSameYear(Date(date1), Date(date2))
}

/**
 * @return whether [date1] and [date2] points to the same year.
 */
fun isSameYear(date1: Date, date2: Date): Boolean {
    val calendar1 = Calendar.getInstance()
    calendar1.time = date1

    val calendar2 = Calendar.getInstance()
    calendar2.time = date2

    return calendar1.get(ERA) == calendar2.get(ERA) &&
        calendar1.get(YEAR) == calendar2.get(YEAR)
}