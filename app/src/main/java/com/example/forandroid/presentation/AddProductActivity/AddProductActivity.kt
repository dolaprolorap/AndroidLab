package com.example.forandroid.presentation.AddProductActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.forandroid.data.api.ProductApiNinjas.ProductApi
import com.example.forandroid.R
import com.example.forandroid.data.repository.ProductRepository.ProductRepository

class AddProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_product)

        val searchButton = findViewById<Button>(R.id.searchBtn)
        val productRepo = ProductRepository(ProductApi())
        val inputET = findViewById<EditText>(R.id.inputET)
        val describeTV = findViewById<TextView>(R.id.headerTV)
        val closeButton = findViewById<Button>(R.id.closeBtn)

        describeTV.text = getString(R.string.welcome_activity_add_product)

        searchButton.setOnClickListener {
            val prodName = inputET.text.toString()
            describeTV.text = getString(R.string.searching_activity_add_product)
            productRepo.getProduct(prodName) { productData ->
                if (productData != null) {
                    val intent = Intent()
                    intent.putExtra("product_name", productData)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
                else {
                    describeTV.text = getString(R.string.no_product_activity_add_product)
                }
            }
        }

        closeButton.setOnClickListener {
            finish()
        }
    }
}