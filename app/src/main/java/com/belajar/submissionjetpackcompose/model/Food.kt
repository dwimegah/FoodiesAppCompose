package com.belajar.submissionjetpackcompose.model

data class Food(
    val id: String,
    val name: String,
    val photoUrl: String,
    val description: String,
    val category: String,
    var isFavorite: Boolean = false
)
