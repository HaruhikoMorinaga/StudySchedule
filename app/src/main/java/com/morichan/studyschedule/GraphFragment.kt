import androidx.fragment.app.Fragment

//package com.morichan.studyschedule
//
//import android.graphics.Typeface
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//
//
class GraphFragment : Fragment() {}
//
//
//    private var mTypeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
//    // データの個数
//    private var chartDataCount = Gra
//
//
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        return inflater.inflate(R.layout.fragment_graph, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // グラフの設定
//        setupLineChart()
//        // データの設定
//        lineChart.data = lineData(chartDataCount, 100f)
//    }
//
//    // LineChart用のデータ作成
//    private fun lineData(count: Int, range: Float):LineData {
//
//        val values = mutableListOf<Entry>()
//
//        for (i in 0 until count) {
//            // 値はランダムで表示させる
//            val value = (Math.random() * (range)).toFloat()
//            values.add(Entry(i.toFloat(), value))
//        }
//
//        // グラフのレイアウトの設定
//        val yVals = LineDataSet(values, "テストデータ").apply {
//            axisDependency =  YAxis.AxisDependency.LEFT
//            color = Color.BLACK
//            // タップ時のハイライトカラー
//            highLightColor = Color.YELLOW
//            setDrawCircles(true)
//            setDrawCircleHole(true)
//            // 点の値非表示
//            setDrawValues(false)
//            // 線の太さ
//            lineWidth = 2f
//        }
//        val data = LineData(yVals)
//        return data
//    }
//
//    private fun setupLineChart(){
//        lineChart.apply {
//            description.isEnabled = false
//            setTouchEnabled(true)
//            isDragEnabled = true
//            // 拡大縮小可能
//            isScaleXEnabled = true
//            setPinchZoom(false)
//            setDrawGridBackground(false)
//
//            //データラベルの表示
//            legend.apply {
//                form = Legend.LegendForm.LINE
//                typeface = mTypeface
//                textSize = 11f
//                textColor = Color.BLACK
//                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
//                horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
//                orientation = Legend.LegendOrientation.HORIZONTAL
//                setDrawInside(false)
//            }
//
//            //y軸右側の設定
//            axisRight.isEnabled = false
//
//            //X軸表示
//            xAxis.apply {
//                typeface = mTypeface
//                setDrawLabels(false)
//                // 格子線を表示する
//                setDrawGridLines(true)
//            }
//
//            //y軸左側の表示
//            axisLeft.apply {
//                typeface = mTypeface
//                textColor = Color.BLACK
//                // 格子線を表示する
//                setDrawGridLines(true)
//            }
//        }
//    }
//}
//
//
//
