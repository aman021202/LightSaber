package com.example.lightsaber

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.lightsaber.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var bottomNavigation:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        //bottom navigation
        bottomNavigation=findViewById(R.id.botttom_nav)
        bottomNavigation.setOnItemSelectedListener {
            val id=it.itemId
            when(id){
                R.id.home->{
                    Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
                   val intent=Intent(this,Retrive_Data::class.java)
                    startActivity(intent)
                }


            }
            return@setOnItemSelectedListener true
        }
    }


//map
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val p1 = LatLng(12.9096295, 77.5627949)
        val p2 = LatLng(12.9096864, 77.5627983)
        val p3= LatLng(12.9098671, 77.5628204)
        val arraylist: ArrayList<LatLng> = ArrayList()
        arraylist.add(p1)
        arraylist.add(p2)
        arraylist.add(p3)
        for (i in arraylist.indices) {
//            mMap.addMarker(MarkerOptions().position(arraylist[i]!!).title("Marker")
//            )
            val markerView = (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.marker_layout, null)
            val cardView= markerView.findViewById<CardView>(R.id.markerCardView)
            val bitmap = Bitmap.createScaledBitmap(viewToBitmap(cardView)!!, cardView.width, cardView.height,false)
            val smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(bitmap)
            //drawing the marker on the google map
            googleMap.addMarker(MarkerOptions().position(arraylist[i]!!).icon(smallMarkerIcon))


            mMap.setMinZoomPreference(16.0f)
            mMap.setMaxZoomPreference(20.0f)
            mMap.animateCamera(CameraUpdateFactory.zoomTo(300.0f))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(arraylist[i]))
        }
        mMap.setOnMarkerClickListener { marker ->

            for (i in arraylist.indices){
                val gmmIntentUri =
                    Uri.parse("google.navigation:q=${arraylist[i]}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)

            }
            val markerName = marker.title
            Toast.makeText(
                this@MapsActivity,
                "Clicked location is $markerName",
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }
    //bitmap for custom marker
    private fun viewToBitmap(view : View): Bitmap {

        view.measure(View.MeasureSpec.UNSPECIFIED , View.MeasureSpec.UNSPECIFIED )
        val bitmap= Bitmap.createBitmap(view.measuredWidth , view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(0 , 0 , view.measuredWidth , view.measuredHeight)
        view.draw(canvas)
        return bitmap

    }
    }

