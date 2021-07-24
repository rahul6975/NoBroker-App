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


class MyRepository(val listDao: ListDao) {

    private val apiClient = Network.getInstance()
        .create(ApiClient::class.java)

    private lateinit var result: List<ResponseClass>

    suspend fun getList() {
        result = apiClient.getResponse()
        addList(result)
    }

    private fun addList(result: List<ResponseClass>) {

//        val listEntity = result.image?.let {
//            result.title?.let { it1 ->
//                result.subTitle?.let { it2 ->
//                    ListEntity(
//                        it,
//                        it1, it2
//                    )
//                }
//            }
//        }
        for (i in 0..result.size - 1) {
            val listEntity =
                ListEntity(result.get(i).image!!, result.get(i).title!!, result.get(i).subTitle!!)
//            CoroutineScope(Dispatchers.IO).launch {
            listDao.addList(listEntity)
        }
    }

    fun displayList(): LiveData<List<ListEntity>> {
        return listDao.getList()
    }
}