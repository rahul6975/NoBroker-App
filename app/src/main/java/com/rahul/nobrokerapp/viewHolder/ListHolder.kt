package com.rahul.nobrokerapp.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahul.nobrokerapp.`interface`.ClickListener
import com.rahul.nobrokerapp.modelClass.ResponseClass
import com.rahul.nobrokerapp.room.entity.ListEntity
import kotlinx.android.synthetic.main.item_layout.view.*

class ListHolder(private val view: View, private val clickListener: ClickListener) :
    RecyclerView.ViewHolder(view) {

    fun setData(listEntity: ListEntity) {
        view.apply {
            Glide.with(itemImage).load(listEntity.url).into(itemImage)
            tvTitle.text = listEntity.title
            tvSubTitle.text = listEntity.subtitle
        }
        view.setOnClickListener {
            clickListener.onClick(adapterPosition)
        }
    }
}