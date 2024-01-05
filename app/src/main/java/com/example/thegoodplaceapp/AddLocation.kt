package com.example.thegoodplaceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class AddLocation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)

        val backButton: ImageButton = findViewById(R.id.exit_button)
        backButton.setOnClickListener{
            finish()
        }
    }
}