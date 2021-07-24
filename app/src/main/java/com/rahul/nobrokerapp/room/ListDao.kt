package com.rahul.nobrokerapp.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ListDao {

    @Insert
    fun addList(listEntity: ListEntity)

    @Query("select * from list_table")
    fun getList(): LiveData<List<ListEntity>>
}