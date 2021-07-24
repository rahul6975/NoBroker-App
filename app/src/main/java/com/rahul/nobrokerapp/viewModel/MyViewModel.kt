package com.rahul.nobrokerapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rahul.nobrokerapp.modelClass.ResponseClass
import com.rahul.nobrokerapp.repository.MyRepository
import com.rahul.nobrokerapp.room.entity.ListEntity

class MyViewModel(val repository: MyRepository) : ViewModel() {

    suspend fun getApi() {
        repository.getList()
    }

    fun displayList(): LiveData<ListEntity> {
        return repository.displayList()
    }
}