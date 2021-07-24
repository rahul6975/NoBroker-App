package com.rahul.nobrokerapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rahul.nobrokerapp.room.entity.ListEntity

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addList(listEntity: ListEntity)

    @Query("select * from list_table")
    fun getList(): LiveData<ListEntity>
}