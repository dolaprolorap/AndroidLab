package com.example.forandroid.domain.UseCase

import com.example.forandroid.data.repository.ProductRepository.ProductData
import kotlin.math.min
import kotlin.math.round
import kotlin.random.Random

class MakeDietUseCase(private val productDataList: List<ProductData>) {

    init {
        if (productDataList.isEmpty()) {
            throw IllegalArgumentException("productDataList can not be empty")
        }
    }

    operator fun invoke(calories: Int, count: Int): List<ProductData> {
        if (count <= 0) {
            throw IllegalArgumentException("count can not be less than zero")
        }
        if (calories <= 0) {
            throw IllegalArgumentException("calories can not be less than zero")
        }

        val finalProductList = mutableListOf<ProductData>()
        val newProductList = mutableListOf<ProductData>()
        val caloriesPerProduct = calories * (1f / count)
        newProductList.addAll(productDataList)
        val realCount = min(newProductList.size, count)

        for (i in 1..realCount) {
            if(productDataList.isEmpty()) break
            var randInd = Random.nextInt(newProductList.size)
            while(newProductList[randInd].calories == 0f) {
                newProductList.removeAt(randInd)
                randInd = Random.nextInt(newProductList.size)
            }
            val randProduct = ProductData(newProductList[randInd])
            newProductList.removeAt(randInd)
            val weight = round((caloriesPerProduct / randProduct.caloriesPer1g) / 100) * 100
            randProduct.weight = weight
            finalProductList.add(randProduct)
        }

        return finalProductList
    }
}