package com.example.fashta_163.uicontroller.route

import com.example.fashta_163.R

object DestinasiProduct : DestinasiNavigasi {
    override val route = "product_home"
    override val titleRes = R.string.product //
}

object DestinasiProductCreate : DestinasiNavigasi {
    override val route = "product_create"
    override val titleRes = R.string.tambah_product
}

object DestinasiProductEdit {

    const val route = "product_edit/{productId}"

    fun createRoute(productId: Int): String {
        return "product_edit/$productId"
    }
}