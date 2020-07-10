package com.example.gurleensethi.roomcontacts

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gurleensethi.roomcontacts.models.Contact

/**
 * Created by gurleensethi on 03/02/18.
 */
class ContactRecyclerAdapter internal constructor (
    private val context: Context,
    var contactList: List<Contact>,
    private val colors: IntArray
) : RecyclerView.Adapter<ContactRecyclerAdapter.ViewHolder>() {
    //Interface for callbacks
    interface ActionCallback {
        fun onLongClickListener(contact: Contact)
    }

    lateinit var mActionCallbacks: ActionCallback
    override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_recycler_contact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    fun updateData(contacts: List<Contact>) {
        contactList = contacts
        notifyDataSetChanged()
    }

    //View Holder
    inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView), OnLongClickListener {
        private val mNameTextView: TextView
        private val mInitialsTextView: TextView
        private val mInitialsBackground: GradientDrawable
        fun bindData(position: Int) {
            val (firstName, lastName) = contactList[position]
            val fullName = "$firstName $lastName"
            mNameTextView.text = fullName
            val initial = firstName.toUpperCase().substring(0, 1)
            mInitialsTextView.text = initial
            mInitialsBackground.setColor(colors[position % colors.size])
        }

        override fun onLongClick(v: View): Boolean {
            mActionCallbacks
                .onLongClickListener(contactList[adapterPosition])
            return true
        }

        init {
            itemView.setOnLongClickListener(this)
            mInitialsTextView = itemView.findViewById(R.id.initialsTextView)
            mNameTextView = itemView.findViewById(R.id.nameTextView)
            mInitialsBackground =
                mInitialsTextView.background as GradientDrawable
        }
    }

    fun addActionCallback(actionCallbacks: ActionCallback) {
        mActionCallbacks = actionCallbacks
    }
}
