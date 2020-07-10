package com.example.gurleensethi.roomcontacts.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gurleensethi.roomcontacts.db.typeconverters.DateTypeConverter
import com.example.gurleensethi.roomcontacts.models.Contact

/**
 * Created by gurleensethi on 04/02/18.
 */
@Database(entities = [Contact::class], version = 1, exportSchema = true)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val contactDAO: ContactDAO
}