package com.rahul.nobrokerapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahul.nobrokerapp.R
import com.rahul.nobrokerapp.`interface`.ClickListener
import com.rahul.nobrokerapp.adapter.ListAdapter
import com.rahul.nobrokerapp.repository.MyRepository
import com.rahul.nobrokerapp.room.entity.ListEntity
import com.rahul.nobrokerapp.viewModel.MyViewModel
import com.rahul.nobrokerapp.viewModel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ClickListener {
    lateinit var listApplication: MyApplication
    lateinit var myRepository: MyRepository
    private var userList = emptyList<ListEntity>()
    private lateinit var listAdapter: ListAdapter
    lateinit var viewModel: MyViewModel
    lateinit var viewModelFactory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getApi()
            runOnUiThread {
                fetchDataFromDB()
            }
        }
    }

    private fun fetchDataFromDB() {
        viewModel.displayList().observe(this, Observer {
            userList = listOf(it)
            listAdapter = ListAdapter(userList, this)
            setRecyclerView(listAdapter)
        })
    }

    private fun setRecyclerView(listAdapter: ListAdapter) {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = listAdapter
    }

    private fun initialize() {
        listApplication = application as MyApplication

        myRepository = listApplication.myRepository

        viewModelFactory = ViewModelFactory(myRepository)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MyViewModel::class.java)

    }

    override fun onClick(position: Int) {
        Toast.makeText(this, "$position", Toast.LENGTH_SHORT).show()
    }
}