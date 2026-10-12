package com.example.foodorderingwizard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {

    private val _order = MutableLiveData(Order())
    val order: LiveData<Order> = _order

    fun selectMainMeal(meal: String) {
        val current = _order.value ?: return
        current.mainMeal = meal
        _order.value = _order.value
    }

    fun toggleSideDish(dish: String, checked: Boolean) {
        val current = _order.value ?: return
        if (checked) {
            if (!current.sideDishes.contains(dish)) {
                current.sideDishes.add(dish)
            }
        } else {
            current.sideDishes.remove(dish)
        }
        _order.value = _order.value
    }

    fun selectDrink(drink: String) {
        val current = _order.value ?: return
        current.drink = drink
        _order.value = _order.value
    }
}