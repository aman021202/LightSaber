package com.example.lightsaber

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.lightsaber.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


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




            mMap.addMarker(MarkerOptions().position(arraylist[i]!!).title("Marker")
            )

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
    }

