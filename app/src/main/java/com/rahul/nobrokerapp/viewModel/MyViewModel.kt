package com.rahul.nobrokerapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rahul.nobrokerapp.repository.MyRepository
import com.rahul.nobrokerapp.room.ListEntity

class MyViewModel(val repository: MyRepository) : ViewModel() {

    suspend fun getApi() {
        repository.getList()
    }

    fun displayList(): LiveData<List<ListEntity>> {
        return repository.displayList()
    }
}