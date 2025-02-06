package sree.ddukk.productapp.data.model

data class ProductResponse(
    val products: List<Product>
)

data class Product(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val images: List<String>,
    val thumbnail: String
)