package com.rahul.nobrokerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rahul.nobrokerapp.R
import com.rahul.nobrokerapp.`interface`.ClickListener
import com.rahul.nobrokerapp.modelClass.ResponseClass
import com.rahul.nobrokerapp.room.entity.ListEntity
import com.rahul.nobrokerapp.viewHolder.ListHolder

class ListAdapter(
    private var responseList: List<ListEntity>,
    private val clickListener: ClickListener
) :
    RecyclerView.Adapter<ListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ListHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val dataModel = responseList[position]
        holder.setData(dataModel)
    }

    override fun getItemCount(): Int {
        return responseList.size
    }

    fun updateList(itemList: List<ListEntity>) {
        responseList = itemList
        notifyDataSetChanged()
    }
}