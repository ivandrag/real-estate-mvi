package com.example.presentation.utils

import java.text.NumberFormat
import java.util.Locale

fun Double.formatPrice(locale: Locale = Locale.getDefault()): String {
    val formatter = NumberFormat.getCurrencyInstance(locale).apply {
        maximumFractionDigits = 0
    }
    return formatter.format(this)
}
