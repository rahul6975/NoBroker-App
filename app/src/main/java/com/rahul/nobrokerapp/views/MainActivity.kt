package com.rahul.nobrokerapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.rahul.nobrokerapp.R
import com.rahul.nobrokerapp.repository.MyRepository
import com.rahul.nobrokerapp.viewModel.MyViewModel
import com.rahul.nobrokerapp.viewModel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class MainActivity : AppCompatActivity() {
    lateinit var listApplication: MyApplication
    lateinit var myRepository: MyRepository
    lateinit var viewModel: MyViewModel
    lateinit var viewModelFactory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getApi()
        }
    }

    private fun initialize() {
        listApplication = application as MyApplication

        myRepository = listApplication.myRepository

        viewModelFactory = ViewModelFactory(myRepository)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MyViewModel::class.java)

    }
}