package com.example.rentalcarapp.controller.hmiapp

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.WindowManager

class AlertPopupActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        //activity finishes when user taps outside
        this.setFinishOnTouchOutside(true)

        val maxSpeed = intent.getStringExtra("carspeed") ?: 0.0F

        AlertDialog.Builder(this)
            .setTitle("Alert")
            .setMessage("Warning: Speed exceeds $maxSpeed km/h!")
            .setPositiveButton("OK") { _, _ -> finish() }
            .setOnDismissListener { finish() }
            .show()
    }
}