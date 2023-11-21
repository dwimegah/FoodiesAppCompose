package com.belajar.submissionjetpackcompose.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belajar.submissionjetpackcompose.data.FoodRepository
import com.belajar.submissionjetpackcompose.model.Food
import com.belajar.submissionjetpackcompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel (private val repository: FoodRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Food>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Food>>>
        get() = _uiState

    fun getFav() = viewModelScope.launch {
        repository.getFav()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }
}