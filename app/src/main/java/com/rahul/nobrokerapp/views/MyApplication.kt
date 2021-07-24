package com.rahul.nobrokerapp.views

import android.app.Application
import com.rahul.nobrokerapp.repository.MyRepository
import com.rahul.nobrokerapp.room.ListDatabase

//Application class
class MyApplication : Application() {
    val listDao by lazy {
        val listDatabase = ListDatabase.getDatabase(this)
        listDatabase.getListDao()
    }

    val myRepository by lazy {
        MyRepository(listDao)
    }
}