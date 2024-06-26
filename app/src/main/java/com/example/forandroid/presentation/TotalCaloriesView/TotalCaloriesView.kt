package com.example.forandroid.presentation.TotalCaloriesView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RelativeLayout
import com.example.forandroid.data.repository.ProductRepository.ProductData
import com.example.forandroid.R
import com.example.forandroid.databinding.TotalCaloriesViewBinding
import com.example.forandroid.domain.Events.Event0

class TotalCaloriesView : RelativeLayout {
    var attributeSet: AttributeSet? = null
    val productList: MutableList<ProductData> = mutableListOf()
    val onAddProductBtnClicked: Event0 = Event0()
    val onMakeDietBtnClicked: Event0 = Event0()

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        this.attributeSet = attributeSet
    }

    constructor(context: Context) : super(context)

    private val binding =
        TotalCaloriesViewBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        addView(binding.root)
        updateView()

        val addProductBtn : Button = findViewById(R.id.addProductBtn)
        val makeDietBtn: Button = findViewById(R.id.makeDietBtn)

        addProductBtn.setOnClickListener {
            onAddProductBtnClicked()
        }

        makeDietBtn.setOnClickListener {
            onMakeDietBtnClicked()
        }
    }

    fun addProduct(prod: ProductData) {
        productList.add(prod)
        updateView()
    }

    fun removeProduct(prod: ProductData) {
        productList.remove(prod)
        updateView()
    }

    fun hasProduct(prod: ProductData) : Boolean {
        return productList.contains(prod)
    }

    fun updateView() {
        var totalCalories = 0f
        var totalProteins = 0f
        var totalFats = 0f
        var totalCarbohydrates = 0f

        for (product in productList) {
            totalCalories += product.calories
            totalProteins += product.proteins
            totalFats += product.fats
            totalCarbohydrates += product.carbohydrates
        }

        binding.totalCaloriesTV.text = "Calories:\n ${String.format("%.1f", totalCalories)}"
        binding.totalProteinsTV.text = "Proteins: ${String.format("%.1f", totalProteins)}"
        binding.totalFatsTV.text = "Fats: ${String.format("%.1f", totalFats)}"
        binding.totalCarbohydratesTV.text = "Carbohydrates: ${String.format("%.1f", totalCarbohydrates)}"
    }
}