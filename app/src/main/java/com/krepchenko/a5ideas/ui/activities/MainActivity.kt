package com.krepchenko.a5ideas.ui.activities

import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.krepchenko.a5ideas.R
import com.krepchenko.a5ideas.ui.adapters.IdeaAdapter
import com.krepchenko.a5ideas.ui.adapters.OnRecyclerViewItemClick
import com.krepchenko.a5ideas.ui.db.IdeaContentProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), OnRecyclerViewItemClick, LoaderManager.LoaderCallbacks<Cursor> {

    private val LOADER_ID = 1
    private var ideaAdapter: IdeaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ideaAdapter = IdeaAdapter(this,this)
        main_rv.adapter = ideaAdapter
        main_rv.layoutManager = LinearLayoutManager(this)
        loaderManager.initLoader(LOADER_ID, Bundle.EMPTY,this)
    }

    override fun onResume() {
        super.onResume()
        loaderManager.initLoader(LOADER_ID, Bundle.EMPTY,this)
    }

    override fun onItemClick(position: Int, view: View) {
        toast("click" + position)
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {
        return CursorLoader(this,IdeaContentProvider.IDEAS,null,null,null,null)
    }

    override fun onLoaderReset(p0: Loader<Cursor>?) {
        ideaAdapter?.swapCursor(null)
    }

    private fun reloadAdapter(){
        loaderManager.getLoader<Cursor>(LOADER_ID).forceLoad()
    }

    override fun onLoadFinished(p0: Loader<Cursor>?, p1: Cursor?) {
        ideaAdapter?.swapCursor(p1)
    }
}


