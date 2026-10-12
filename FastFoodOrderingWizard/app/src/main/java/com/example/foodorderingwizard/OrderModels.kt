package com.example.foodorderingwizard

data class Order(
    var mainMeal: String? = null,
    val sideDishes: MutableList<String> = mutableListOf(),
    var drink: String? = null
) {
    fun isComplete(): Boolean =
        mainMeal != null && sideDishes.isNotEmpty() && drink != null
}