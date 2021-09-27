package com.example.donationproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
    }

    fun donor(view: android.view.View) {
        val intent = Intent(this,DonorActivity::class.java)
        startActivity(intent)
    }
    fun recipient(view: android.view.View) {
        val intent = Intent(this,RecipientActivity::class.java)
        startActivity(intent)
    }
}