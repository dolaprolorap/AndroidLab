package com.example.forandroid.data.api.ProductApiNinjas

import android.util.Log
import com.example.forandroid.data.repository.ProductRepository.IProductApi
import com.example.forandroid.data.repository.ProductRepository.ProductData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val apiUrl = "https://api.api-ninjas.com/v1/"
const val apiKey = "r+iixRun17TpNE4ijp76/Q==dvV29sAF1rpZOiZy"

class ProductApi(
    private val retrofit : Retrofit = Retrofit.Builder()
    .baseUrl(apiUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .build(),
    private val apiService : ApiService = retrofit.create(ApiService::class.java)) : IProductApi {

    override fun getProduct(productName : String, callback : ((ProductData?) -> Unit)) {
        val products : Call<List<ProductApiData>> = apiService.getProducts(productName, apiKey)
        products.enqueue(object : Callback<List<ProductApiData>> {
            override fun onResponse(call: Call<List<ProductApiData>>, response: Response<List<ProductApiData>>) {
                if (!response.isSuccessful) {
                    Log.e("ERROR", "onResponse was not success: ${response.errorBody()}")
                    throw Exception("onResponse was not success: ${response.errorBody()}")
                }
                val prods : List<ProductApiData> = response.body() ?: return
                if (prods.count() != 1) {
                    callback(null)
                }
                else {
                    callback(ProductData(prods[0]))
                }
            }

            override fun onFailure(call: Call<List<ProductApiData>>, t: Throwable) {
                Log.e("ERROR", "onFailure: ${t.message}")
                throw Exception("onFailure: ${t.message}")
            }
        })
    }

    override fun getProducts(productNames: List<String>, callback: (List<ProductData>) -> Unit) {
        val productNamesConc : String = productNames.joinToString(" and ")
        val products : Call<List<ProductApiData>> = apiService.getProducts(productNamesConc, apiKey)
        products.enqueue(object : Callback<List<ProductApiData>> {
            override fun onResponse(call: Call<List<ProductApiData>>, response: Response<List<ProductApiData>>) {
                if (!response.isSuccessful) {
                    Log.e("ERROR", "onResponse was not success: ${response.errorBody()}")
                    throw Exception("onResponse was not success: ${response.errorBody()}")
                }
                val prods : List<ProductApiData> = response.body() ?: return
                callback(prods.map { ProductData(it) })
            }

            override fun onFailure(call: Call<List<ProductApiData>>, t: Throwable) {
                Log.e("ERROR", "onFailure: ${t.message}")
                throw Exception("onFailure: ${t.message}")
            }
        })
    }
}