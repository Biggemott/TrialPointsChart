package com.biggemot.trialpointschart.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.biggemot.trialpointschart.R
import com.biggemot.trialpointschart.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        binding.pointCountEditText.addTextChangedListener {
            viewModel.pointCountTextChanged(it?.toString())
        }
        binding.startButton.setOnClickListener {
            viewModel.startButtonClick(binding.pointCountEditText.text?.toString())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            binding.run {
                when (state) {
                    MainViewModel.UiState.Empty -> {
                        pointCountInputLayout.error = null
                        startButton.isEnabled = false
                    }
                    MainViewModel.UiState.Default -> {
                        pointCountInputLayout.error = null
                        startButton.isEnabled = true
                    }
                    MainViewModel.UiState.Error -> {
                        pointCountInputLayout.error =
                            resources.getText(R.string.main_point_count_input_error)
                        startButton.isEnabled = false
                    }
                }
            }
        }

        viewModel.navEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(it)
        }
    }

}