package com.krepchenko.a5ideas.ui.adapters

import android.content.Context
import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.krepchenko.a5ideas.R

/**
 * Created by ann on 10/20/16.
 */
class IdeaAdapter(context: Context?, cursor: Cursor?) : CursorRecyclerViewAdapter<IdeaAdapter.ViewHolder>(context, cursor) {

    private var onRecyclerItemClick: OnRecyclerViewItemClick = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent?.context).inflate(R.layout.item_card_idea,parent,false)
        return ViewHolder(view,onRecyclerItemClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, cursor: Cursor?) {
    }

    class ViewHolder(itemView: View?, onRecyclerItemClick: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        override fun onClick(v: View?) {
        }

    }
}