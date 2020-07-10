package com.example.gurleensethi.roomcontacts

import androidx.room.*
import java.util.*

class Converters {
        @TypeConverter
        fun convertDateToLong(date: Date): Long {
                return date.time
        }

        @TypeConverter
        fun convertLongToDate(time: Long): Date {
                return Date(time)
        }
}

@Database(entities = [Contact::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
        abstract val contactDAO: ContactDAO
}
@Entity(indices = arrayOf(Index("phoneNumber", unique = true)))
data class Contact (
        var firstName: String,
        var lastName: String,
        var phoneNumber: String,
        var createdDate: Date,
        @PrimaryKey(autoGenerate = true)
        var id : Int = 0
)
@Dao
interface ContactDAO {
        @Insert
        fun insert(vararg contacts: Contact)

        @Update
        fun update(vararg contacts: Contact)

        @Delete
        fun delete(contact: Contact)

        @get:Query("SELECT * FROM contact")
        val contacts: List<Contact>

        @Query("SELECT * FROM contact WHERE phoneNumber = :number")
        fun getContactWithId(number: String): Contact
}