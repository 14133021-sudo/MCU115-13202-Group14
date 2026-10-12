package com.example.foodorderingwizard

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OrderTest {

    @Test
    fun contextIsAvailable() {
        assertNotNull(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @Test
    fun incompleteOrderFailsValidation() {
        val order = Order()
        assertFalse(order.isComplete())
    }

    @Test
    fun missingSideDishesFailsValidation() {
        val order = Order()
        order.mainMeal = "Burger"
        order.drink = "Cola"
        assertFalse(order.isComplete())
    }

    @Test
    fun completeOrderPassesValidation() {
        val order = Order()
        order.mainMeal = "Burger"
        order.sideDishes.add("Fries")
        order.sideDishes.add("Salad")
        order.drink = "Iced Tea"
        assertTrue(order.isComplete())
    }
}