package com.example.foodorderingwizard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class ConfirmFragment : Fragment() {

    private lateinit var viewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        val root = inflater.inflate(R.layout.fragment_confirm, container, false)
        val summary = root.findViewById<TextView>(R.id.summaryText)

        viewModel.order.observe(viewLifecycleOwner) { order ->
            summary.text = when {
                order.mainMeal == null && order.sideDishes.isEmpty() && order.drink == null ->
                    getString(R.string.order_summary_placeholder)
                else ->
                    "Main: ${order.mainMeal ?: "-"}\n" +
                    "Sides: ${order.sideDishes.joinToString(", ")}\n" +
                    "Drink: ${order.drink ?: "-"}"
            }
        }

        root.findViewById<Button>(R.id.btnDone).setOnClickListener {
            (activity as? MainActivity)?.confirmOrder()
        }
        return root
    }
}