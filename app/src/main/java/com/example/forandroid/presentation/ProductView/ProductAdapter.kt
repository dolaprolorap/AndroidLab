package com.example.forandroid.presentation.ProductView

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class ProductAdapter(private val context: Context, productData: MutableList<ProductViewModel>) :
    ArrayAdapter<ProductViewModel>(context, android.R.layout.simple_list_item_2, productData) {

    fun getAllProductViewState(): Array<ProductViewModel> {
        return Array(this.count) {
            getItem(it)!!
        }
    }

    fun forceOnStateChangeEvent() {
        getAllProductViewState().forEach {
            it.update()
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val prod : ProductViewModel = getItem(position) ?: throw IllegalArgumentException("Product is null")

        val resView : ProductView = when(convertView) {
            null -> ProductView(context, prod)
            else -> convertView as ProductView
        }

        resView.productViewModel = prod

        return resView
    }
}