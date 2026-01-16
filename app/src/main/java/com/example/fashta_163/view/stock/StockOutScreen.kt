package com.example.fashta_163.view.stock

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel
import com.example.fashta_163.viewmodel.stock.StockViewModel

@Composable
fun StockOutScreen(
    itemId: Int,
    viewModel: StockViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navigateBack: () -> Unit
) {
    LaunchedEffect(itemId) {
        viewModel.loadItemDetail(itemId)
        viewModel.loadStock(itemId)
    }

    Column {

        viewModel.itemDetail?.let { detail ->
            ItemStockDetailCard(
                name = detail.product_name,
                size = detail.size,
                color = detail.color,
                stock = viewModel.currentStock
            )
        }

        StockForm(
            itemId = itemId,
            type = "OUT",
            viewModel = viewModel,
            onSuccess = {
                viewModel.loadStock(itemId)
                navigateBack()
            }
        )
    }
}