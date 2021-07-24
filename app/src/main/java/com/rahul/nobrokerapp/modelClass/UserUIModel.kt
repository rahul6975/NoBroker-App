package com.rahul.nobrokerapp.modelClass

//sealed class which returns the data class depending upon the success/failure
sealed class UserUIModel {

    data class Success(val responseClass: List<ResponseClass>) : UserUIModel()

    data class Failure(val error: String) : UserUIModel()
}