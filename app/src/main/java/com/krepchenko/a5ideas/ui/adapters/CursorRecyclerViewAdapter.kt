package com.krepchenko.a5ideas.ui.adapters

import android.content.Context
import android.database.Cursor
import android.database.DataSetObserver
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter

/**
 * Created by Ann
 */
abstract class CursorRecyclerViewAdapter<VH : RecyclerView.ViewHolder>(context: Context) : Adapter<VH>(){

    private var context: Context? = null
    private var cursor: Cursor? = null
    private var rowIdColumn = -1
    private var dataObserver: DataSetObserver? = null
    private var dataValid = false

    init {
        this.context = context
        this.cursor = null
        dataValid = cursor != null
        this.rowIdColumn = if (dataValid) cursor!!.getColumnIndex("_id") else -1
        this.dataObserver = NotifyDataSetObserver()
    }

    fun getCursor() : Cursor{
        return cursor!!
    }

    override fun getItemCount(): Int {
        if (dataValid && cursor != null) {
            return cursor!!.count
        }
        return 0
    }

    override fun getItemId(position: Int): Long {
        if (dataValid && cursor != null && cursor!!.moveToPosition(position))
            return cursor!!.getLong(rowIdColumn)
        return 0
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(true)
    }

    abstract fun onBindViewHolder(viewHolder: VH, cursor: Cursor?)

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (!dataValid) {
            throw IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!cursor!!.moveToPosition(position)) {
            throw IllegalStateException("couldn't move cursor to position " + position);
        }
        onBindViewHolder(holder,cursor)
    }

    fun changeCursor(cursor: Cursor?){
        var old = swapCursor(cursor)
        if(old!=null)
            old.close()
    }

    fun swapCursor(newCursor: Cursor?) : Cursor?{
        if(newCursor == cursor)
            return null
        val oldCursor = cursor
        if(oldCursor!=null && dataObserver != null)
            oldCursor.unregisterDataSetObserver(dataObserver)
        cursor = newCursor
        if(cursor != null){
            if(dataObserver!=null)
                cursor?.registerDataSetObserver(dataObserver)
            rowIdColumn=newCursor!!.getColumnIndexOrThrow("_id")
            dataValid = true
            notifyDataSetChanged()
        }else{
            rowIdColumn = -1
            dataValid = false
            notifyDataSetChanged()
        }
        return oldCursor
    }

    inner class NotifyDataSetObserver : DataSetObserver() {

        override fun onChanged() {
            super.onChanged()
            dataValid = true
            notifyDataSetChanged()
        }

        override fun onInvalidated() {
            super.onInvalidated()
            dataValid = false
            notifyDataSetChanged()
        }
    }
}