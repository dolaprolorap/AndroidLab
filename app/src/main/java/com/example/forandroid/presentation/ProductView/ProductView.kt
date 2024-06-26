package com.example.forandroid.presentation.ProductView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.forandroid.data.repository.ProductRepository.ProductData
import com.example.forandroid.databinding.ProductViewBinding
import com.example.forandroid.presentation.Animators.LayoutAnimator

class ProductView (
    context : Context,
    productViewModel : ProductViewModel,
    private val animator: LayoutAnimator = LayoutAnimator()
): LinearLayout(context) {
    private val hiddenWidth = 1300
    private val hiddenHeight = 200
    private val activeWidth = 1300
    private val activeHeight = ViewGroup.LayoutParams.WRAP_CONTENT

    var productViewModel : ProductViewModel = productViewModel
        set(value) {
            field = value
            productViewModel.onStateChange += this::stateChange
            bind()
            productViewModel.update()
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
        updateText(productViewModel.productData)
        updateVisibility(productViewModel.isVisible)
        updateSizes(productViewModel.isVisible)
    }

    private fun bind() {
        binding.apply {
            binding.moreProductBtn.setOnClickListener {
                productViewModel.productData.weight += 100f
                productViewModel.update()
            }

            binding.lessProductBtn.setOnClickListener {
                if (productViewModel.productData.weight > 100) {
                    productViewModel.productData.weight -= 100f
                }
                else {
                    animator.animate(binding.productLL, hiddenWidth, hiddenHeight)
                    productViewModel.toDefault()
                }
                productViewModel.update()
            }

            binding.productLL.setOnClickListener {
                if (productViewModel.isVisible) {
                    animator.animate(binding.productLL, hiddenWidth, hiddenHeight)
                    productViewModel.toDefault()
                }
                else {
                    animator.animate(binding.productLL, activeWidth, activeHeight)
                    productViewModel.isVisible = true
                }
                productViewModel.update()
            }
        }
    }

    private fun stateChange(productViewModel: ProductViewModel) {
        updateViewData()
    }

    init {
        addView(binding.root)
        binding.productLL.layoutParams.height = hiddenHeight
        binding.productLL.layoutParams.width = hiddenWidth
    }
}