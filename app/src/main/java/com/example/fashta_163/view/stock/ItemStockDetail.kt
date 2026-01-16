package com.example.fashta_163.view.stock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ItemStockDetailCard(
    name: String,
    size: String,
    color: String,
    stock: Int
) {
    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(4.dp))

        Text("Size : $size")
        Text("Warna : $color")

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Stok Saat Ini: $stock",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
