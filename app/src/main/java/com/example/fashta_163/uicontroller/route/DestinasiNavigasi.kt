package com.example.fashta_163.uicontroller.route

import androidx.annotation.StringRes

interface DestinasiNavigasi {
    val route: String

    @get:StringRes
    val titleRes: Int
}
