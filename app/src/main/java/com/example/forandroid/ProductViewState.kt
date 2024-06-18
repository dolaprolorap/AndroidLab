package com.example.forandroid

data class ProductViewState(
    var productData: ProductData,
    var isVisible: Boolean,
    val onStateChange: Event<ProductViewState> = Event()) {

    fun toDefault() {
        productData.weight = 100f
        isVisible = false
    }

    fun update() {
        onStateChange(this)
    }
}