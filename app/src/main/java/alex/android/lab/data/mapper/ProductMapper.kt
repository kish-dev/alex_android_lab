package alex.android.lab.data.mapper

import alex.android.lab.data.dto.ProductInListDTO
import alex.android.lab.data.local.model.ProductDbModel
import alex.android.lab.domain.entities.Product
import javax.inject.Inject

class ProductMapper @Inject constructor() {

    fun mapDtoToNewDbModel(
        newDtoProduct: ProductInListDTO,
    ): ProductDbModel {
        return ProductDbModel(
            guid = newDtoProduct.guid,
            image = newDtoProduct.image,
            name = newDtoProduct.name,
            price = newDtoProduct.price,
            rating = newDtoProduct.rating,
            isFavorite = newDtoProduct.isFavorite,
            isInCart = newDtoProduct.isInCart,
            inCartCount = 0,
            viewCount = 0
        )
    }

    fun mapDtoToCurrentDbModel(
        newDtoProduct: ProductInListDTO,
        currentDbProduct: ProductDbModel,
    ): ProductDbModel {
        return ProductDbModel(
            guid = newDtoProduct.guid,
            image = newDtoProduct.image,
            name = newDtoProduct.name,
            price = newDtoProduct.price,
            rating = newDtoProduct.rating,
            isFavorite = currentDbProduct.isFavorite,
            isInCart = currentDbProduct.isInCart,
            inCartCount = currentDbProduct.inCartCount,
            viewCount = currentDbProduct.viewCount
        )
    }

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
}