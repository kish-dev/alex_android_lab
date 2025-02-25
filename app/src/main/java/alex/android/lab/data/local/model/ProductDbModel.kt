package alex.android.lab.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "all_products_list")
data class ProductDbModel(
    @PrimaryKey(autoGenerate = false)
    val guid: String,
    val image: String,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val viewCount: Int,
)
