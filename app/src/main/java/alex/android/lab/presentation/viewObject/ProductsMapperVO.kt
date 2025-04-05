package alex.android.lab.presentation.viewObject

import alex.android.lab.domain.entities.Product

fun Product.toVO(): ProductInListVO {
    return ProductInListVO(
        guid,
        image,
        name,
        "$price ₽",
        rating,
        isFavorite,
        isInCart,
        inCartCount,
        viewCount
    )
}