package com.rahul.nobrokerapp.remote

import com.rahul.nobrokerapp.modelClass.ResponseClass
import retrofit2.Call
import retrofit2.http.GET

//this interface specifies the request type as GET

interface ApiClient {

    @GET("b/60fa8fefa917050205ce5470")
    suspend fun getResponse(): List<ResponseClass>
}