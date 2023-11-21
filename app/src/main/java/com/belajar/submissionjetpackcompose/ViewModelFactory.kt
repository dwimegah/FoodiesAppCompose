package com.belajar.submissionjetpackcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.belajar.submissionjetpackcompose.data.FoodRepository
import com.belajar.submissionjetpackcompose.ui.screen.detail.DetailViewModel
import com.belajar.submissionjetpackcompose.ui.screen.favorite.FavoriteViewModel
import com.belajar.submissionjetpackcompose.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: FoodRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}