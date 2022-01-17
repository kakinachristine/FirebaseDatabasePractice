package com.example.myfirebasedatabaseapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UsersActivity : AppCompatActivity() {
    var mListPerson :ListView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        mListPerson = findViewById(R.id.mListPeople)
        var users:ArrayList<User> = ArrayList()
        var myAdapter = CustomAdapter(this,users!!)
        var progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("PLease wait...")
        //access the table in the database
        var my_db = FirebaseDatabase.getInstance().reference.child("Names")

        //Start fetching/retrieving data
        progress.show()
        my_db.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //get the data and put it on the arraylist users
                users.clear()
                for (snap in snapshot.children){
                    var person = snap.getValue(User::class.java)
                    users.add(person!!)
                }
                //Notify the adapter that the data has changed
                myAdapter.notifyDataSetChanged()
                progress.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                progress.dismiss()
                Toast.makeText(applicationContext,"DB Locked",Toast.LENGTH_LONG).show()
            }
        })

        mListPerson!!.adapter = myAdapter
    }
}