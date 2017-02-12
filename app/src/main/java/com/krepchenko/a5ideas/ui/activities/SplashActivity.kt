package com.krepchenko.a5ideas.ui.activities

import android.os.Bundle
import android.os.Handler
import com.krepchenko.a5ideas.R
import com.krepchenko.a5ideas.ui.activities.base.BaseActivity


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val handler = Handler()
        handler.postDelayed({ navigateAndClear<MainActivity>() }, 3000)
    }

    override fun setContentView(): Int {
        return R.layout.activity_splash
    }

}
