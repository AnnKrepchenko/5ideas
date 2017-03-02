package com.krepchenko.a5ideas.ui.activities

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.Toolbar
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.krepchenko.a5ideas.DB
import com.krepchenko.a5ideas.R
import com.krepchenko.a5ideas.ui.activities.base.BaseIdeaActivity
import com.krepchenko.a5ideas.ui.db.DbManager
import com.krepchenko.a5ideas.ui.db.Idea
import com.krepchenko.a5ideas.ui.utils.Consts
import com.krepchenko.a5ideas.ui.utils.images.BitmapUtils
import com.krepchenko.a5ideas.ui.utils.images.ImagePicker
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_idea.*


class CreateIdeaActivity : BaseIdeaActivity() {

    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableBack()
        initViews()
        if (intent.getBooleanExtra(Consts.EXTRA_EDIT, false)) editInit()
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
        val bitmap = ImagePicker.getImageFromResult(this, requestCode, data)
        idea_image_iv.background = BitmapDrawable(resources, bitmap)
        uri = BitmapUtils.bitmapToUriConverter(this, bitmap)
    }

    fun initViews() {
        idea_image_iv.setOnClickListener {
            ImagePicker.pickImage(this, "Select your image:")
        }
    }

    fun save() {
        val idea: Idea = Idea(intent.getIntExtra(Consts.EXTRA_ID, -1), idea_name_et.text.toString(), idea_desc_et.text.toString(), uri?.toString())
        DbManager.insertIdea(contentResolver, idea)
    }

    fun editInit() {
        val bundle: Bundle = Bundle()
        bundle.putInt(ID, intent.getIntExtra(Consts.EXTRA_ID, -1))
        loaderManager.initLoader(LOADER_ID, bundle, this)
    }

    override fun setToViews(idea: DB.Ideas) {
        idea_name_et.text = SpannableStringBuilder(idea.name)
        idea_desc_et.text = SpannableStringBuilder(idea.description)
        idea_image_iv.setImageBitmap(MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(idea.pictureUri)))

    }


}
