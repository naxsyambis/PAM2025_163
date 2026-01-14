package com.example.fashta_163.uicontroller.route

import com.example.fashta_163.R

object DestinasiItemProduk : DestinasiNavigasi {
    override val route = "item_produk/{productId}"
    override val titleRes = R.string.item_product

    const val productIdArg = "productId"

    fun createRoute(productId: Int): String {
        return "item_produk/$productId"
    }
}

object DestinasiItemProdukCreate : DestinasiNavigasi {
    override val route = "item_produk_create/{productId}"
    override val titleRes = R.string.tambah_item_product

    fun createRoute(productId: Int): String {
        return "item_produk_create/$productId"
    }
}

object DestinasiItemProdukEdit : DestinasiNavigasi {
    override val route = "item_produk_edit/{itemId}"
    override val titleRes = R.string.edit_item_product

    const val itemIdArg = "itemId"

    fun createRoute(itemId: Int): String {
        return "item_produk_edit/$itemId"
    }
}