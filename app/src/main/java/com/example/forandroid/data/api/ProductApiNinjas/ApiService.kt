package com.example.forandroid.data.api.ProductApiNinjas

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("nutrition")
    fun getProducts(@Query("query") productName: String, @Header("X-Api-Key") key: String): Call<List<ProductApiData>>
}