package com.example.fashta_163.modeldata

import kotlinx.serialization.Serializable


@Serializable
data class DataProduct(
    val product_id: Int,
    val product_name: String,
    val category_id: Int,
    val category_name: String,
    val image_url: String? = null,
    val is_active: Int = 1
)

data class DetailProduct(
    val product_id: Int = 0,
    val product_name: String = "",
    val category_id: Int = 0,
    val image_url: String = ""
)

data class UIStateProduct(
    val detailProduct: DetailProduct = DetailProduct(),
    val isEntryValid: Boolean = false
)

fun DetailProduct.toDataProduct(): DataProduct = DataProduct(
    product_id = product_id,
    product_name = product_name,
    category_id = category_id,
    category_name = "",      // backend tidak butuh ini saat insert
    image_url = image_url,
    is_active = 1
)