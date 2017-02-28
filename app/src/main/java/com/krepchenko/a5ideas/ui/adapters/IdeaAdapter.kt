package com.krepchenko.a5ideas.ui.adapters

import android.content.Context
import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.krepchenko.a5ideas.R

/**
 * Created by ann on 10/20/16.
 */
class IdeaAdapter(context: Context?, onRecyclerItemClick: OnRecyclerViewItemClick?) : CursorRecyclerViewAdapter<IdeaAdapter.ViewHolder>(context!!) {
    private var onRecyclerItemClick: OnRecyclerViewItemClick? = null
    private var context : Context? = null

    init {
        this.onRecyclerItemClick = onRecyclerItemClick
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_card_idea,parent,false)
        return ViewHolder(view,onRecyclerItemClick!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, cursor: Cursor?) {
        viewHolder.itemName?.text = cursor?.getString(cursor.getColumnIndex("name"))
        viewHolder.itemDesc?.text = cursor?.getString(cursor.getColumnIndex("description"))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        Log.d("Bind",position.toString())
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var itemName: TextView? = null
        var itemDesc: TextView? = null
        var onRecyclerItemClick: OnRecyclerViewItemClick? = null

        constructor(itemView: View?, onRecyclerItemClick: OnRecyclerViewItemClick) : this(itemView) {
            this.onRecyclerItemClick = onRecyclerItemClick
            itemName = itemView?.findViewById(R.id.itemIdeasName) as TextView?
            itemDesc = itemView?.findViewById(R.id.itemIdeasDesc) as TextView?
            itemView?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onRecyclerItemClick?.onItemClick(adapterPosition,itemView)
        }

    }
}
