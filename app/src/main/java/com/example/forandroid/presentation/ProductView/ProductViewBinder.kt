package com.example.forandroid.presentation.ProductView

import com.example.forandroid.domain.UseCase.MakeDietUseCase

class ProductViewBinder {
    fun bindMakeDiet(productStateList: List<ProductViewModel>, calories: Int, count: Int = 5) {
        val productDataList = productStateList.map {
            it.productData
        }
        val makeDietUseCase = MakeDietUseCase(productDataList)
        val diet = makeDietUseCase(calories, count)
        for (productState in productStateList) {
            for (dietProduct in diet) {
                if (productState.productData.name == dietProduct.name) {
                    productState.productData.weight = dietProduct.weight
                    productState.isVisible = true
                    break
                } else {
                    productState.toDefault()
                }
            }
        }
    }
}