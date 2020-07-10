package com.example.gurleensethi.roomcontacts

import android.app.Activity
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_create_contact.*
import java.util.*

class CreateContactActivity : AppCompatActivity() {
    private lateinit var mContactDAO: ContactDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contact)
        mContactDAO = Room.databaseBuilder(this, AppDatabase::class.java, "db-contacts")
                .allowMainThreadQueries() //Allows room to do operation on main thread
                .build().contactDAO
        saveButton.setOnClickListener(View.OnClickListener {
            val firstName = firstNameEditText.getText().toString()
            val lastName = lastNameEditText.getText().toString()
            val phoneNumber = phoneNumberEditText.getText().toString()
            if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(this@CreateContactActivity, "Please make sure all details are correct", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            val contact = Contact(firstName, lastName, phoneNumber, Date())
            //Insert to database
            try {
                mContactDAO.insert(contact)
                setResult(Activity.RESULT_OK)
                finish()
            } catch (e: SQLiteConstraintException) {
                Toast.makeText(this@CreateContactActivity, "A cotnact with same phone number already exists.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}