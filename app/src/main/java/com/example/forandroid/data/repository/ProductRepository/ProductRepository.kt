package com.example.forandroid.data.repository.ProductRepository

class ProductRepository<T: IProductApi>(private val productApi: T) {
    fun getProduct(productName : String, callback : ((ProductData?) -> Unit)) {
        productApi.getProduct(productName, callback)
    }

    fun getProducts(productNames: List<String>, callback: (List<ProductData>) -> Unit) {
        productApi.getProducts(productNames, callback)
    }
}