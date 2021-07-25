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

    //hit the api using kotlin coroutines
    suspend fun getApi() {
        repository.getList()
    }

    //fetch all the data from database using livedata
    fun displayList(): LiveData<List<ListEntity>> {
        return repository.displayList()
    }

    //deletes all the record from database
    suspend fun deleteList() {
        repository.deletePreviousList()
    }
}