package sree.ddukk.productapp.data.apicalls

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import sree.ddukk.productapp.data.model.Product
import sree.ddukk.productapp.data.model.ProductResponse

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): ProductResponse
    @POST("products/add")
    suspend fun addProduct(@Body product: Product): Product
}
