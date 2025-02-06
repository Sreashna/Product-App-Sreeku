package sree.ddukk.productapp.domain

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import sree.ddukk.productapp.data.model.Product
import sree.ddukk.productapp.data.apicalls.RetrofitInstance

class ProductRepository {
    private val api = RetrofitInstance.api
    private val db = FirebaseFirestore.getInstance()

    // Fetch products from API
    fun getProducts(): Flow<List<Product>> = flow {
        try {
            val response = api.getProducts()
            emit(response.products)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    // Post product to API
    suspend fun addProduct(product: Product): Product {
        return try {
            api.addProduct(product)
        } catch (e: Exception) {
            throw e
        }
    }

    // Post product to Firebase Firestore (without images)
    suspend fun addProductToFirebase(product: Product): Product {
        val productData = hashMapOf(
            "title" to product.title,
            "description" to product.description,
            "price" to product.price,
            "category" to product.category,
            "stock" to product.stock,
            "brand" to product.brand,
            "images" to product.images // Keeping it for consistency, but you can remove if unnecessary
        )

        return try {
            val docRef = db.collection("products").add(productData).await()
            product.copy(id = docRef.id) // Return the product with Firestore-generated ID
        } catch (e: Exception) {
            throw e
        }
    }
}
