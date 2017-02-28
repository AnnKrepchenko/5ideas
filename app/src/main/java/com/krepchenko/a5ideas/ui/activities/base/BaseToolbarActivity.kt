package com.krepchenko.a5ideas.ui.activities.base

import android.os.Bundle
import android.support.v7.widget.Toolbar

abstract class BaseToolbarActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(setToolbar())
    }

    abstract fun setToolbar() : Toolbar


}
