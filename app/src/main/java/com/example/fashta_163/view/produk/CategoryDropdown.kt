package com.example.fashta_163.view.produk

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.fashta_163.viewmodel.pengaturan.CategoryReadViewModel
import com.example.fashta_163.viewmodel.pengaturan.StatusUiCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    categoryViewModel: CategoryReadViewModel,
    selectedCategoryId: Int,
    onCategorySelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    when (val state = categoryViewModel.statusUiCategory) {

        is StatusUiCategory.Success -> {

            val selectedCategory =
                state.listCategory.firstOrNull {
                    it.category_id == selectedCategoryId
                }?.category_name ?: "Pilih Kategori"

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Kategori*") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                    },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    state.listCategory.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.category_name) },
                            onClick = {
                                onCategorySelected(category.category_id)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        is StatusUiCategory.Loading -> {
            Text("Loading kategori...")
        }

        is StatusUiCategory.Error -> {
            Text("Gagal memuat kategori")
        }
    }
}
