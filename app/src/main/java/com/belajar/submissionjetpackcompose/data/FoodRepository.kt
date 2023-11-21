package com.belajar.submissionjetpackcompose.data

import com.belajar.submissionjetpackcompose.model.Food
import com.belajar.submissionjetpackcompose.model.FoodData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FoodRepository {
    private val dummyFood = mutableListOf<Food>()

    init {
        if (dummyFood.isEmpty()) {
            FoodData.food.forEach {
                dummyFood.add(it)
            }
        }
    }

    fun getFood(): List<Food> {
        return FoodData.food
    }

    fun searchFood(query: String) = flow {
        val data = FoodData.food.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun getFoodById(foodId: String): Food {
        return dummyFood.first {
            it.id == foodId
        }
    }

    fun getFav(): Flow<List<Food>> {
        return flowOf(dummyFood.filter { it.isFavorite })
    }

    fun updateFav(foodId: String): Flow<Boolean> {
        val index = dummyFood.indexOfFirst { it.id == foodId }
        val result = if (index >= 0) {
            val food = dummyFood[index]
            dummyFood[index] = food.copy(isFavorite = !food.isFavorite)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: FoodRepository? = null

        fun getInstance(): FoodRepository =
            instance ?: synchronized(this) {
                FoodRepository().apply {
                    instance = this
                }
            }
    }
}