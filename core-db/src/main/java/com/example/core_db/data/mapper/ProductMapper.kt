package alex.android.lab.data.mapper

import com.example.core_db.data.model.ProductDbModel
import com.example.core_db_api.domain.entities.Product
import javax.inject.Inject

internal class ProductMapper @Inject constructor() {

    fun mapDbModelToEntity(dbModel: ProductDbModel): Product =
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

    fun mapEntityToDbModel(product: Product): ProductDbModel =
        ProductDbModel(
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