package com.rahul.nobrokerapp.views

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), ClickListener {

    lateinit var listApplication: MyApplication
    lateinit var myRepository: MyRepository
    private var userList = emptyList<ListEntity>()
    lateinit var listAdapter: ListAdapter
    private lateinit var newList: ArrayList<ListEntity>
    private lateinit var tempArrayList: ArrayList<ListEntity>
    lateinit var viewModel: MyViewModel
    lateinit var viewModelFactory: ViewModelFactory
    var checkIfThereInDatabase: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
        setRecycler()
        fetchDataFromDB()

        //checks if internet access if available, if yes then call the api or else get data from database

        val ConnectionManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected == true) {

            viewModel.displayList().observe(this, Observer {

                /*
                if launching app for the first time then it[0].title will throw index out of bound exception
                so we catch it and it the api and not let our app crash
                 */
                try {
                    checkIfThereInDatabase = it[0].title
                } catch (e: Exception) {
                    if (checkIfThereInDatabase == null) {

                        /*
                         if internet access is there and also database contains previous data then delete all
                         data from database and hit the api and store the new list into database
                          */
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.deleteList()
                        }
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.getApi()
                            runOnUiThread {
                                fetchDataFromDB()
                            }
                        }
                    } else {
                        /*
                        if checkIfThereInDatabase is not null means database contains previous data,
                        so we don't need to hit api again and directly fetch the data from database
                         */

                        fetchDataFromDB()
                    }
                }

            })
        }

        //watch the changes in the edittext and filter recyclerview accordingly
        etSearch.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    tempArrayList.clear()
                    val searchText = s.toString().lowercase(Locale.getDefault())
                    if (searchText.isNotEmpty()) {
                        newList.forEach {
                            if (it.title.lowercase(Locale.getDefault()).contains(searchText)
                                || it.subtitle.lowercase(Locale.getDefault()).contains(searchText)
                            ) {
                                tempArrayList.add(it)
                            }
                        }
                        listAdapter.notifyDataSetChanged()
                    } else {
                        tempArrayList.clear()
                        tempArrayList.addAll(newList)
                        listAdapter.notifyDataSetChanged()
                    }

                }

                override fun afterTextChanged(s: Editable?) {

                }

            })


    }

    //sets the recyclerview
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


    //fetches the data from database
    private fun fetchDataFromDB() {
        viewModel.displayList().observe(this, Observer {
            userList = it
            newList.addAll(it)
            tempArrayList.addAll(it)
            listAdapter.updateList(tempArrayList)
            shimmer.stopShimmer()
            shimmer.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        })
    }


    //initialize variables
    private fun initialize() {
        listApplication = application as MyApplication

        myRepository = listApplication.myRepository

        viewModelFactory = ViewModelFactory(myRepository)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MyViewModel::class.java)

        tempArrayList = arrayListOf<ListEntity>()
        newList = arrayListOf<ListEntity>()

    }

    // overriding onClick function from our interface
    override fun onClick(position: Int) {
        val intent = Intent(this, AfterClickActivity::class.java)
        intent.putExtra("url", userList[position].url)
        intent.putExtra("title", userList[position].title)
        intent.putExtra("subtitle", userList[position].subtitle)
        startActivity(intent)
    }

    override fun onResume() {
        shimmer.startShimmer()    // starts our shimmer effect
        super.onResume()
    }

    override fun onPause() {
        shimmer.stopShimmer()    // stop our shimmer effect when activity is paused
        super.onPause()
    }
}