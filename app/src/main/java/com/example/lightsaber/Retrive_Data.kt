package com.example.lightsaber


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Retrive_Data : AppCompatActivity() {
    private lateinit var datar:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrive_data)
        datar=Firebase.database.getReference("user")

        val listOfError= mutableListOf<SensorData>()
        val rv: RecyclerView =findViewById(R.id.rv)
        val adapter=ErrorAdapter(listOfError)

        rv.layoutManager= LinearLayoutManager(this)
        rv.adapter=adapter


        datar.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children){
                    val cu=i.getValue(SensorData::class.java)
                    listOfError.add(cu!!)
                    adapter.notifyDataSetChanged()
                    Log.i("dayanand",cu.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //TODO("Not yet implemented")
            }

        })

    }
}