package com.example.cosmin.recyclerviewapp.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.example.cosmin.recyclerviewapp.R

class DetailActivity: AppCompatActivity() {
    companion object {
        private const val EXTRA_DATE_TIME = "EXTRA_DATE_TIME"
        private const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        private const val EXTRA_DRAWABLE = "EXTRA_DRAWABLE"
    }

    private lateinit var dateAndTime: TextView
    private lateinit var message: TextView
    private lateinit var coloredBackground: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val dateAndTimeExtra = intent.getStringExtra(EXTRA_DATE_TIME)
        val messageExtra = intent.getStringExtra(EXTRA_MESSAGE)
        val drawableResourceExtra = intent.getIntExtra(EXTRA_DRAWABLE,0)

        dateAndTime = findViewById(R.id.lbl_date_and_time_header)
        dateAndTime.text = dateAndTimeExtra

        message = findViewById(R.id.lbl_message_body)
        message.text = messageExtra

        coloredBackground = findViewById(R.id.imv_colored_background)
        coloredBackground.setBackgroundResource(drawableResourceExtra)
    }
}