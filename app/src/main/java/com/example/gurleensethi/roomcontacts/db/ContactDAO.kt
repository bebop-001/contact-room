package com.example.gurleensethi.roomcontacts.db

import androidx.room.*
import com.example.gurleensethi.roomcontacts.models.Contact

/**
 * Created by gurleensethi on 04/02/18.
 */
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