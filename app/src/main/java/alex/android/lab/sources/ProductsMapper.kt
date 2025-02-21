package alex.android.lab.sources

fun ProductInListDTO.toVO() : ProductInListVO =
    ProductInListVO(guid, image, name, price, rating, isFavorite, isInCart)