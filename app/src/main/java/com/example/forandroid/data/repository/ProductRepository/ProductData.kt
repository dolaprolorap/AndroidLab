package com.example.forandroid.data.repository.ProductRepository

import android.os.Parcel
import android.os.Parcelable
import com.example.forandroid.data.api.ProductApiNinjas.ProductApiData
import java.security.InvalidParameterException

class ProductData (var name: String,
                   caloriesPer1g: Float,
                   proteinsPer1g: Float,
                   fatsPer1g: Float,
                   carbohydratesPer1g: Float,
                   weight: Float): Parcelable {

    constructor(productApiData: ProductApiData, weight: Float = 100f) : this(
        productApiData.name,
        productApiData.fat_saturated_g / weight,
        productApiData.cholesterol_mg / weight,
        productApiData.fat_total_g / weight,
        productApiData.carbohydrates_total_g / weight,
        weight
    )

    constructor(productData: ProductData) : this(
        productData.name,
        productData.caloriesPer1g,
        productData.proteinsPer1g,
        productData.fatsPer1g,
        productData.carbohydratesPer1g,
        productData.weight
    )

    constructor(parcel : Parcel) : this(
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeFloat(caloriesPer1g)
        parcel.writeFloat(proteinsPer1g)
        parcel.writeFloat(fatsPer1g)
        parcel.writeFloat(carbohydratesPer1g)
        parcel.writeFloat(weight)
    }

    override fun describeContents(): Int {
        return 0;
    }

    companion object CREATOR : Parcelable.Creator<ProductData> {
        override fun createFromParcel(parcel: Parcel): ProductData {
            return ProductData(parcel)
        }

        override fun newArray(size: Int): Array<ProductData?> {
            return arrayOfNulls(size)
        }
    }

    var caloriesPer1g = caloriesPer1g
        set(value) {
            field = if (value >= 0) value
            else throw InvalidParameterException("caloriesPer1g can not be < 0")
        }

    var proteinsPer1g = proteinsPer1g
        set(value) {
            field = if (value >= 0) value
            else throw InvalidParameterException("proteinsPer1g can not be < 0")
        }

    var fatsPer1g = fatsPer1g
        set(value) {
            field = if (value >= 0) value
            else throw InvalidParameterException("fatsPer1g can not be < 0")
        }

    var carbohydratesPer1g = carbohydratesPer1g
        set(value) {
            field = if (value >= 0) value
            else throw InvalidParameterException("carbohydratesPer1g can not be < 0")
        }

    var weight = weight
        set(value) {
            field = if (value >= 0) value
            else throw InvalidParameterException("weight can not be < 0")
        }

    val calories: Float
        get() = caloriesPer1g * weight

    val proteins: Float
        get() = proteinsPer1g * weight

    val fats: Float
        get() = fatsPer1g * weight

    val carbohydrates: Float
        get() = carbohydratesPer1g * weight
}