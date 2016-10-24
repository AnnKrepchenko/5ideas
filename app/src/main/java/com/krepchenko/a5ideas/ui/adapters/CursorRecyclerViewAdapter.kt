package com.krepchenko.a5ideas.ui.adapters

import android.content.Context
import android.database.Cursor
import android.database.DataSetObserver
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter

/**
 * Created by Ann
 */
/*abstract class CursorRecyclerViewAdapter<VH: RecyclerView.ViewHolder>: Adapter<VH>() {

    var context: Context? = null
    var cursor: Cursor? = null
    var dataValid = false
    var rowIdColumn = -1
    var dataObserver: DataSetObserver? = null

    constructor(context: Context, cursor: Cursor){
        this.context = context
        this.cursor = cursor
        this.dataValid = cursor!=null
        this.rowIdColumn = if(dataValid) cursor.getColumnIndex("_id") else -1
        this.dataObserver =
    }

    private class NotifyDataSetObserver: DataSetObserver(){

        override fun onChanged() {
            super.onChanged()
            dataValid = true

        }
    }
}*/