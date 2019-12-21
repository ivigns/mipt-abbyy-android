package com.github.ivigns.abbyy.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setTitle("Notes")

        val cardView = findViewById<CardView>(R.id.cardView)
        cardView.setOnClickListener {
            val detailedView = Intent(this, DetailedView::class.java)
            startActivity(detailedView)
        }
    }
}
