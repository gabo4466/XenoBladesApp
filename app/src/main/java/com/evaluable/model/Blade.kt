package com.evaluable.model

import androidx.compose.ui.graphics.Color
import com.evaluable.ui.theme.*

class Blade(var id: String, var name: String, var description: String?, var element: String?) {

    val elementColors = mapOf<String, Color>("Electricidad" to Electricity100, "Tierra" to Earth100, "Viento" to Wind100, "Hielo" to Ice100, "Agua" to Water100, "Fuego" to Fire100, "Luz" to Light100, "Oscuridad" to Dark100)

    fun getColorElement(): Color? {
        return elementColors[element]
    }

}