package com.omarkarimli.omarproject.model


import com.google.gson.annotations.SerializedName

data class ProductModelSecond(
    @SerializedName("category")
    val category: Category?,
    @SerializedName("creationAt")
    val creationAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("images")
    val images: List<String?>?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)