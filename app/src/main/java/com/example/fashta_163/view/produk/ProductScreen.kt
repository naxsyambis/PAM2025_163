package com.example.fashta_163.view.produk

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.fashta_163.modeldata.DataProduct
import com.example.fashta_163.viewmodel.Produk.ProductHomeViewModel
import com.example.fashta_163.viewmodel.Produk.StatusUiProduct
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    navigateToAdd: () -> Unit,
    navigateToEdit: (DataProduct) -> Unit,
    navigateToItemProduct: (Int) -> Unit
) {
    val viewModel: ProductHomeViewModel =
        viewModel(factory = PenyediaViewModel.Factory)

    var showDeleteDialog by remember { mutableStateOf(false) }
    var productToDelete by remember { mutableStateOf<DataProduct?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadProduct()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Produk") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToAdd) {
                Text("+")
            }
        }
    ) { padding ->

        when (val state = viewModel.listProduct) {

            is StatusUiProduct.Loading -> {
                Text("Loading...", Modifier.padding(padding))
            }

            is StatusUiProduct.Error -> {
                Text("Gagal load data", Modifier.padding(padding))
            }

            is StatusUiProduct.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(padding)
                ) {
                    items(state.listProduct) { product ->

                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clickable { navigateToItemProduct(product.product_id) }
                        ) {
                            Column {

                                // ===== GAMBAR PRODUK =====
                                AsyncImage(
                                    model = product.image_url,
                                    contentDescription = product.product_name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(160.dp),
                                    contentScale = ContentScale.Crop
                                )

                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {

                                    Text(
                                        text = product.product_name,
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Text(
                                        text = "Kategori: ${product.category_name}",
                                        style = MaterialTheme.typography.bodySmall
                                    )

                                    Text(
                                        text = if (product.is_active == 1)
                                            "Status: Aktif"
                                        else
                                            "Status: Non-Aktif",
                                        color = if (product.is_active == 1)
                                            Color(0xFF4CAF50)
                                        else
                                            Color.Red,
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        if (product.is_active == 1) {
                                            IconButton(onClick = {
                                                productToDelete = product
                                                showDeleteDialog = true
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Nonaktifkan",
                                                    tint = MaterialTheme.colorScheme.error
                                                )
                                            }
                                        } else {
                                            Button(
                                                onClick = {
                                                    viewModel.reActivateProduct(product)
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color(0xFF4CAF50)
                                                )
                                            ) {
                                                Text("Aktifkan")
                                            }
                                        }

                                        IconButton(
                                            onClick = { navigateToEdit(product) },
                                            enabled = product.is_active == 1
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "Edit Produk",
                                                tint = if (product.is_active == 1)
                                                    MaterialTheme.colorScheme.primary
                                                else
                                                    Color.Gray
                                            )
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDeleteDialog && productToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Nonaktifkan Produk") },
            text = {
                Text("Produk akan disembunyikan (soft delete), bukan dihapus permanen.")
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.nonActiveProduct(productToDelete!!.product_id)
                    showDeleteDialog = false
                }) {
                    Text("Nonaktifkan")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}