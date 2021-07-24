package com.rahul.nobrokerapp.views

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.Shimmer
import com.rahul.nobrokerapp.R
import com.rahul.nobrokerapp.interfaces.ClickListener
import com.rahul.nobrokerapp.adapter.ListAdapter
import com.rahul.nobrokerapp.repository.MyRepository
import com.rahul.nobrokerapp.room.ListEntity
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
    lateinit var listAdapter: ListAdapter
    lateinit var viewModel: MyViewModel
    lateinit var viewModelFactory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
        setRecycler()
        fetchDataFromDB()

        val ConnectionManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected == true) {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getApi()
                runOnUiThread {
                    fetchDataFromDB()
                }
            }
        } else {
            fetchDataFromDB()
        }
    }

    private fun setRecycler() {
        listAdapter = ListAdapter(userList, this)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.apply {
            this.layoutManager = layoutManager
            adapter = listAdapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
    }

    private fun fetchDataFromDB() {
        viewModel.displayList().observe(this, Observer {
            userList = it
            listAdapter.updateList(userList)
            shimmer.stopShimmer()
            shimmer.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        })
    }


    private fun initialize() {
        listApplication = application as MyApplication

        myRepository = listApplication.myRepository

        viewModelFactory = ViewModelFactory(myRepository)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MyViewModel::class.java)
    }

    override fun onClick(position: Int) {
        val intent = Intent(this, AfterClickActivity::class.java)
        intent.putExtra("url", userList[position].url)
        intent.putExtra("title", userList[position].title)
        intent.putExtra("subtitle", userList[position].subtitle)
        startActivity(intent)
    }

    override fun onResume() {
        shimmer.startShimmer()
        super.onResume()
    }

    override fun onPause() {
        shimmer.stopShimmer()
        super.onPause()
    }
}