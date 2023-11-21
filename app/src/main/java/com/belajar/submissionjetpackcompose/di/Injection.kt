package com.belajar.submissionjetpackcompose.di

import com.belajar.submissionjetpackcompose.data.FoodRepository

object Injection {
    fun provideRepository(): FoodRepository {
        return FoodRepository.getInstance()
    }
}