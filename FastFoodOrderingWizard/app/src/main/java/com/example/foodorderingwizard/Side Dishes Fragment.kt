package com.example.foodorderingwizard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class SideDishesFragment : Fragment() {

    private lateinit var viewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        val root = inflater.inflate(R.layout.fragment_side_dishes, container, false)
        val selected = viewModel.order.value?.sideDishes.orEmpty()

        fun bindCheckBox(checkBox: CheckBox) {
            checkBox.isChecked = selected.contains(checkBox.text.toString())
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                viewModel.toggleSideDish(checkBox.text.toString(), isChecked)
            }
        }

        bindCheckBox(root.findViewById(R.id.checkFries))
        bindCheckBox(root.findViewById(R.id.checkSalad))
        bindCheckBox(root.findViewById(R.id.checkCornCup))
        return root
    }
}