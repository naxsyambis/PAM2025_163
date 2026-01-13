package com.example.fashta_163.viewmodel.Produk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fashta_163.modeldata.DetailProduct
import com.example.fashta_163.modeldata.UIStateProduct
import com.example.fashta_163.modeldata.toDataProduct
import com.example.fashta_163.repository.RepositoryProduct




class ProductCreateViewModel(
    private val repositoryProduct: RepositoryProduct
) : ViewModel() {

    var uiStateProduct by mutableStateOf(UIStateProduct())
        private set

    private fun validasiInput(
        detail: DetailProduct = uiStateProduct.detailProduct
    ): Boolean {
        return detail.product_name.isNotBlank()
                && detail.category_id != 0
    }

    fun updateUiState(detailProduct: DetailProduct) {
        uiStateProduct = UIStateProduct(
            detailProduct = detailProduct,
            isEntryValid = validasiInput(detailProduct)
        )
    }

    suspend fun addProduct() {
        if (!uiStateProduct.isEntryValid) {
            println("Validasi gagal")
            return
        }

        try {
            val response =
                repositoryProduct.postDataProduct(
                    uiStateProduct.detailProduct.toDataProduct()
                )

            if (response.isSuccessful) {
                println("Produk berhasil ditambahkan")
            } else {
                println("Gagal: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            println("Error insert product: ${e.message}")
        }
    }
}