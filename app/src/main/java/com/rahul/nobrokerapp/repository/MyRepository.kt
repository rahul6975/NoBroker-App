package com.rahul.nobrokerapp.repository

import androidx.lifecycle.LiveData
import com.rahul.nobrokerapp.modelClass.ResponseClass
import com.rahul.nobrokerapp.remote.ApiClient
import com.rahul.nobrokerapp.remote.Network
import com.rahul.nobrokerapp.remote.Resource
import com.rahul.nobrokerapp.remote.ResponseHandler
import com.rahul.nobrokerapp.room.dao.ListDao
import com.rahul.nobrokerapp.room.entity.ListEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MyRepository(val listDao: ListDao) {

    private val apiClient = Network.getInstance()
        .create(ApiClient::class.java)

    private lateinit var result: ResponseClass

    suspend fun getList() {
        result = apiClient.getResponse()
        addList(result)
    }

    fun addList(result: ResponseClass) {

        val listEntity = result.image?.let {
            result.title?.let { it1 ->
                result.subTitle?.let { it2 ->
                    ListEntity(
                        it,
                        it1, it2
                    )
                }
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            listDao.addList(listEntity!!)
        }
    }

    fun displayList(): LiveData<ListEntity> {
        return listDao.getList()
    }
}