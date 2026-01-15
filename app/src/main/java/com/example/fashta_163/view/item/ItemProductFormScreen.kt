package com.example.fashta_163.view.item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fashta_163.modeldata.DetailItemProduct

private val sizeOptions = listOf("S", "M", "L", "XL", "XXL")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemProductForm(
    modifier: Modifier = Modifier,
    detail: DetailItemProduct,
    onValueChange: (DetailItemProduct) -> Unit,
    isButtonEnabled: Boolean,
    buttonText: String,
    onSubmit: () -> Unit,
    title: String = "Item Produk"
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {


            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = detail.size,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Ukuran*") },
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    sizeOptions.forEach { size ->
                        DropdownMenuItem(
                            text = { Text(size) },
                            onClick = {
                                onValueChange(detail.copy(size = size))
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            /* ===== COLOR ===== */
            OutlinedTextField(
                value = detail.color,
                onValueChange = {
                    onValueChange(detail.copy(color = it))
                },
                label = { Text("Warna*") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            /* ===== PRICE (ANGKA SAJA) ===== */
            OutlinedTextField(
                value = detail.price,
                onValueChange = { input ->
                    if (input.all { it.isDigit() }) {
                        onValueChange(detail.copy(price = input))
                    }
                },
                label = { Text("Harga*") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onSubmit,
                enabled = isButtonEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(buttonText)
            }
        }
    }
}