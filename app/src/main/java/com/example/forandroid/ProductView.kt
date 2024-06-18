package com.example.forandroid

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.forandroid.databinding.ProductViewBinding

class ProductView (
    context : Context,
    productViewState : ProductViewState,
    private val animator: LayoutAnimator = LayoutAnimator()
): LinearLayout(context) {
    private val hiddenWidth = 1300
    private val hiddenHeight = 200
    private val activeWidth = 1300
    private val activeHeight = ViewGroup.LayoutParams.WRAP_CONTENT

    var productViewState : ProductViewState = productViewState
        set(value) {
            field = value
            productViewState.onStateChange += this::stateChange
            bind()
            productViewState.update()
        }

    private var binding: ProductViewBinding =
        ProductViewBinding.inflate(LayoutInflater.from(context), this, false)

    private fun updateVisibility(isVisible : Boolean) {
        val isVis : Int = when(isVisible) {
            true -> VISIBLE
            false -> INVISIBLE
        }
        binding.moreProductBtn.visibility = isVis
        binding.lessProductBtn.visibility = isVis
        binding.productWeightTV.visibility = isVis
        binding.productCaloriesTV.visibility = isVis
        binding.productProteinTV.visibility = isVis
        binding.productFatTV.visibility = isVis
        binding.productCarbohydratesTV.visibility = isVis
    }

    private fun updateText(productData : ProductData) {
        binding.productNameTV.text = productData.name
        binding.productCaloriesTV.text = "Calories: ${String.format("%.1f", productData.calories)}"
        binding.productWeightTV.text = String.format("%.1f", productData.weight)
        binding.productProteinTV.text = "Proteins: ${String.format("%.1f", productData.proteins)}"
        binding.productFatTV.text = "Fats: ${String.format("%.1f", productData.fats)}"
        binding.productCarbohydratesTV.text = "Carbohydrates: ${String.format("%.1f", productData.carbohydrates)}"
    }

    private fun updateSizes(isVisible: Boolean) {
        if(isVisible) {
            binding.productLL.layoutParams.height = activeHeight
            binding.productLL.layoutParams.width = activeWidth
        }
        else {
            binding.productLL.layoutParams.height = hiddenHeight
            binding.productLL.layoutParams.width = hiddenWidth
        }
    }

    private fun updateViewData() {
        updateText(productViewState.productData)
        updateVisibility(productViewState.isVisible)
        updateSizes(productViewState.isVisible)
    }

    private fun bind() {
        binding.apply {
            binding.moreProductBtn.setOnClickListener {
                productViewState.productData.weight += 100f
                productViewState.update()
            }

            binding.lessProductBtn.setOnClickListener {
                if (productViewState.productData.weight > 100) {
                    productViewState.productData.weight -= 100f
                }
                else {
                    animator.animate(binding.productLL, hiddenWidth, hiddenHeight)
                    productViewState.toDefault()
                }
                productViewState.update()
            }

            binding.productLL.setOnClickListener {
                if (productViewState.isVisible) {
                    animator.animate(binding.productLL, hiddenWidth, hiddenHeight)
                    productViewState.toDefault()
                }
                else {
                    animator.animate(binding.productLL, activeWidth, activeHeight)
                    productViewState.isVisible = true
                }
                productViewState.update()
            }
        }
    }

    private fun stateChange(productViewState: ProductViewState) {
        updateViewData()
    }

    init {
        addView(binding.root)
        binding.productLL.layoutParams.height = hiddenHeight
        binding.productLL.layoutParams.width = hiddenWidth
    }
}