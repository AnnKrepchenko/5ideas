package com.krepchenko.a5ideas.ui.db

import android.net.Uri
import com.krepchenko.a5ideas.DB
import novoda.lib.sqliteprovider.provider.SQLiteContentProviderImpl

/**
 * Created by Ann
 */
class IdeaContentProvider: SQLiteContentProviderImpl() {
    companion object{
        val AUTHORITY:String = "content://com.krepchenko.a5ideas/"
        val IDEAS: Uri = Uri.parse(AUTHORITY).buildUpon().appendPath(DB.Tables.Ideas).build()

    }
}
