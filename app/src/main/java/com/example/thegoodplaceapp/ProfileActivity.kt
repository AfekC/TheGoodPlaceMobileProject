package com.example.thegoodplaceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val backButton: ImageButton = findViewById(R.id.exit_button)
        backButton.setOnClickListener{
            finish()
        }

    }
}