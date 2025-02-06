package sree.ddukk.productapp.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import sree.ddukk.productapp.data.model.Product
import sree.ddukk.productapp.data.apicalls.RetrofitInstance

class ProductRepository {
    private val api = RetrofitInstance.api

    fun getProducts(): Flow<List<Product>> = flow {
        try {
            val response = api.getProducts()
            emit(response.products)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    suspend fun addProduct(product: Product): Product {
        return try {
            api.addProduct(product)
        } catch (e: Exception) {
            throw e
        }
    }
}
