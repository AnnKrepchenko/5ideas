package com.krepchenko.a5ideas.ui.activities

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.SpannableStringBuilder
import com.krepchenko.a5ideas.DB
import com.krepchenko.a5ideas.R
import com.krepchenko.a5ideas.ui.activities.base.BaseIdeaActivity
import com.krepchenko.a5ideas.ui.utils.Consts
import kotlinx.android.synthetic.main.activity_view_idea.*
import kotlinx.android.synthetic.main.content_view_idea.*

class ViewIdeaActivity : BaseIdeaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        load()
    }

    override fun setToolbar(): Toolbar {
        return toolbar
    }

    override fun setContentView(): Int {
        return R.layout.activity_view_idea
    }

    fun initViews() {
        enableBack()
        val bundle = Bundle()
        bundle.putBoolean(Consts.EXTRA_EDIT, true)
        bundle.putInt(Consts.EXTRA_ID, intent.getIntExtra(Consts.EXTRA_ID, -1))
        fab_edit.setOnClickListener { navigate<CreateIdeaActivity>(bundle) }
    }

    fun load() {
        val bundle: Bundle = Bundle()
        bundle.putInt(ID, intent.getIntExtra(Consts.EXTRA_ID, -1))
        loaderManager.initLoader(LOADER_ID, bundle, this)
    }

    override fun setToViews(idea: DB.Ideas) {
        toolbar_layout.title = idea.name
        view_desc_tv.text = SpannableStringBuilder(idea.description)
    }

}
