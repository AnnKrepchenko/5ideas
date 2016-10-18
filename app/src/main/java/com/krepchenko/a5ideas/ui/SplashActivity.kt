package com.krepchenko.a5ideas.ui

import android.os.Bundle
import android.os.Handler
import com.krepchenko.a5ideas.R


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val handler = Handler()
        handler.postDelayed({navigate<MainActivity>()}, 3000)

    }
}
