package com.example.fashta_163.view.item

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ItemProductDeactivateDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nonaktifkan Item Produk") },
        text = {
            Text(
                "Item produk ini akan dinonaktifkan dan tidak dapat digunakan " +
                        "dalam proses operasional. Apakah Anda yakin?"
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Ya, Nonaktifkan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}

