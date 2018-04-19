package com.example.hermivaldo.projetoinicial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.hide()
        setContentView(R.layout.activity_main)
    }

    fun realizarLogin(view: View){
        var newPage = Intent(this, Painel::class.java)
        startActivity(newPage)
    }
}
