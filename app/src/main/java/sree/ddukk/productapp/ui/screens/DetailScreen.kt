package sree.ddukk.productapp.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import sree.ddukk.productapp.ui.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(productId: Int, viewModel: ProductViewModel) {
    val products by viewModel.products.collectAsState()
    val product = products.find { it.id == productId }

    product?.let {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(it.title, color = Color.White) },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF2A4FA4))
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = it.thumbnail,
                    contentDescription = it.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Color.Blue, RoundedCornerShape(16.dp))
//                        .shadow(2.dp, RoundedCornerShape(13.dp),color = Color.Black)
                        .padding(bottom = 16.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Price: $${it.price}", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = Color(
                            0xFF145096
                        ), modifier = Modifier.padding(bottom = 8.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        ) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = "Stock", modifier = Modifier.size(24.dp))
                            Text("Stock: ${it.stock}", style = MaterialTheme.typography.bodyMedium)

                            Icon(Icons.Filled.Star, contentDescription = "Rating", modifier = Modifier.size(24.dp))
                            Text("Rating: ${it.rating}", style = MaterialTheme.typography.bodyMedium)
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        ) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = "Category", modifier = Modifier.size(24.dp))
                            Text("Category: ${it.category}", style = MaterialTheme.typography.bodyMedium)

                            Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Brand", modifier = Modifier.size(24.dp))
                            Text("Brand: ${it.brand}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Description", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp), color = Color.Blue)
                Surface(
                    color = Color(0xFFDBE1EC),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Text(it.description, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(16.dp))
                }
            }
        }
    } ?: Text("Product not found!", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
}

