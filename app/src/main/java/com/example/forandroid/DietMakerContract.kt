package com.example.forandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class DietMakerContract: ActivityResultContract<Unit, Int>() {
    companion object {
        const val CALORIES = "calories"
    }

    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, DietMakerActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int = when(resultCode) {
        Activity.RESULT_OK -> when(val calories = intent?.getIntExtra(CALORIES, 0)) {
            null -> throw Exception("Output calories from diet maker activity can not be null")
            else -> calories
        }
        else -> throw Exception("Diet maker activity result is not RESULT_OK")
    }
}