package com.krepchenko.a5ideas.ui.activities

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.Toolbar
import android.text.SpannableStringBuilder
import android.view.Menu
import android.view.MenuItem
import com.krepchenko.a5ideas.DB
import com.krepchenko.a5ideas.R
import com.krepchenko.a5ideas.ui.activities.base.BaseIdeaActivity
import com.krepchenko.a5ideas.ui.db.DbManager
import com.krepchenko.a5ideas.ui.db.Idea
import com.krepchenko.a5ideas.ui.utils.Consts
import com.mvc.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_idea.*
import android.R.attr.data
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.graphics.BitmapFactory
import android.net.Uri
import com.krepchenko.a5ideas.ui.utils.BitmapUtils
import java.io.File
import java.util.*


class CreateIdeaActivity : BaseIdeaActivity() {

    private val PICK_IMAGE_ID = 234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (intent.getBooleanExtra(Consts.EXTRA_EDIT, false)) edit()
        ImagePicker.setMinQuality(600, 600)
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
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_CANCELED)
            return
        val bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data)
        idea_image_iv.background = BitmapDrawable(resources, bitmap)
        Log.d("URI", BitmapUtils.bitmapToUriConverter(this,bitmap).toString())
    }


    fun save() {
        val idea: Idea = Idea(intent.getIntExtra(Consts.EXTRA_ID, -1), idea_name_et.text.toString(), idea_desc_et.text.toString())
        DbManager.insertIdea(contentResolver, idea)
    }

    fun edit() {
        val bundle: Bundle = Bundle()
        bundle.putInt(ID, intent.getIntExtra(Consts.EXTRA_ID, -1))
        loaderManager.initLoader(LOADER_ID, bundle, this)
    }

    override fun setToViews(idea: DB.Ideas) {
        idea_name_et.text = SpannableStringBuilder(idea.name)
        idea_desc_et.text = SpannableStringBuilder(idea.description)
        idea_image_iv.setOnClickListener {
            ImagePicker.pickImage(this, "Select your image:")
        }
    }


}
