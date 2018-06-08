package com.example.hermivaldo.projetoinicial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
    }
}
