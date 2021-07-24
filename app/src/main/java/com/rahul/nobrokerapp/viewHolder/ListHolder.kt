package com.rahul.nobrokerapp.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahul.nobrokerapp.interfaces.ClickListener
import com.rahul.nobrokerapp.room.ListEntity
import kotlinx.android.synthetic.main.item_layout.view.*
import java.lang.StringBuilder

class ListHolder(private val view: View, private val clickListener: ClickListener) :
    RecyclerView.ViewHolder(view) {

    fun setData(listEntity: ListEntity) {
        view.apply {
            Glide.with(itemImage).load(listEntity.url).into(itemImage)
            tvTitle.text = listEntity.title
            var stringBuilder = StringBuilder()
            var stringArray = listEntity.subtitle.split(" ")
            for (i in 0..stringArray.size / 4) {
                stringBuilder.append(stringArray[i]+" ")
            }
            tvSubTitle.text = stringBuilder.toString()
        }
        view.setOnClickListener {
            clickListener.onClick(adapterPosition)
        }
    }
}