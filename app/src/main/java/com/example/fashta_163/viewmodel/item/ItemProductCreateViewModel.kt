package com.example.fashta_163.viewmodel.item

import androidx.lifecycle.ViewModel
import com.example.fashta_163.modeldata.DetailItemProduct
import com.example.fashta_163.modeldata.UIStateItemProduct
import com.example.fashta_163.modeldata.toDataItemProduct
import com.example.fashta_163.repository.RepositoryItemProduct

class ItemProductCreateViewModel(
    private val repositoryItemProduct: RepositoryItemProduct
) : ViewModel() {

    var uiStateItemProduct = UIStateItemProduct()
        private set

    private fun validasiInput(
        detail: DetailItemProduct = uiStateItemProduct.detailItemProduct
    ): Boolean {
        return detail.size.isNotBlank()
                && detail.color.isNotBlank()
                && detail.price.isNotBlank()
                && detail.price.toDoubleOrNull() != null
    }

    fun updateUiState(detailItemProduct: DetailItemProduct) {
        uiStateItemProduct = UIStateItemProduct(
            detailItemProduct = detailItemProduct,
            isEntryValid = validasiInput(detailItemProduct)
        )
    }

    suspend fun addItemProduct() {
        if (!uiStateItemProduct.isEntryValid) return

        repositoryItemProduct.postItemProduct(
            uiStateItemProduct.detailItemProduct.toDataItemProduct()
        )
    }
}