package com.example.forandroid.presentation.ProductView

import com.example.forandroid.data.repository.ProductRepository.ProductData
import com.example.forandroid.domain.Events.Event1

data class ProductViewModel(
    var productData: ProductData,
    var isVisible: Boolean,
    val onStateChange: Event1<ProductViewModel> = Event1()
) {

    fun toDefault() {
        productData.weight = 100f
        isVisible = false
    }

    fun update() {
        onStateChange(this)
    }
}