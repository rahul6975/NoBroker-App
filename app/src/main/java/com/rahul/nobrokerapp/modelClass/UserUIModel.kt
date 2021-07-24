package com.rahul.nobrokerapp.modelClass

sealed class UserUIModel {

    data class Success(val responseClass: List<ResponseClass>) : UserUIModel()

    data class Failure(val error: String) : UserUIModel()
}