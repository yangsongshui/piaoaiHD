package myapplication.com.piaoaihd;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import myapplication.com.piaoaihd.base.BaseFragment;

/**
 * Created by ys on 2017/7/25.
 */

public class ChartFragment extends BaseFragment{
    CombinedChart mChart;
    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        mChart = (CombinedChart)layout.findViewById(R.id.week_chart);
        initChart();
    }

    @Override
    protected int getContentView() {
        return R.layout.ftagment_chart;
    }
    private void initChart() {

        /**
         * ====================1.初始化-自由配置===========================
         */
        // 是否在折线图上添加边框
        mChart.setDrawGridBackground(false);
        mChart.setVisibleXRangeMaximum(7);
        mChart.setDrawBorders(false);
        // 设置右下角描述
        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE,
        });

        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mChart.setTouchEnabled(true);
        //设置是否可以拖拽
        mChart.setDragEnabled(false);
        //设置是否可以缩放
        mChart.setScaleEnabled(false);
        mChart.setDoubleTapToZoomEnabled(false);
        //设置是否能扩大扩小
        mChart.setPinchZoom(false);
        //设置四个边的间距

        //隐藏Y轴
        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisLeft().setDrawLabels(false);
        mChart.setDoubleTapToZoomEnabled(false);
        //不画网格
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisLeft().setTextColor(R.color.silver_sand);

        XAxis xAxis = mChart.getXAxis();

        xAxis.setAxisMinimum(-0.2f);
        xAxis.setGranularity(0.3f);
        xAxis.setAxisMaximum(6);
        xAxis.setTextColor(R.color.spindle);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴在底部
        YAxis yAxis = mChart.getAxisRight();
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(500);
        yAxis.setDrawLabels(false);
        yAxis.setDrawGridLines(false);
        mChart.getAxisLeft().setAxisMinimum(0);
        mChart.getAxisLeft().setAxisMaximum(500);


        //不画网格
        xAxis.setDrawGridLines(false);
        mChart.getLegend().setEnabled(false);
        //  mChart.getAxisLeft().setValueFormatter();
        CombinedData data = new CombinedData();
        data.setData(getLineData());
        mChart.setData(data);
        mChart.invalidate();


    }

    private LineData getLineData() {
        ArrayList<Entry> values1 = new ArrayList<>();
        values1.add(new Entry(0, 35));
        values1.add(new Entry(1, 75));
        values1.add(new Entry(2, 115));
        values1.add(new Entry(3, 150));
        values1.add(new Entry(4, 250));
        values1.add(new Entry(5, 350));
        values1.add(new Entry(6, 500));
        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
        } else {
            set1 = new LineDataSet(values1, "");
            set1.setLineWidth(2f);//设置线宽
            set1.setCircleRadius(3f);//设置焦点圆心的大小
            set1.setHighlightLineWidth(0.5f);//设置点击交点后显示高亮线宽
            set1.setHighlightEnabled(true);//是否禁用点击高亮线
            set1.setDrawHorizontalHighlightIndicator(true);//设置不显示水平高亮线
            set1.setDrawCircles(false);  //设置有圆点
            set1.setDrawValues(false);  //不显示数据
            set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER); //设置为曲线
            set1.setHighLightColor(Color.rgb(51, 51, 51));//设置点击交点后显示交高亮线的颜色
            set1.setColor(Color.rgb(51, 153, 255));    //设置曲线的颜色

        }
        return new LineData(set1);
    }
}
