package alex.android.lab.data.mapper

import com.example.core_model.domain.Product
import javax.inject.Inject

internal class ProductMapper @Inject constructor() {

    fun mapDbModelToEntity(dbModel: com.example.core_model.data.db.ProductDbModel): Product =
        Product(
            guid = dbModel.guid,
            image = dbModel.image,
            name = dbModel.name,
            price = dbModel.price,
            rating = dbModel.rating,
            isFavorite = dbModel.isFavorite,
            isInCart = dbModel.isInCart,
            inCartCount = dbModel.inCartCount,
            viewCount = dbModel.viewCount
        )

    fun mapEntityToDbModel(product: Product): com.example.core_model.data.db.ProductDbModel =
        com.example.core_model.data.db.ProductDbModel(
            guid = product.guid,
            image = product.image,
            name = product.name,
            price = product.price,
            rating = product.rating,
            isFavorite = product.isFavorite,
            isInCart = product.isInCart,
            inCartCount = product.inCartCount,
            viewCount = product.viewCount
        )
}