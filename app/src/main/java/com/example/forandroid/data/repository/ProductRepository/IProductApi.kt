package com.example.forandroid.data.repository.ProductRepository

interface IProductApi {
    fun getProduct(productName : String, callback : ((ProductData?) -> Unit))
    fun getProducts(productNames: List<String>, callback: (List<ProductData>) -> Unit)
}