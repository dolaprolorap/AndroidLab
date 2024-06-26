package com.example.forandroid.data.api.ProductApiNinjas

import com.google.gson.annotations.SerializedName

data class ProductApiData (
    @SerializedName("name")
    var name: String,

//    @SerializedName("calories")
//    var calories: Float,

//    @SerializedName("serving_size_g")
//    var serving_size_g: Float,

    @SerializedName("fat_total_g")
    var fat_total_g: Float,

    @SerializedName("fat_saturated_g")
    var fat_saturated_g: Float,

//    @SerializedName("protein_g")
//    var protein_g: Float,

    @SerializedName("sodium_g")
    var sodium_g: Float,

    @SerializedName("potassium_g")
    var potassium_g: Float,

    @SerializedName("cholesterol_mg")
    var cholesterol_mg: Float,

    @SerializedName("carbohydrates_total_g")
    var carbohydrates_total_g: Float,

    @SerializedName("fiber_g")
    var fiber_g: Float,

    @SerializedName("sugar_g")
    var sugar_g: Float
)