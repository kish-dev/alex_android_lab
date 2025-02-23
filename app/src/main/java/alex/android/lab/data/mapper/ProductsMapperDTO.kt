package alex.android.lab.data.mapper

import alex.android.lab.data.dto.ProductInListDTO
import alex.android.lab.domain.entities.Product

fun ProductInListDTO.toEntity(): Product =
    Product(guid, image, name, price, rating, isFavorite, isInCart)