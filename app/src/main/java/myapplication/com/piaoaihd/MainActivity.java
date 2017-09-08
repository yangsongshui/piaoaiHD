/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package myapplication.com.piaoaihd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import myapplication.com.piaoaihd.base.BaseActivity;
import myapplication.com.piaoaihd.bean.Facility;
import myapplication.com.piaoaihd.presenter.FacilityPresenerImp;
import myapplication.com.piaoaihd.util.Constan;
import myapplication.com.piaoaihd.util.FragmentEvent;
import myapplication.com.piaoaihd.util.SpUtils;
import myapplication.com.piaoaihd.util.Toastor;
import myapplication.com.piaoaihd.view.FacilityView;

import static myapplication.com.piaoaihd.util.Constan.ACTION_BLE_NOTIFY_DATA;


public class MainActivity extends BaseActivity implements FacilityView {
    private final static String TAG = MainActivity.class.getSimpleName();
    private int REQUEST_CODE = 0x01;
    private int RESULT_OK = 0xA1;
    private List<Fragment> frags;
    private TextView main_place, main_pm_tv, main_pm, main_temperature, main_humidity;
    private TextView co2, co2_tv, pm10, pm10_tv, jiaquan, jiaquan_tv, tvoc, tvoc_tv;
    private ImageView iv, iv2, iv3, iv4;
    private LinearLayout pm_ll;
    private ViewPager pager;
    private Handler handler;
    private Runnable myRunnable;
    private Runnable deviceRunnable;
    private Runnable dataRunnable;
    private Toastor toastor;
    private TextView textClock;
    private FacilityPresenerImp facilityPresenerImp;
    private ProgressDialog progressDialog = null;
    private boolean isOne = true;
    List<Facility.ResBodyBean.ListBean> mList;
    Facility.ResBodyBean.ListBean listBean;
    private int indext = 0;
    private int deviceTime = 0;
    private int dataTime = 0;
    TestFragmentAdapter mAdapter;
    private int mark = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        initView();
        mList = new ArrayList<>();
        handler = new Handler();
        facilityPresenerImp = new FacilityPresenerImp(this, this);
        toastor = new Toastor(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据更新中,请稍后");
        frags = new ArrayList<>();
        frags.add(new TimeFragment());
        frags.add(new ChartFragment());
        frags.add(new WeekFragment());
        frags.add(new YearFragment());
        mAdapter = new TestFragmentAdapter(getSupportFragmentManager(), frags);
        pager.setAdapter(mAdapter);
        initRunnable();
    }

    private void initView() {

        pager = (ViewPager) findViewById(R.id.pager);
        main_place = (TextView) findViewById(R.id.main_place);
        main_pm_tv = (TextView) findViewById(R.id.main_pm_tv);
        main_pm = (TextView) findViewById(R.id.main_pm);
        main_temperature = (TextView) findViewById(R.id.main_temperature);
        main_humidity = (TextView) findViewById(R.id.main_humidity);
        textClock = (TextView) findViewById(R.id.main_time);
        pm_ll = (LinearLayout) findViewById(R.id.pm_ll);
        iv = (ImageView) findViewById(R.id.iv);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);

        co2 = (TextView) findViewById(R.id.co2);
        co2_tv = (TextView) findViewById(R.id.co2_tv);
        pm10 = (TextView) findViewById(R.id.pm10);
        pm10_tv = (TextView) findViewById(R.id.pm10_tv);
        jiaquan = (TextView) findViewById(R.id.jiaquan);
        jiaquan_tv = (TextView) findViewById(R.id.jiaquan_tv);
        tvoc = (TextView) findViewById(R.id.tvoc);
        tvoc_tv = (TextView) findViewById(R.id.tvoc_tv);

