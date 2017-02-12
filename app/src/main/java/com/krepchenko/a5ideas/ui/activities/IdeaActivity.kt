package com.krepchenko.a5ideas.ui.activities

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.krepchenko.a5ideas.R
import com.krepchenko.a5ideas.ui.activities.base.BaseToolbarActivity
import com.krepchenko.a5ideas.ui.db.DbManager
import com.krepchenko.a5ideas.ui.db.Idea
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_idea.*

class IdeaActivity : BaseToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun setToolbar(): Toolbar {
        toolbar.title = getString(R.string.idea)
        return toolbar
    }

    override fun setContentView(): Int {
        return R.layout.activity_idea
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.idea_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_accept -> {
                save()
                finish()
                return true
            }
        }
        return false
    }

    fun save() {
        val idea: Idea = Idea(idea_name_et.text.toString(), idea_desc_et.text.toString())
        DbManager.insertIdea(contentResolver, idea)
    }

}
