package com.example.gurleensethi.roomcontacts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var mContactRecyclerAdapter: ContactRecyclerAdapter
    private lateinit var mContactDAO: ContactDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContactDAO = Room.databaseBuilder(this, AppDatabase::class.java, "db-contacts")
                .allowMainThreadQueries() //Allows room to do operation on main thread
                .build().contactDAO
        contactsRecyclerView.setLayoutManager(LinearLayoutManager(this))
        val colors = intArrayOf(ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, android.R.color.holo_red_light),
                ContextCompat.getColor(this, android.R.color.holo_orange_light),
                ContextCompat.getColor(this, android.R.color.holo_green_light),
                ContextCompat.getColor(this, android.R.color.holo_blue_dark),
                ContextCompat.getColor(this, android.R.color.holo_purple))
        mContactRecyclerAdapter = ContactRecyclerAdapter(this, ArrayList(), colors)
        mContactRecyclerAdapter.addActionCallback(object : ContactRecyclerAdapter.ActionCallback {
            override fun onLongClickListener(contact: Contact) {
                val intent = Intent(this@MainActivity, UpdateContactActivity::class.java)
                intent.putExtra(UpdateContactActivity.EXTRA_CONTACT_ID, contact.phoneNumber)
                startActivityForResult(intent, RC_UPDATE_CONTACT)
            }
        })
        contactsRecyclerView.setAdapter(mContactRecyclerAdapter)
        addContactFloatingActionButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, CreateContactActivity::class.java)
            startActivityForResult(intent, RC_CREATE_CONTACT)
        })
        loadContacts()
    }

    private fun loadContacts() {
        mContactRecyclerAdapter.updateData(mContactDAO.contacts)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_CREATE_CONTACT && resultCode == Activity.RESULT_OK) {
            loadContacts()
        } else if (requestCode == RC_UPDATE_CONTACT && resultCode == Activity.RESULT_OK) {
            loadContacts()
        }
    }

    companion object {
        private const val RC_CREATE_CONTACT = 1
        private const val RC_UPDATE_CONTACT = 2
    }
}