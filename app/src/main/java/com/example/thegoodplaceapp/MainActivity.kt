package com.example.thegoodplaceapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val locationText: TextView = findViewById(R.id.location_text)
        val findLocationButton: Button = findViewById(R.id.find_places)
        val profileButton: ImageButton = findViewById(R.id.my_profile_button)
        val myCollectionButton: ImageButton = findViewById(R.id.my_collection_button)
        val addLocationButton: ImageButton = findViewById(R.id.add_place_button)

        findLocationButton.setOnClickListener(::findPlacesClicked)
        profileButton.setOnClickListener(::profileClicked)
        myCollectionButton.setOnClickListener(::myCollectionClicked)
        addLocationButton.setOnClickListener {
            startActivity(Intent(this, AddLocation::class.java))
        }

        runBlocking {
            locationText.text = "אתה נמצא בעיר: " + getCityName()
        }
    }

    fun findPlacesClicked(view: View) {
        val intent = Intent(this, AllPlacesListActivity::class.java)
        startActivity(intent)
    }

    fun profileClicked(view: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    fun myCollectionClicked(view: View) {
        val intent = Intent(this, AllPlacesListActivity::class.java)
        startActivity(intent)
    }

    private suspend fun getCityName(): String {
//        val url = URL("https://maps.googleapis.com/maps/api/geocode/json").openStream().bufferedReader().use { it.readText() }
//        val client = HttpClient(CIO)
//        val response: HttpResponse = client.get("https://ktor.io/")
//        println(response.status)
//        client.close()
        return "בלהבלה"
    }
}