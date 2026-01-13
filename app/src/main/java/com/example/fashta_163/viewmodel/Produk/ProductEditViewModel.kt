package com.example.fashta_163.viewmodel.Produk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fashta_163.modeldata.DataProduct
import com.example.fashta_163.modeldata.DetailProduct
import com.example.fashta_163.modeldata.UIStateProduct
import com.example.fashta_163.modeldata.toDataProduct
import com.example.fashta_163.repository.RepositoryProduct

class ProductEditViewModel(
    private val repositoryProduct: RepositoryProduct
) : ViewModel() {

    var uiStateProduct by mutableStateOf(UIStateProduct())
        private set

    /** preload data dari ProductScreen */
    fun setProduct(data: DataProduct) {
        uiStateProduct = UIStateProduct(
            detailProduct = DetailProduct(
                product_id = data.product_id,
                product_name = data.product_name,
                category_id = data.category_id,
                image_url = data.image_url ?: ""
            ),
            isEntryValid = true
        )
    }

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

    suspend fun updateProduct() {
        if (!uiStateProduct.isEntryValid) {
            println("Validasi gagal")
            return
        }

        try {
            val detail = uiStateProduct.detailProduct

            val response =
                repositoryProduct.editDataProduct(
                    detail.product_id,
                    detail.toDataProduct()
                )

            if (response.isSuccessful) {
                println("Produk berhasil diupdate")
            } else {
                println("Gagal update")
            }
        } catch (e: Exception) {
            println("Error update product: ${e.message}")
        }
    }
}
