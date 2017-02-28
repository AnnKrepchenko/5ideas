package com.krepchenko.a5ideas.ui.db

import android.content.ContentValues
import android.database.SQLException
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

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        var result: Uri?
        try {
            result = super.insert(uri, values)
        } catch (e: SQLException) {
            e.printStackTrace()
            writableDatabase.replace(uri.lastPathSegment, null, values)
            result = null
        }
        return result
    }
}
