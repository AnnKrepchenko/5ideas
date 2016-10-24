package com.krepchenko.a5ideas.ui.adapters

import android.content.Context
import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.krepchenko.a5ideas.R
import kotlinx.android.synthetic.main.item_card_idea.*

/**
 * Created by ann on 10/20/16.
 */
class IdeaAdapter : CursorRecyclerViewAdapter<IdeaAdapter.ViewHolder> {

    private var onRecyclerItemClick: OnRecyclerViewItemClick? = null
    private var context : Context? = null

    constructor(context: Context?, cursor: Cursor?, onRecyclerItemClick: OnRecyclerViewItemClick?) : super(context,cursor) {
        this.onRecyclerItemClick = onRecyclerItemClick
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_card_idea,parent,false)
        return ViewHolder(view,onRecyclerItemClick!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, cursor: Cursor?) {
       // viewHolder.itemName.setText(cursor.getString(cursor.))
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView), View.OnClickListener{


        var itemName: TextView? = null
        var itemDesc: TextView? = null
        var onRecyclerItemClick: OnRecyclerViewItemClick? = null

        constructor(itemView: View?, onRecyclerItemClick: OnRecyclerViewItemClick) : this(itemView){
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