        findViewById(R.id.main_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });
    }

    @Override
    public void showProgress() {
        if (progressDialog != null && !progressDialog.isShowing() && isOne) {
            progressDialog.show();
            isOne = false;
        }
    }

    @Override
    public void disimissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void loadDataSuccess(Facility tData) {
        if (tData.getResCode().equals("0")) {
            mList.clear();
            for (int i = 0; i < tData.getResBody().getList().size(); i++) {
                if (tData.getResBody().getList().get(i).getType().equals("1") || tData.getResBody().getList().get(i).getType().equals("2") || tData.getResBody().getList().get(i).getType().equals("4")) {
                    mList.add(tData.getResBody().getList().get(i));
                }
            }
            if (mList.size() > 0) {
                if (listBean == null) {
                    listBean = mList.get(0);
                    initdata();
                } else {
                    if (mList.size() > mark)
                        listBean = mList.get(mark);
                    else {
                        mark = 0;
                        listBean = mList.get(0);
                    }
                    initdata();
                }
                MyApplication.newInstance().setListBean(listBean);
                EventBus.getDefault().post(new FragmentEvent(mList));
            } else {
                listBean = null;
                initdata();
            }
        } else {
            toastor.showSingletonToast(tData.getResMessage());
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        //Log.e(TAG, throwable.toString());

        toastor.showSingletonToast("网络连接异常");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                //toastor.showSingletonToast("你按下中间键");

                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(myRunnable);
        handler.removeCallbacks(dataRunnable);
        handler.removeCallbacks(deviceRunnable);
        handler = null;
        facilityPresenerImp = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        facilityPresenerImp.findUserDevice(MyApplication.newInstance().getUser().getResBody().getPhoneNumber());
        handler.postDelayed(myRunnable, 60000);

        if (deviceTime != SpUtils.getInt("device", 15)) {
            if (deviceTime > 0)
                handler.removeCallbacks(deviceRunnable);
            deviceTime = SpUtils.getInt("device", 15);
           handler.postDelayed(deviceRunnable, (deviceTime * 60 * 1000));
            //handler.postDelayed(deviceRunnable, 3000);
        }
        if (dataTime != SpUtils.getInt("data", 40)) {
            if (dataTime > 0)
                handler.removeCallbacks(dataRunnable);
            dataTime = SpUtils.getInt("data", 40);
            handler.postDelayed(dataRunnable, (dataTime * 1000));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(myRunnable);
    }

    private void initdata() {
        if (listBean != null) {
            main_place.setText(listBean.getDeviceName().trim().equals("") ? "——" : listBean.getDeviceName());
            if (listBean.get_$Pm25267() == null || listBean.get_$Pm25267().trim().equals("")) {
                main_pm.setText("——");
                main_pm_tv.setText("——");
            } else {
                Constan.PM2_5(main_pm, Double.parseDouble(listBean.get_$Pm25267()), pm_ll);
                main_pm_tv.setText(listBean.get_$Pm25267().trim().equals("0") ? "——" : listBean.get_$Pm25267());
            }

            if (listBean.getShidu() != null && !listBean.getShidu().trim().equals(""))
                main_humidity.setText(listBean.getShidu().trim().equals("0") ? "——" : listBean.getShidu()+"%");
            else
                main_humidity.setText("——");
            if (listBean.getWendu() != null && !listBean.getWendu().trim().equals(""))
                main_temperature.setText(listBean.getWendu().trim().equals("0") ? "——" : listBean.getWendu()+"℃");
            else
                main_temperature.setText("——");
            if (listBean.getCo2() != null && !listBean.getCo2().equals("")) {
                Constan.CO2(co2_tv, Double.parseDouble(listBean.getCo2()), iv);
                co2.setText(listBean.getCo2().trim().equals("0") ? "——" : listBean.getCo2());
            } else {
                co2.setText("——");
                co2_tv.setText("——");
            }
            if (listBean.getPm10() != null && !listBean.getPm10().equals("")) {
                pm10.setText(listBean.getPm10().trim().equals("0") ? "——" : listBean.getPm10());
                Constan.PM10(pm10_tv, Double.parseDouble(listBean.getPm10()), iv2);
            } else {
                pm10_tv.setText("——");
                pm10.setText("——");
            }
            if (listBean.getJiaquan() != null && !listBean.getJiaquan().equals("")) {
                jiaquan.setText(listBean.getJiaquan().trim().equals("0") ? "——" : listBean.getJiaquan());
                Constan.jiaquan(jiaquan_tv, Double.parseDouble(listBean.getJiaquan()), iv3);
            } else {
                jiaquan_tv.setText("——");
                jiaquan.setText("——");
            }

            if (listBean.getTvoc() != null && !listBean.getTvoc().equals("")) {
                tvoc.setText(listBean.getTvoc().trim().equals("0") ? "——" : listBean.getTvoc());
                Constan.TVOC(tvoc_tv, Double.parseDouble(listBean.getTvoc()), iv4);
            } else {
                tvoc_tv.setText("——");
                tvoc.setText("——");
            }
        } else {
            main_place.setText("——");
            main_pm_tv.setText("——");
            main_pm.setText("——");
            main_humidity.setText("——");
            co2.setText("——");
            co2_tv.setText("——");
            pm10.setText("——");
            pm10_tv.setText("——");
            jiaquan.setText("——");
            jiaquan_tv.setText("——");
            tvoc.setText("——");
            tvoc_tv.setText("——");
        }
    }

    private void initRunnable() {
        myRunnable = new Runnable() {
            @Override
            public void run() {
                facilityPresenerImp.findUserDevice(MyApplication.newInstance().getUser().getResBody().getPhoneNumber());
                handler.postDelayed(myRunnable, 60000);
            }
        };
        dataRunnable = new Runnable() {
            @Override
            public void run() {
                if (indext == 4) {
                    indext = 0;
                    pager.setCurrentItem(indext);
                } else {
                    indext++;
                    pager.setCurrentItem(indext);
                }

                //切换页面历史数据
                handler.postDelayed(this, (dataTime * 1000));
                //Log.e("MainAcitvity", "切换历史数据");
            }
        };
        deviceRunnable = new Runnable() {
            @Override
            public void run() {
                handler.removeCallbacks(dataRunnable);
                //切换设备
                if (mList.size() > mark) {
                    listBean = mList.get(mark);
                    mark++;
                    //  Log.e("MainAcitvity", "切换设备:" + mark + "" + listBean.getDeviceName());
                } else if (mList.size() <= mark && mList.size() != 0) {
                    mark = 1;
                    listBean = mList.get(0);

                    // Log.e("MainAcitvity", "切换设备" + listBean.getDeviceName());
                } else {
                    listBean = null;

                }

                initdata();
                MyApplication.newInstance().setListBean(listBean);
                //Log.e("MainAcitvity", "切换设备" + listBean.getDeviceid()+listBean.getDeviceid());
                Intent intent = new Intent();
                intent.setAction(ACTION_BLE_NOTIFY_DATA);
                sendBroadcast(intent);
                handler.postDelayed(this, (deviceTime * 60 * 1000));
                handler.postDelayed(dataRunnable, (dataTime * 1000));
               // handler.postDelayed(this, 3000);

            }
        };
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            MyApplication.newInstance().outLogin();
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
    }

}
