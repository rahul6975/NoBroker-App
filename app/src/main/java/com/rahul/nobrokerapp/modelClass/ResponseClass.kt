package com.rahul.nobrokerapp.modelClass

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import java.io.Serializable

//model class for API Json response

@Generated("com.robohorse.robopojogenerator")
data class ResponseClass(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("subTitle")
    val subTitle: String? = null,

    @field:SerializedName("title")
    val title: String? = null
)