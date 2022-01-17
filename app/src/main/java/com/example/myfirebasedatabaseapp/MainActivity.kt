package com.example.myfirebasedatabaseapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    var editTextName:EditText ?= null
    var editTextEmail:EditText ?= null
    var editTextAge:EditText ?= null
    var buttonSave:Button ?=null
    var buttonView:Button ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        editTextName = findViewById(R.id.mEdtName)
        editTextEmail = findViewById(R.id.mEdtEmail)
        editTextAge = findViewById(R.id.mEdtAge)
        buttonSave = findViewById(R.id.mBtnSave)
        buttonView = findViewById(R.id.mBtnVeiw)
        buttonSave!!.setOnClickListener {
            //Receive data from the user
            var name = editTextName!!.text.toString()
            var email = editTextEmail!!.text.toString()
            var age = editTextAge!!.text.toString()
            var time = System.currentTimeMillis()
            var progress = ProgressDialog(this)
            progress.setTitle("Saving")
            progress.setMessage("PLease wait...")


            if (name.isEmpty() or email.isEmpty() or age.isEmpty()){
                Toast.makeText(this,"Please fill all the inputs",Toast.LENGTH_LONG).show()
            }else{
                //Proceed to save data
                //Create a child(table) in the database
                var my_child = FirebaseDatabase.getInstance().reference.child("Names/$time")
                var data= User(name, email, age)


                //To save data ,simply set the data to my child
                progress.show()
                my_child.setValue(data).addOnCompleteListener { task->
                    progress.dismiss()
                    if(task.isSuccessful){
                        editTextName!!.setText(null)
                        editTextEmail!!.setText(null)
                        editTextAge!!.setText(null)
                        Toast.makeText(this,"Saving successful",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"Saving failed",Toast.LENGTH_LONG).show()
                    }

                }

            }


        }
        buttonView!!.setOnClickListener {
            //Moving from one page to another
            startActivity(Intent(this,UsersActivity::class.java))


        }
    }
}