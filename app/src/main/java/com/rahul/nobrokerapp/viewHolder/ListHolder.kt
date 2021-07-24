package com.rahul.nobrokerapp.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahul.nobrokerapp.modelClass.ResponseClass
import kotlinx.android.synthetic.main.item_layout.view.*

class ListHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun setData(responseClass: ResponseClass) {
        view.apply {
            Glide.with(itemImage).load(responseClass.image).into(itemImage)
            tvTitle.text = responseClass.title
            tvSubTitle.text = responseClass.subTitle
        }
    }
}