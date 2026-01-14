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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
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
    navigateToEdit: (DataProduct) -> Unit
) {
    val viewModel: ProductHomeViewModel =
        viewModel(factory = PenyediaViewModel.Factory)

    LaunchedEffect(Unit) {
        viewModel.loadProduct()
    }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var productToDelete by remember { mutableStateOf<DataProduct?>(null) }

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

            is StatusUiProduct.Loading ->
                Text("Loading...", Modifier.padding(padding))

            is StatusUiProduct.Error ->
                Text("Gagal load data", Modifier.padding(padding))

            is StatusUiProduct.Success ->
                LazyColumn(Modifier.padding(padding)) {
                    items(state.listProduct) { DataProduct ->
                        Card(
                            Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Column {

                                // ===== GAMBAR PRODUK =====
                                AsyncImage(
                                    model = DataProduct.image_url,
                                    contentDescription = DataProduct.product_name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(160.dp),
                                    contentScale = ContentScale.Crop
                                )

                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {

                                    Text(
                                        text = DataProduct.product_name,
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Text(
                                        text = "Kategori: ${DataProduct.category_name}",
                                        style = MaterialTheme.typography.bodySmall
                                    )

                                    Text(
                                        text = if (DataProduct.is_active == 1) "Status: Aktif" else "Status: Non-Aktif",
                                        color = if (DataProduct.is_active == 1) Color(0xFF4CAF50) else Color.Red, // Hijau atau Merah
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    // ===== AKSI DELETE (SOFT DELETE) =====
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        if (DataProduct.is_active == 1) {
                                            IconButton(onClick = {
                                                productToDelete = DataProduct
                                                showDeleteDialog = true
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Nonaktifkan",
                                                    tint = MaterialTheme.colorScheme.error
                                                )
                                            }
                                        } else {
                                            // Jika Non-Aktif -> Tampilkan Tombol Aktifkan
                                            Button(
                                                onClick = {
                                                    // Memanggil fungsi re-aktivasi
                                                    viewModel.reActivateProduct(DataProduct)
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color(0xFF4CAF50)
                                                )
                                            ) {
                                                Text("Aktifkan")
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
}
