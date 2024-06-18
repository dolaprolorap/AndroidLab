package com.example.forandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class AddProductContract : ActivityResultContract<Int, ProductData?>() {
    override fun createIntent(context: Context, input: Int): Intent {
        return Intent(context, AddProductActivity::class.java).
                putExtra("stub", 0)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ProductData? = when {
        resultCode != Activity.RESULT_OK -> null
        else -> intent?.getParcelableExtra("product_name", ProductData::class.java)
    }
}