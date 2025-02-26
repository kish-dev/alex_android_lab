package alex.android.lab.data.dto

import com.google.gson.annotations.SerializedName

data class ProductInListDTO(
    @SerializedName("guid") val guid: String,
    @SerializedName("image") val image: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("isFavorite") val isFavorite: Boolean,
    @SerializedName("isInCart") val isInCart: Boolean,
)