package com.example.gurleensethi.roomcontacts.db.typeconverters

import androidx.room.TypeConverter
import java.util.*

/**
 * Created by gurleensethi on 05/02/18.
 */
class DateTypeConverter {
    @TypeConverter
    fun convertDateToLong(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun convertLongToDate(time: Long): Date {
        return Date(time)
    }
}