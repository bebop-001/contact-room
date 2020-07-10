package com.example.gurleensethi.roomcontacts.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by gurleensethi on 02/02/18.
 */
@Entity(tableName = "contact")
data class Contact (
        var firstName: String,
        var lastName: String,
        var phoneNumber: String,
        var createdDate: Date,
        @PrimaryKey(autoGenerate = true)
        var id : Int = 0
)