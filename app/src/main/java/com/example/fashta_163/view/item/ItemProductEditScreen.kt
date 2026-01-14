package com.example.fashta_163.view.item

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fashta_163.viewmodel.item.ItemProductEditViewModel
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemProductEditScreen(
    navigateBack: () -> Unit
) {
    val viewModel: ItemProductEditViewModel =
        viewModel(factory = PenyediaViewModel.Factory)

    val uiState = viewModel.uiStateItemProduct
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Edit Item Produk") })
        }
    ) { padding ->

        ItemProductForm(
            modifier = Modifier.padding(padding),
            detail = uiState.detailItemProduct,
            onValueChange = viewModel::updateUiState,
            isButtonEnabled = uiState.isEntryValid,
            buttonText = "Update",
            onSubmit = {
                coroutineScope.launch {
                    viewModel.updateItemProduct()
                    navigateBack()
                }
            }
        )
    }
}



