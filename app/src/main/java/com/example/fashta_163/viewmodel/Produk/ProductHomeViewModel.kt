package com.example.fashta_163.viewmodel.Produk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashta_163.modeldata.DataProduct
import com.example.fashta_163.repository.RepositoryProduct
import kotlinx.coroutines.launch

sealed interface StatusUiProduct {
    data class Success(val listProduct: List<DataProduct>) : StatusUiProduct
    object Error : StatusUiProduct
    object Loading : StatusUiProduct
}

class ProductHomeViewModel(
    private val repositoryProduct: RepositoryProduct
) : ViewModel() {

    var listProduct: StatusUiProduct by mutableStateOf(StatusUiProduct.Loading)
        private set

    init {
        loadProduct()
    }

    fun loadProduct() {
        viewModelScope.launch {
            listProduct = StatusUiProduct.Loading
            listProduct = try {
                StatusUiProduct.Success(
                    repositoryProduct.getDataProduct()
                )
            } catch (e: Exception) {
                StatusUiProduct.Error
            }
        }
    }

    fun nonActiveProduct(id: Int) {
        viewModelScope.launch {
            repositoryProduct.hapusDataProduct(id)
            loadProduct()
        }
    }

    fun reActivateProduct(product: DataProduct) {
        viewModelScope.launch {
            // Kita menggunakan endpoint EDIT untuk mengaktifkan kembali (is_active = 1)
            val activeProduct = product.copy(is_active = 1)
            try {
                repositoryProduct.editDataProduct(product.product_id, activeProduct)
                loadProduct() // Reload setelah update
            } catch (e: Exception) {
                println("Gagal mengaktifkan kembali: ${e.message}")
            }
        }
    }
}
