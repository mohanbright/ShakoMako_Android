package com.io.app.shakomako.ui.analytics

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.FragmentAnalyticsBinding
import com.io.app.shakomako.ui.base.BaseFragment


class FragmentAnalytics : BaseFragment<FragmentAnalyticsBinding>() {
    override fun layoutRes(): Int = R.layout.fragment_analytics

    lateinit var graphMap: HashMap<String, Float>
    lateinit var colors: ArrayList<Int>


    fun instance(): FragmentAnalytics {
        return FragmentAnalytics()

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        colors = ArrayList()
        colors.add(ContextCompat.getColor(getThisActivity(), R.color.colorGraphLight))
        colors.add(ContextCompat.getColor(getThisActivity(), R.color.colorGraphDark))
        graphMap = HashMap()
        graphMap["Men"] = 24f
        graphMap["Women"] = 74f
        viewDataBinding.genderPieChart.apply {
            this.transparentCircleRadius = 0f
            this.setUsePercentValues(true)
            this.minimumWidth = 250
            this.minimumHeight = 250
            this.isDrawHoleEnabled = true
            this.holeRadius = 0f
            this.rotationAngle = 0f
            this.isRotationEnabled = true
            this.description.isEnabled = false
            this.setTouchEnabled(false)


            this.setDrawCenterText(true)
            this.setDrawEntryLabels(true)
            this.setDrawMarkers(false)
            this.description.isEnabled = false
            this.legend.isEnabled = false
        }

        addData(graphMap)

        val l: Legend = viewDataBinding.genderPieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f


    }

    private fun addData(dadosGrafico: Map<String, Float>) {
        var i = 0
        var valGrafico = ArrayList<PieEntry>()
        var legendaGrafico = ArrayList<String>()

        dadosGrafico.entries.forEach {
            Log.e("textValue", "${it.value} _name is_ ${it.key}")
            valGrafico.add(PieEntry(it.value, i++.toFloat()))
            legendaGrafico.add(it.key)
        }

        val dataSet = PieDataSet(valGrafico, null)

        dataSet.sliceSpace = 0f
        dataSet.selectionShift = 5f

        dataSet.colors = colors

        val data = PieData(dataSet)

        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        viewDataBinding.genderPieChart.apply {
            this.data = data
            this.highlightValue(null)
            this.invalidate()
        }
        setBarGraph()

    }

    private fun setBarGraph() {

        val data = BarData(getDataSet())
        data.barWidth = 1.5f
        data.setValueTextColor(Color.BLACK);
        viewDataBinding.ageRangeBarChart.setFitBars(true) // make the x-axis fit exactly all bars

        viewDataBinding.ageRangeBarChart.animateXY(2000, 2000)
        viewDataBinding.ageRangeBarChart.xAxis.setDrawGridLines(false)
        viewDataBinding.ageRangeBarChart.minimumWidth = 250
        viewDataBinding.ageRangeBarChart.minimumHeight = 370
        viewDataBinding.ageRangeBarChart.legend.textColor =
            ContextCompat.getColor(getThisActivity(), R.color.colorPrimary)
        viewDataBinding.ageRangeBarChart.description.isEnabled = false
        viewDataBinding.ageRangeBarChart.setPinchZoom(false)
        viewDataBinding.ageRangeBarChart.legend.isEnabled = false
        viewDataBinding.ageRangeBarChart.setTouchEnabled(false)


        viewDataBinding.ageRangeBarChart.invalidate()
        val xAxis = viewDataBinding.ageRangeBarChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        val formatter = IndexAxisValueFormatter(getXAxisValues())
        xAxis.valueFormatter = formatter


        viewDataBinding.ageRangeBarChart.apply {
            this.xAxis.isEnabled = false
            this.axisLeft.isEnabled = false
            this.axisRight.isEnabled = false
        }

        viewDataBinding.ageRangeBarChart.data = data

        setLocationBarGraph()


    }

    private fun getDataSet(): IBarDataSet {
        val entries: ArrayList<BarEntry> = ArrayList()


        entries.add(BarEntry(4f, 0f))
        entries.add(BarEntry(6f, 1f))
        entries.add(BarEntry(8f, 2f))
        entries.add(BarEntry(10f, 3f))
        entries.add(BarEntry(12f, 4f))

        val barDataSet = BarDataSet(entries, null)
        barDataSet.colors = colors

        return barDataSet


    }

    private fun getXAxisValues(): ArrayList<String> {
        val labels: ArrayList<String> = ArrayList()
        labels.add("January")
        labels.add("February")
        labels.add("March")
        labels.add("April")
        labels.add("May")
        labels.add("June")
        return labels
    }


    private fun setLocationBarGraph() {

        val data = BarData(getDataSet())
        data.barWidth = 1.5f
        data.setValueTextColor(Color.BLACK);
        viewDataBinding.locationRangeBarChart.setFitBars(true) // make the x-axis fit exactly all bars

        viewDataBinding.locationRangeBarChart.animateXY(2000, 2000)
        viewDataBinding.locationRangeBarChart.xAxis.setDrawGridLines(false)
        viewDataBinding.locationRangeBarChart.minimumWidth = 250
        viewDataBinding.locationRangeBarChart.minimumHeight = 370
        viewDataBinding.locationRangeBarChart.legend.textColor =
            ContextCompat.getColor(getThisActivity(), R.color.colorPrimary)
        viewDataBinding.locationRangeBarChart.description.isEnabled = false
        viewDataBinding.locationRangeBarChart.setPinchZoom(false)
        viewDataBinding.locationRangeBarChart.legend.isEnabled = false
        viewDataBinding.locationRangeBarChart.setTouchEnabled(false)


        viewDataBinding.locationRangeBarChart.invalidate()
        val xAxis = viewDataBinding.locationRangeBarChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        val formatter = IndexAxisValueFormatter(getXAxisValues())
        xAxis.valueFormatter = formatter


        viewDataBinding.locationRangeBarChart.apply {
            this.xAxis.isEnabled = false
            this.axisLeft.isEnabled = false
            this.axisRight.isEnabled = false
        }

        viewDataBinding.locationRangeBarChart.data = data


    }


}