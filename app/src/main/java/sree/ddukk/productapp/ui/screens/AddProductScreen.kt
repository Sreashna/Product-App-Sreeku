package sree.ddukk.productapp.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import sree.ddukk.productapp.data.model.Product
import sree.ddukk.productapp.ui.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(navController: NavController, viewModel: ProductViewModel) {
    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isUrlSelected by remember { mutableStateOf(true) }

    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Product", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF84DA8F))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Product Information",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF5FE38F),
                    unfocusedBorderColor = Color.Gray
                )
            )

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF5FE38F),
                    unfocusedBorderColor = Color.Gray
                )
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF5FE38F),
                    unfocusedBorderColor = Color.Gray
                )
            )
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                Text("Image Source:", modifier = Modifier.weight(1f))

                Row {
                    RadioButton(
                        selected = isUrlSelected,
                        onClick = { isUrlSelected = true }
                    )
                    Text("URL")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Row {
                    RadioButton(
                        selected = !isUrlSelected,
                        onClick = { isUrlSelected = false }
                    )
                    Text("Gallery")
                }
            }
            if (isUrlSelected) {
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image URL") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF5FE38F),
                        unfocusedBorderColor = Color.Gray
                    )
                )
            } else {
                Button(
                    onClick = { imagePicker.launch("image/*") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5FE38F))
                ) {
                    Text("Pick Image")
                }
                selectedImageUri?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = "Picked Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(bottom = 12.dp)
                    )
                }
            }
            Button(
                onClick = {
                    val finalImage = if (isUrlSelected) imageUrl else selectedImageUri?.toString().orEmpty()

                    val newProduct = Product(
                        id = "0",
                        title = title,
                        price = price.toDoubleOrNull() ?: 0.0,
                        description = description,
                        thumbnail = finalImage,
                        category = "Misc",
                        brand = "Generic",
                        discountPercentage = 0.0,
                        rating = 0.0,
                        stock = 0,
                        images = listOf(finalImage)
                    )

                    // Call the method to add the product to Firebase
                    viewModel.addProductToFirebase(newProduct, selectedImageUri) {
                        navController.popBackStack()  // Navigate back after success
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp).padding(vertical = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF086C35))
            ) {
                Text("Add Product", color = Color.White)
            }

        }
        }
    }

