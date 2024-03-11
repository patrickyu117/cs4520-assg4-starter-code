package com.cs4520.assignment4

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey val name: String,
    val type: String,
    val expiryDate: String?,
    val price: Double
) {
    fun isValidProduct(): Boolean {
        return name.isNotBlank() && price > 0 && type.isNotBlank()
    }
}