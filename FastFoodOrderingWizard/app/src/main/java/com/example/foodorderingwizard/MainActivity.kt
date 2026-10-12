package com.example.foodorderingwizard

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: OrderViewModel
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        viewPager.adapter = WizardPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.offscreenPageLimit = 3

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_main_meal)
                1 -> getString(R.string.tab_side_dishes)
                2 -> getString(R.string.tab_drink)
                else -> getString(R.string.tab_confirm)
            }
        }.attach()

        findViewById<Button>(R.id.btnConfirm).setOnClickListener { confirmOrder() }
    }

    fun confirmOrder() {
        // Confirm page: validate
        val order = viewModel.order.value
        if (order?.isComplete() != true) {
            Toast.makeText(
                this,
                "Please select a main meal, at least one side dish, and a drink.",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        // Show AlertDialog with Submit
        AlertDialog.Builder(this)
            .setTitle("Submit Order")
            .setMessage(
                "Main: ${order.mainMeal}\n" +
                "Sides: ${order.sideDishes.joinToString(", ")}\n" +
                "Drink: ${order.drink}\n\nSubmit this order?"
            )
            .setPositiveButton("Submit") { d, _ ->
                d.dismiss()
                Toast.makeText(this, "Order submitted!              ", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}