package com.example.forandroid

class ProductViewBinder {
    fun bindMakeDiet(productStateList: List<ProductViewState>, calories: Int, count: Int = 5) {
        val productDataList = productStateList.map {
            it.productData
        }
        val dietMaker = DietMaker()
        val diet = dietMaker.makeDiet(productDataList, calories, count)
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