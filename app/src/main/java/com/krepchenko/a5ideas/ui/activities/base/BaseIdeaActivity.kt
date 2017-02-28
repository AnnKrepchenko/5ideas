package com.krepchenko.a5ideas.ui.activities.base

import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import com.krepchenko.a5ideas.DB
import com.krepchenko.a5ideas.ui.db.IdeaContentProvider

abstract class BaseIdeaActivity : BaseToolbarActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    val LOADER_ID = 0

    val ID = "id"
    val selection = DB.Columns.Ideas.Id + "=?"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    abstract fun setToViews(idea: DB.Ideas)

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val selectionArgs = arrayOf(args!!.get(ID).toString())
        return CursorLoader(this, IdeaContentProvider.IDEAS, null, selection, selectionArgs, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        if (data!!.moveToFirst())
            setToViews(DB.Ideas.fromCursor(data))
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {

    }

}
