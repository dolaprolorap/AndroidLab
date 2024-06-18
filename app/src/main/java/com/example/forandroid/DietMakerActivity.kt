package com.example.forandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.forandroid.databinding.ActivityDietMakerBinding

class DietMakerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_diet_maker)

        val inputET = findViewById<EditText>(R.id.dietMakerInputET)
        val closeBtn = findViewById<Button>(R.id.dietMakerCloseBtn)
        val makeBtn = findViewById<Button>(R.id.dietMakerMakeBtn)

        val intent = Intent()

        closeBtn.setOnClickListener {
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        makeBtn.setOnClickListener {
            val calories = when(val caloriesAsInt = inputET.text.toString().toIntOrNull()) {
                null -> throw Exception("Calories input in Diet Maker Activity can not contain non digit symbols")
                else -> caloriesAsInt
            }
            intent.putExtra(DietMakerContract.CALORIES, calories)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}