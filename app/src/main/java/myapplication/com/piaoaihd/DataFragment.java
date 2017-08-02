package myapplication.com.piaoaihd;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import myapplication.com.piaoaihd.base.BaseFragment;
import myapplication.com.piaoaihd.bean.Facility;
import myapplication.com.piaoaihd.util.FragmentEvent;


public class DataFragment extends BaseFragment {
    TextView device1_name, device1_co2, device1_pm25, device1_pm10, device1_jiaquan, device1_tvoc, device1_wendu, device1_shidu;
    TextView device2_name, device2_co2, device2_pm25, device2_pm10, device2_jiaquan, device2_tvoc, device2_wendu, device2_shidu;
    TextView device3_name, device3_co2, device3_pm25, device3_pm10, device3_jiaquan, device3_tvoc, device3_wendu, device3_shidu;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        //注册EventBus
        EventBus.getDefault().register(this);
        initView(layout);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_data;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        EventBus.getDefault().unregister(this);//反注册EventBus

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FragmentEvent event) {
        setView(event.getMsg());
    }

    private void setView(List<Facility.ResBodyBean.ListBean> mList) {
        Facility.ResBodyBean.ListBean data1 = mList.get(0);
        if (data1 != null && (data1.getType().equals("1") || data1.getType().equals("2"))) {
            device1_name.setText(data1.getDeviceName().trim().equals("") ? "——" : data1.getDeviceName());
            device1_co2.setText(data1.getCo2().trim().equals("") ? "——" : data1.getCo2());
            device1_pm25.setText(data1.get_$Pm25267().trim().equals("") ? "——" : data1.get_$Pm25267());
            device1_pm10.setText(data1.getPm10().trim().equals("") ? "——" : data1.getPm10());
            device1_jiaquan.setText(data1.getJiaquan().trim().equals("") ? "——" : data1.getJiaquan());
            device1_tvoc.setText(data1.getTvoc().trim().equals("") ? "——" : data1.getTvoc());
            device1_shidu.setText(data1.getShidu().trim().equals("") ? "——" : data1.getShidu());
            device1_wendu.setText("——");
        }
        Facility.ResBodyBean.ListBean data2 = mList.get(1);
        if (data2 != null && (data2.getType().equals("1") || data2.getType().equals("2"))) {
            device2_name.setText(data2.getDeviceName().trim().equals("") ? "——" : data2.getDeviceName());
            device2_co2.setText(data2.getCo2().trim().equals("") ? "——" : data2.getCo2());
            device2_pm25.setText(data2.get_$Pm25267().trim().equals("") ? "——" : data2.get_$Pm25267());
            device2_pm10.setText(data2.getPm10().trim().equals("") ? "——" : data2.getPm10());
            device2_jiaquan.setText(data2.getJiaquan().trim().equals("") ? "——" : data2.getJiaquan());
            device2_tvoc.setText(data2.getTvoc().trim().equals("") ? "——" : data2.getTvoc());
            device2_shidu.setText(data2.getShidu().trim().equals("") ? "——" : data2.getShidu());
            device2_wendu.setText("——");
        }
        Facility.ResBodyBean.ListBean data3 = mList.get(2);
        if (data3 != null && (data3.getType().equals("1") || data3.getType().equals("2"))){
            device3_name.setText(data3.getDeviceName().trim().equals("") ? "——" : data3.getDeviceName());
            device3_co2.setText(data3.getCo2().trim().equals("") ? "——" : data3.getCo2());
            device3_pm25.setText(data3.get_$Pm25267().trim().equals("") ? "——" : data3.get_$Pm25267());
            device3_pm10.setText(data3.getPm10().trim().equals("") ? "——" : data3.getPm10());
            device3_jiaquan.setText(data3.getJiaquan().trim().equals("") ? "——" : data3.getJiaquan());
            device3_tvoc.setText(data3.getTvoc().trim().equals("") ? "——" : data3.getTvoc());
            device3_shidu.setText(data3.getShidu().trim().equals("") ? "——" : data3.getShidu());
            device3_wendu.setText("——");
        }
    }

    private void initView(View layout) {
        device1_name = (TextView) layout.findViewById(R.id.device1_name);
        device1_co2 = (TextView) layout.findViewById(R.id.device1_co2);
        device1_pm25 = (TextView) layout.findViewById(R.id.device1_pm25);
        device1_pm10 = (TextView) layout.findViewById(R.id.device1_pm10);
        device1_jiaquan = (TextView) layout.findViewById(R.id.device1_jiaquan);
        device1_tvoc = (TextView) layout.findViewById(R.id.device1_tvoc);
        device1_wendu = (TextView) layout.findViewById(R.id.device1_wendu);
        device1_shidu = (TextView) layout.findViewById(R.id.device1_shidu);

        device2_name = (TextView) layout.findViewById(R.id.device2_name);
        device2_co2 = (TextView) layout.findViewById(R.id.device2_co2);
        device2_pm25 = (TextView) layout.findViewById(R.id.device2_pm25);
        device2_pm10 = (TextView) layout.findViewById(R.id.device2_pm10);
        device2_jiaquan = (TextView) layout.findViewById(R.id.device2_jiaquan);
        device2_tvoc = (TextView) layout.findViewById(R.id.device2_tvoc);
        device2_wendu = (TextView) layout.findViewById(R.id.device2_wendu);
        device2_shidu = (TextView) layout.findViewById(R.id.device2_shidu);

        device3_name = (TextView) layout.findViewById(R.id.device3_name);
        device3_co2 = (TextView) layout.findViewById(R.id.device3_co2);
        device3_pm25 = (TextView) layout.findViewById(R.id.device3_pm25);
        device3_pm10 = (TextView) layout.findViewById(R.id.device3_pm10);
        device3_jiaquan = (TextView) layout.findViewById(R.id.device3_jiaquan);
        device3_tvoc = (TextView) layout.findViewById(R.id.device3_tvoc);
        device3_wendu = (TextView) layout.findViewById(R.id.device3_wendu);
        device3_shidu = (TextView) layout.findViewById(R.id.device3_shidu);
    }
}
