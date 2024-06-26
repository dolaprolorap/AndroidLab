package com.example.forandroid.presentation.MainActivity

import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.forandroid.data.api.ProductApiNinjas.ProductApi
import com.example.forandroid.R
import com.example.forandroid.data.repository.ProductRepository.ProductRepository
import com.example.forandroid.presentation.AddProductActivity.AddProductContract
import com.example.forandroid.presentation.DietMakerActivity.DietMakerContract
import com.example.forandroid.presentation.ProductView.ProductAdapter
import com.example.forandroid.presentation.ProductView.ProductViewModel
import com.example.forandroid.presentation.ProductView.ProductViewBinder
import com.example.forandroid.presentation.TotalCaloriesView.TotalCaloriesView

class MainActivity : AppCompatActivity() {
    private val getContentAddProduct = registerForActivityResult(AddProductContract()) {
        if(it != null) {
            val productViewModel = ProductViewModel(it, false)
            productViewModel.onStateChange += ::onChangeProductViewVisibility
            adapter?.add(productViewModel)
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

        val productRepo = ProductRepository(ProductApi())
        productRepo.getProducts(listOfProducts) { prods ->
            adapter?.addAll(prods.map { ProductViewModel(it, false) })
            adapter?.getAllProductViewState()?.forEach {
                it.onStateChange += ::onChangeProductViewVisibility
            }
        }
    }

    fun onChangeProductViewVisibility(pv : ProductViewModel) {
        if(pv.isVisible && !totalCaloriesView?.hasProduct(pv.productData)!!) {
            totalCaloriesView?.addProduct(pv.productData)
        }
        if(!pv.isVisible) {
            totalCaloriesView?.removeProduct(pv.productData)
        }
        totalCaloriesView?.updateView()
    }
}