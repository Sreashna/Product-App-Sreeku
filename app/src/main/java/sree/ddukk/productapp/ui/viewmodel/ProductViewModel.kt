package sree.ddukk.productapp.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sree.ddukk.productapp.domain.ProductRepository
import sree.ddukk.productapp.data.model.Product

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    init {
        fetchProducts()
    }
    private fun fetchProducts() {
        viewModelScope.launch {
            productRepository.getProducts().collect { productList ->
                _products.value = productList
            }
        }
    }

    fun postProduct(product: Product, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val addedProduct = productRepository.addProduct(product)
                _products.value = _products.value + addedProduct
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addProductToFirebase(product: Product, imageUri: Uri?, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val addedProduct = productRepository.addProductToFirebase(product)
                _products.value = _products.value + addedProduct
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
