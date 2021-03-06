package com.example.gurleensethi.roomcontacts

import android.app.Activity
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_update_contact.*

class UpdateContactActivity : AppCompatActivity() {
    private lateinit var mContactDAO: ContactDAO
    private lateinit var CONTACT: Contact
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_contact)
        setSupportActionBar(toolbar)

        mContactDAO = Room.databaseBuilder(this, AppDatabase::class.java, "db-contacts")
                .allowMainThreadQueries() //Allows room to do operation on main thread
                .build().contactDAO
        CONTACT = mContactDAO.getContactWithId(intent.getStringExtra(EXTRA_CONTACT_ID)!!)
        CONTACT.apply {
            firstNameEditText.setText(firstName)
            lastNameEditText.setText(lastName)
            phoneNumberEditText.setText(phoneNumber)
            createdTimeTextView.text = createdDate.toString()
        }
        updateButton.setOnClickListener(View.OnClickListener {
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(this@UpdateContactActivity,
                "Please make sure all details are correct", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            CONTACT.firstName = firstName
            CONTACT.lastName = lastName
            CONTACT.phoneNumber = phoneNumber

            //Insert to database
            try {
                mContactDAO.update(CONTACT)
            }
            catch (e : SQLiteConstraintException) {
                Log.d("update", "exception : ${e.message}")
                if ("constraint failed: Contact.phoneNumber".toRegex().find(e.message!!) != null) {
                    Toast.makeText(this, "update failed.  Non unique phone number",Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "update failed for unknown reason.", Toast.LENGTH_SHORT).show()
                }
            }
            setResult(Activity.RESULT_OK)
            finish()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_update_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                mContactDAO.delete(CONTACT)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        var EXTRA_CONTACT_ID = "contact_id"
    }
}