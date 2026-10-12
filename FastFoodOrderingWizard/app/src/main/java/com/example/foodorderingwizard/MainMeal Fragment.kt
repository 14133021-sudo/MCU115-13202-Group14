package com.example.foodorderingwizard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class MainMealFragment : Fragment() {

    private lateinit var viewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        val root = inflater.inflate(R.layout.fragment_main_meal, container, false)
        val group = root.findViewById<RadioGroup>(R.id.mainMealGroup)
        group.setOnCheckedChangeListener { _, checkedId ->
            val rb = group.findViewById<RadioButton>(checkedId)
            if (rb != null) {
                viewModel.selectMainMeal(rb.text.toString())
            }
        }

        val saved = viewModel.order.value?.mainMeal
        for (i in 0 until group.childCount) {
            val rb = group.getChildAt(i)
            if (rb is RadioButton && rb.text.toString() == saved) {
                group.check(rb.id)
            }
        }
        return root
    }
}