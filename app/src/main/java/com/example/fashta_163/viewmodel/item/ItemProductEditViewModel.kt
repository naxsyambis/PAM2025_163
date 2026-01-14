package com.example.fashta_163.viewmodel.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.fashta_163.modeldata.DataItemProduct
import com.example.fashta_163.modeldata.DetailItemProduct
import com.example.fashta_163.modeldata.UIStateItemProduct
import com.example.fashta_163.modeldata.toDataItemProduct
import com.example.fashta_163.modeldata.toDetailItemProduct
import com.example.fashta_163.repository.RepositoryItemProduct
import com.example.fashta_163.uicontroller.route.DestinasiItemProdukEdit

class ItemProductEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryItemProduct: RepositoryItemProduct
) : ViewModel() {

    private val itemId: Int =
        checkNotNull(savedStateHandle[DestinasiItemProdukEdit.itemIdArg])

    var uiStateItemProduct = UIStateItemProduct()
        private set

    fun setItemProduct(data: DataItemProduct) {
        uiStateItemProduct = UIStateItemProduct(
            detailItemProduct = data.toDetailItemProduct(),
            isEntryValid = true
        )
    }

    fun updateUiState(detailItemProduct: DetailItemProduct) {
        uiStateItemProduct = UIStateItemProduct(
            detailItemProduct = detailItemProduct,
            isEntryValid = true
        )
    }

    suspend fun updateItemProduct() {
        val detail = uiStateItemProduct.detailItemProduct
        repositoryItemProduct.updateItemProduct(
            itemId,
            detail.toDataItemProduct()
        )
    }
}