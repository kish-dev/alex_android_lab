package alex.android.lab.data.network

import alex.android.lab.data.dto.ProductInListDTO
import retrofit2.http.GET

interface ApiService {

    @GET("products")
    suspend fun getProductsList(): List<ProductInListDTO>
}