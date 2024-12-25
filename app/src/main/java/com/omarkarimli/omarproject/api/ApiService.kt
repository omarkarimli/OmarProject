package com.omarkarimli.omarproject.api

import com.omarkarimli.omarproject.model.LoginRequestModel
import com.omarkarimli.omarproject.model.LoginResponseModel
import com.omarkarimli.omarproject.model.ProductModel
import com.omarkarimli.omarproject.model.ProductModelSecond
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("products")
    fun getProductsSecond() : Call<List<ProductModelSecond>>

    @GET("products")
    fun getProducts() : Call<List<ProductModel>>

    @GET("products/{id}")
    fun getProductById(@Path("id") id: Int) : Call<ProductModel>

    @POST("auth/login")
//    @FormUrlEncoded   // for turn encoded data to normal
    fun loginUser(@Body loginRequestModel: LoginRequestModel) : Call<LoginResponseModel>
}