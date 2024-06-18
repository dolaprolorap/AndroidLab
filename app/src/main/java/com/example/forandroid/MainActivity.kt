package com.example.forandroid

import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val getContentAddProduct = registerForActivityResult(AddProductContract()) {
        if(it != null) {
            val productViewState = ProductViewState(it, false)
            productViewState.onStateChange += ::onChangeProductViewVisibility
            adapter?.add(productViewState)
            adapter?.notifyDataSetChanged()
        }
    }

    private val productViewBinder = ProductViewBinder()
    private val getContentMakeDiet = registerForActivityResult(DietMakerContract()) { calories ->
        adapter?.getAllProductViewState()?.let { it1 -> productViewBinder.bindMakeDiet(it1.toList(), calories) }
        adapter?.forceOnStateChangeEvent()
        adapter?.notifyDataSetChanged()
    }

    private var adapter: ProductAdapter? = null

    private var totalCaloriesView: TotalCaloriesView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listOfProducts = listOf(
            "chicken",
            "egg",
            "bread",
            "potato",
            "tomato",
            "milk",
            "pork",
            "beef",
            "sugar",
            "cucumber",
            "honey",
            "tea",
            "coffee",
            "garlic"
        )

        totalCaloriesView = findViewById(R.id.totalCaloriesView)
        val productsLV = findViewById<ListView>(R.id.productsLV)

        totalCaloriesView?.onAddProductBtnClicked?.plusAssign {
            getContentAddProduct.launch(1)
        }

        totalCaloriesView?.onMakeDietBtnClicked?.plusAssign {
            getContentMakeDiet.launch(Unit)
        }

        adapter = ProductAdapter(
            this,
            mutableListOf()
        )

        productsLV?.adapter = adapter

        val productApi = ProductApi()
        productApi.getProducts(listOfProducts) { prods ->
            adapter?.addAll(prods.map { ProductViewState(it, false) })
            adapter?.getAllProductViewState()?.forEach {
                it.onStateChange += ::onChangeProductViewVisibility
            }
        }
    }

    fun onChangeProductViewVisibility(pv : ProductViewState) {
        if(pv.isVisible && !totalCaloriesView?.hasProduct(pv.productData)!!) {
            totalCaloriesView?.addProduct(pv.productData)
        }
        if(!pv.isVisible) {
            totalCaloriesView?.removeProduct(pv.productData)
        }
        totalCaloriesView?.updateView()
    }
}