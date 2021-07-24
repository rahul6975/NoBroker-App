package com.rahul.nobrokerapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rahul.nobrokerapp.repository.MyRepository
import com.rahul.nobrokerapp.room.ListEntity

/**
 * This is a VM layer in the `MVVM` architecture, where we are notifying the Activity/view about the
 * response changes via live data
 */
class MyViewModel(private val repository: MyRepository) : ViewModel() {

    suspend fun getApi() {
        repository.getList()
    }

    fun displayList(): LiveData<List<ListEntity>> {
        return repository.displayList()
    }
}