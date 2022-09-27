package com.biggemot.trialpointschart.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.biggemot.trialpointschart.R
import com.biggemot.trialpointschart.databinding.FragmentChartBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChartFragment : Fragment() {

    private lateinit var binding: FragmentChartBinding
    private val viewModel: ChartViewModel by viewModels()
    private val args: ChartFragmentArgs by navArgs()

    private val pointsAdapter = PointTableAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setArgs(args)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChartBinding.inflate(inflater)
        binding.run {
            retryButton.setOnClickListener { viewModel.retryClick() }
            saveChartButton.setOnClickListener { viewModel.saveChartClick(chartView.chartBitmap) }
            pointsTableRecyclerView.adapter = pointsAdapter
            setUpChart()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            binding.run {
                when (state) {
                    is ChartViewModel.UiState.Data -> {
                        pointsAdapter.submitList(state.tableData)
                        pointsTableGroup.visibility = View.VISIBLE
                        loadingProgressBar.visibility = View.GONE
                        errorTextView.visibility = View.GONE
                        retryButton.visibility = View.GONE

                        setChartData(state.chartData)
                        chartView.visibility = View.VISIBLE
                        saveChartButton.visibility = View.VISIBLE
                    }
                    ChartViewModel.UiState.Loading -> {
                        pointsTableGroup.visibility = View.GONE
                        loadingProgressBar.visibility = View.VISIBLE
                        errorTextView.visibility = View.GONE
                        retryButton.visibility = View.GONE
                        chartView.visibility = View.GONE
                        saveChartButton.visibility = View.GONE
                    }
                    ChartViewModel.UiState.NetworkError -> {
                        showErrorState(resources.getString(R.string.chart_data_loading_network_error))
                    }
                    ChartViewModel.UiState.ServerError -> {
                        showErrorState(resources.getString(R.string.chart_data_loading_server_error))
                    }
                    ChartViewModel.UiState.UnknownError -> {
                        showErrorState(resources.getString(R.string.chart_data_loading_generic_error))
                    }
                }
            }
        }
        viewModel.saveChartResult.observe(viewLifecycleOwner) {
            val text = if (it is ChartViewModel.SaveChartResult.Success) {
                resources.getString(R.string.fmt_save_chart_success, it.fileName)
            } else {
                resources.getString(R.string.save_chart_error)
            }
            Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
        }
    }

    private fun showErrorState(text: String) {
        binding.run {
            errorTextView.text = text
            errorTextView.visibility = View.VISIBLE
            retryButton.visibility = View.VISIBLE
            pointsTableGroup.visibility = View.GONE
            chartView.visibility = View.GONE
            saveChartButton.visibility = View.GONE
            loadingProgressBar.visibility = View.GONE
        }
    }

    private fun setUpChart() {
        with(binding.chartView) {
            description.isEnabled = false
            legend.isEnabled = false
            axisRight.isEnabled = false
        }
    }

    private fun setChartData(data: List<Entry>) {
        val dataSet = LineDataSet(data, "")
        dataSet.lineWidth = 1f
        dataSet.valueTextSize = 10f
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.cubicIntensity = 0.1f
        dataSet.color = ContextCompat.getColor(requireContext(), R.color.teal_700)
        dataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.black)

        binding.chartView.data = LineData(dataSet)
    }
}