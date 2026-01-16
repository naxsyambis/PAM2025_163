package com.example.fashta_163.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class StockReport(
    val product_name: String,
    val size: String,
    val color: String,
    val movement_type: String,
    val quantity: Int,
    val reason: String,
    val note: String?,
    val created_at: String
)

