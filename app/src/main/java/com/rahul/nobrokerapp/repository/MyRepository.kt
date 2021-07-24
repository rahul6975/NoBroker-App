package com.rahul.nobrokerapp.repository

import androidx.lifecycle.LiveData
import com.rahul.nobrokerapp.modelClass.ResponseClass
import com.rahul.nobrokerapp.remote.ApiClient
import com.rahul.nobrokerapp.remote.Network
import com.rahul.nobrokerapp.room.ListDao
import com.rahul.nobrokerapp.room.ListEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MyRepository(private val listDao: ListDao) {

    private val apiClient = Network.getInstance()
        .create(ApiClient::class.java)

    private lateinit var result: List<ResponseClass>

    suspend fun getList() {
        result = apiClient.getResponse()
        addList(result)
    }

    private fun addList(result: List<ResponseClass>) {

        for (i in 0..result.size - 1) {
            val listEntity =
                ListEntity(result.get(i).image!!, result.get(i).title!!, result.get(i).subTitle!!)
            listDao.addList(listEntity)
        }
    }

    fun displayList(): LiveData<List<ListEntity>> {
        return listDao.getList()
    }
}