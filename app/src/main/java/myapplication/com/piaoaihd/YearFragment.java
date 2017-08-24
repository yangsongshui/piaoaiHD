package myapplication.com.piaoaihd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myapplication.com.piaoaihd.base.BaseFragment;
import myapplication.com.piaoaihd.bean.PMBean;
import myapplication.com.piaoaihd.presenter.PMdataPresenterImp;
import myapplication.com.piaoaihd.util.DateUtil;
import myapplication.com.piaoaihd.util.FragmentEvent;
import myapplication.com.piaoaihd.util.Toastor;
import myapplication.com.piaoaihd.view.PMView;

import static myapplication.com.piaoaihd.util.Constan.ACTION_BLE_NOTIFY_DATA;
import static myapplication.com.piaoaihd.util.DateUtil.LONG_DATE_FORMAT;

/**
 * Created by ys on 2017/7/25.
 */

public class YearFragment extends BaseFragment implements PMView {
    CombinedChart mChart;
    PMdataPresenterImp pMdataPresenterImp;
    private Toastor toastor;
    private Map<String, String> map;
    List<String> mList;
    List<String> month;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        pMdataPresenterImp = new PMdataPresenterImp(this, getActivity());
        //注册EventBus
        EventBus.getDefault().register(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BLE_NOTIFY_DATA);
        getActivity().registerReceiver(notifyReceiver, intentFilter);
        mList = new ArrayList<>();
        month = new ArrayList<>();
        toastor = new Toastor(getActivity());
        mChart = (CombinedChart) layout.findViewById(R.id.week_chart);
        ((TextView) layout.findViewById(R.id.chart_msg)).setText("PM2.5月曲线图");
        map = new HashMap<>();

        initMonth();
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


        XAxis xAxis = mChart.getXAxis();

        // xAxis.setAxisMinimum(-0.1f);
        xAxis.setGranularity(0.3f);
        xAxis.setAxisMaximum(30);
        xAxis.setLabelCount(10, true);
        xAxis.setTextColor(Color.rgb(255, 255, 255));
        xAxis.setAxisLineColor(Color.rgb(255, 255, 255));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴在底部
        YAxis yAxis = mChart.getAxisRight();
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(500);
        yAxis.setAxisLineColor(Color.rgb(255, 255, 255));
        LimitLine yLimitLine = new LimitLine(35);
        yLimitLine.setLineWidth(0.3f);
        yLimitLine.setLineColor(Color.rgb(107, 237, 29));
        yAxis.addLimitLine(yLimitLine);

        LimitLine yLimitLine2 = new LimitLine(75);
        yLimitLine2.setLineWidth(0.3f);
        yLimitLine2.setLineColor(Color.rgb(255, 245, 69));
        yAxis.addLimitLine(yLimitLine2);

        LimitLine yLimitLine3 = new LimitLine(115);
        yLimitLine3.setLineWidth(0.3f);
        yLimitLine3.setLineColor(Color.rgb(245, 98, 35));
        yAxis.addLimitLine(yLimitLine3);

        LimitLine yLimitLine4 = new LimitLine(150);
        yLimitLine4.setLineWidth(0.3f);
        yLimitLine4.setLineColor(Color.rgb(245, 40, 9));
        yAxis.addLimitLine(yLimitLine4);

        LimitLine yLimitLine5 = new LimitLine(250);
        yLimitLine5.setLineWidth(0.3f);
        yLimitLine5.setLineColor(Color.rgb(173, 45, 21));
        yAxis.addLimitLine(yLimitLine5);
        LimitLine yLimitLine6 = new LimitLine(350);
        yLimitLine6.setLineWidth(0.3f);
        yLimitLine6.setLineColor(Color.rgb(134, 22, 0));
        yAxis.addLimitLine(yLimitLine6);
        yAxis.setDrawLabels(false);
        yAxis.setDrawGridLines(false);
        mChart.getAxisLeft().setAxisMinimum(0);
        mChart.getAxisLeft().setAxisMaximum(500);
        //不画网格
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                return month.get((int) value % month.size());
            }

        });
        mChart.getLegend().setEnabled(false);
        CombinedData data = new CombinedData();
        data.setData(getLineData());
        mChart.setData(data);
        mChart.invalidate();


    }

    private LineData getLineData() {
        ArrayList<Entry> values1 = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            if (i >= mList.size() ) {
               // Log.e(TAG, i + "");
                values1.add(new Entry(i, 0));
            } else {

                values1.add(new Entry(i, Integer.parseInt(mList.get(i))));
            }

        }

        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
        } else {
            set1 = new LineDataSet(values1, "");
            set1.setLineWidth(1f);//设置线宽
            set1.setCircleRadius(3f);//设置焦点圆心的大小
            set1.setHighlightLineWidth(0.5f);//设置点击交点后显示高亮线宽
            set1.setHighlightEnabled(false);//是否禁用点击高亮线
            set1.setDrawHorizontalHighlightIndicator(false);//设置不显示水平高亮线
            set1.setDrawCircles(false);  //设置有圆点
            set1.setDrawValues(false);  //不显示数据
            set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER); //设置为曲线
            // set1.setHighLightColor(Color.rgb(51, 51, 51));//设置点击交点后显示交高亮线的颜色
            set1.setColor(Color.rgb(255, 255, 255));    //设置曲线的颜色

        }
        return new LineData(set1);
    }

    private void initMonth() {
        Date data = new Date();
        //通过格式化输出日期
        String time = DateUtil.getCurrDate(LONG_DATE_FORMAT);
        String time2 = DateUtil.dateToString(DateUtil.nextDay(data, -29), LONG_DATE_FORMAT);
        map.put("type", "3");
        map.put("endDate", time);
        map.put("beginDate", time2 );
        String string = "";
        SimpleDateFormat format2 = new SimpleDateFormat("dd");
        month.add(0, DateUtil.getCurrDate("dd") + "号");
        for (int i = 0; i < 30; i++) {
            string = format2.format(DateUtil.nextDay(data, -i)) + "号";

            month.add(0, string);
        }
    }

    private BroadcastReceiver notifyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //设备
            if (ACTION_BLE_NOTIFY_DATA.equals(intent.getAction())) {
                if (MyApplication.newInstance().getListBean() != null) {
                    map.put("imei", MyApplication.newInstance().getListBean().getDeviceid());
                    pMdataPresenterImp.binding(map);
                }
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FragmentEvent event) {
        if (MyApplication.newInstance().getListBean() != null) {
            map.put("imei", MyApplication.newInstance().getListBean().getDeviceid());
            pMdataPresenterImp.binding(map);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//反注册EventBus
        getActivity().unregisterReceiver(notifyReceiver);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void disimissProgress() {

    }

    @Override
    public void loadDataSuccess(PMBean tData) {
        if (tData.getResCode().equals("0")) {
            if (tData.getResBody().getList().size() > 0) {
                mList = tData.getResBody().getList();

            }
            CombinedData data = new CombinedData();
            data.setData(getLineData());
            mChart.setData(data);
            mChart.invalidate();
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }
}
