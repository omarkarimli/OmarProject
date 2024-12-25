package com.omarkarimli.omarproject.model

import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
    @SerializedName("username")
    val username: String?,
    @SerializedName("password")
    val password: String?
)