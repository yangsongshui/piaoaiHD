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
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import myapplication.com.piaoaihd.base.BaseActivity;
import myapplication.com.piaoaihd.bean.Facility;
import myapplication.com.piaoaihd.presenter.FacilityPresenerImp;
import myapplication.com.piaoaihd.util.Constan;
import myapplication.com.piaoaihd.util.FragmentEvent;
import myapplication.com.piaoaihd.util.Log;
import myapplication.com.piaoaihd.util.SpUtils;
import myapplication.com.piaoaihd.util.Toastor;
import myapplication.com.piaoaihd.view.FacilityView;

import static myapplication.com.piaoaihd.util.Constan.ACTION_BLE_NOTIFY_DATA;


public class MainActivity extends BaseActivity implements FacilityView {
    private final static String TAG = MainActivity.class.getSimpleName();
    private int REQUEST_CODE = 0x01;
    private int RESULT_OK = 0xA1;
    private List<Fragment> frags;
    private TextView main_place, main_pm_tv, main_pm, main_temperature, main_temperature2, main_humidity, main_humidity2;
    private TextView co2_1, co2_tv1, pm10_1, pm10_tv1, jiaquan_1, jiaquan_tv1, tvoc_1, tvoc_tv1;
    private TextView co2_2, co2_tv2, pm10_2, pm10_tv2, jiaquan_2, jiaquan_tv2, tvoc_2, tvoc_tv2;
    private TextView co2, co2_tv, pm10, pm10_tv, jiaquan, jiaquan_tv, tvoc, tvoc_tv, main_title;

    private ImageView iv, iv2, iv3, iv4;
    private LinearLayout pm_ll, quxiantu_ll, data_ll, humidity_ll, temperature_ll;
    private LinearLayout co2_ll, pm10_ll, jiaquan_ll, tvoc_ll;
    private LinearLayout co2_ll2, pm10_ll2, jiaquan_ll2, tvoc_ll2, main_ll;
    private RelativeLayout co2_rl, pm10_rl, jiaquan_rl, tvoc_rl;
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
        main_title = (TextView) findViewById(R.id.main_title);
        main_pm = (TextView) findViewById(R.id.main_pm);
        main_temperature = (TextView) findViewById(R.id.main_temperature);
        main_temperature2 = (TextView) findViewById(R.id.main_temperature2);
        main_humidity = (TextView) findViewById(R.id.main_humidity);
        main_humidity2 = (TextView) findViewById(R.id.main_humidity2);
        textClock = (TextView) findViewById(R.id.main_time);
        pm_ll = (LinearLayout) findViewById(R.id.pm_ll);
        quxiantu_ll = (LinearLayout) findViewById(R.id.quxiantu_ll);
        data_ll = (LinearLayout) findViewById(R.id.data_ll);
        main_ll = (LinearLayout) findViewById(R.id.main_ll);
        main_ll = (LinearLayout) findViewById(R.id.main_ll);
        temperature_ll = (LinearLayout) findViewById(R.id.temperature_ll);
        humidity_ll = (LinearLayout) findViewById(R.id.humidity_ll);

        co2_ll = (LinearLayout) findViewById(R.id.co2_ll);
        pm10_ll = (LinearLayout) findViewById(R.id.pm10_ll);
        jiaquan_ll = (LinearLayout) findViewById(R.id.jiaquan_ll);
        tvoc_ll = (LinearLayout) findViewById(R.id.tvoc_ll);

        co2_ll2 = (LinearLayout) findViewById(R.id.co2_ll2);
        pm10_ll2 = (LinearLayout) findViewById(R.id.pm10_ll2);
        jiaquan_ll2 = (LinearLayout) findViewById(R.id.jiaquan_ll2);
        tvoc_ll2 = (LinearLayout) findViewById(R.id.tvoc_ll2);

        co2_rl = (RelativeLayout) findViewById(R.id.co2_rl);
        pm10_rl = (RelativeLayout) findViewById(R.id.pm10_rl);
        jiaquan_rl = (RelativeLayout) findViewById(R.id.jiaquan_rl);
        tvoc_rl = (RelativeLayout) findViewById(R.id.tvoc_rl);


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

        co2_1 = (TextView) findViewById(R.id.co2_1);
        co2_tv1 = (TextView) findViewById(R.id.co2_tv2);
        pm10_1 = (TextView) findViewById(R.id.pm10_1);
        pm10_tv1 = (TextView) findViewById(R.id.pm10_tv1);
        jiaquan_1 = (TextView) findViewById(R.id.jiaquan_1);
        jiaquan_tv1 = (TextView) findViewById(R.id.jiaquan_tv1);
        tvoc_1 = (TextView) findViewById(R.id.tvoc_1);
        tvoc_tv1 = (TextView) findViewById(R.id.tvoc_tv1);

        co2_2 = (TextView) findViewById(R.id.co2_2);
        co2_tv2 = (TextView) findViewById(R.id.co2_tv2);
        pm10_2 = (TextView) findViewById(R.id.pm10_2);
        pm10_tv2 = (TextView) findViewById(R.id.pm10_tv2);
        jiaquan_2 = (TextView) findViewById(R.id.jiaquan_2);
        jiaquan_tv2 = (TextView) findViewById(R.id.jiaquan_tv2);
        tvoc_2 = (TextView) findViewById(R.id.tvoc_2);
        tvoc_tv2 = (TextView) findViewById(R.id.tvoc_tv2);

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
        Log.e(TAG, throwable.toString());

        toastor.showSingletonToast("网络连接异常");

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
        String title = SpUtils.getString("titile", "");
        if (!title.isEmpty())
            main_title.setText(title);
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
        inView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(myRunnable);
    }

    private void initdata() {
        if (listBean != null) {
            if (listBean.getDeviceName() == null || listBean.getDeviceName().trim().equals(""))
                main_place.setText("——");
            else
                main_place.setText(listBean.getDeviceName());
            if (listBean.get_$Pm25267() == null || listBean.get_$Pm25267().trim().equals("")) {
                main_pm.setText("——");
                main_pm_tv.setText("——");
            } else {
                Constan.PM2_5(main_pm, Double.parseDouble(listBean.get_$Pm25267()), pm_ll);
                main_pm_tv.setText(listBean.get_$Pm25267().trim().equals("0") ? "——" : listBean.get_$Pm25267());
            }

            if (listBean.getShidu() != null && !listBean.getShidu().trim().equals("")) {
                main_humidity.setText(listBean.getShidu().trim().equals("0") ? "——" : listBean.getShidu() + "%");
                main_humidity2.setText(listBean.getShidu().trim().equals("0") ? "——" : listBean.getShidu() + "%");
            } else {
                main_humidity.setText("——");
                main_humidity2.setText("——");
            }
            if (listBean.getWendu() != null && !listBean.getWendu().trim().equals("")) {
                main_temperature.setText(listBean.getWendu().trim().equals("0") ? "——" : listBean.getWendu() + "℃");
                main_temperature2.setText(listBean.getWendu().trim().equals("0") ? "——" : listBean.getWendu() + "℃");
            } else {
                main_temperature.setText("——");
                main_temperature2.setText("——");
            }
            if (listBean.getCo2() != null && !listBean.getCo2().equals("")) {
                Constan.CO2(co2_tv, Double.parseDouble(listBean.getCo2()), iv);
                Constan.CO2(co2_tv1, Double.parseDouble(listBean.getCo2()), co2_ll);
                Constan.CO2(co2_tv2, Double.parseDouble(listBean.getCo2()), co2_ll2);
                co2.setText(listBean.getCo2().trim().equals("0") ? "——" : listBean.getCo2());
                co2_1.setText(listBean.getCo2().trim().equals("0") ? "——" : listBean.getCo2());
                co2_2.setText(listBean.getCo2().trim().equals("0") ? "——" : listBean.getCo2());
            } else {
                co2.setText("——");
                co2_1.setText("——");
                co2_2.setText("——");
                co2_tv.setText("——");
                co2_tv1.setText("——");
                co2_tv2.setText("——");
            }
            if (listBean.getPm10() != null && !listBean.getPm10().equals("")) {
                pm10.setText(listBean.getPm10().trim().equals("0") ? "——" : listBean.getPm10());
                pm10_1.setText(listBean.getPm10().trim().equals("0") ? "——" : listBean.getPm10());
                pm10_2.setText(listBean.getPm10().trim().equals("0") ? "——" : listBean.getPm10());
                Constan.PM10(pm10_tv, Double.parseDouble(listBean.getPm10()), iv2);
                Constan.PM10(pm10_tv1, Double.parseDouble(listBean.getPm10()), pm10_ll);
                Constan.PM10(pm10_tv2, Double.parseDouble(listBean.getPm10()), pm10_ll2);
            } else {
                pm10_tv.setText("——");
                pm10_tv1.setText("——");
                pm10_tv2.setText("——");
                pm10_1.setText("——");
                pm10_2.setText("——");
                pm10.setText("——");
            }
            if (listBean.getJiaquan() != null && !listBean.getJiaquan().equals("")) {
                jiaquan.setText(listBean.getJiaquan().trim().equals("0") ? "——" : listBean.getJiaquan());
                jiaquan_1.setText(listBean.getJiaquan().trim().equals("0") ? "——" : listBean.getJiaquan());
                jiaquan_2.setText(listBean.getJiaquan().trim().equals("0") ? "——" : listBean.getJiaquan());
                Constan.jiaquan(jiaquan_tv, Double.parseDouble(listBean.getJiaquan()), iv3);
                Constan.jiaquan(jiaquan_tv1, Double.parseDouble(listBean.getJiaquan()), jiaquan_ll);
                Constan.jiaquan(jiaquan_tv2, Double.parseDouble(listBean.getJiaquan()), jiaquan_ll2);
            } else {
                jiaquan_tv.setText("——");
                jiaquan_tv2.setText("——");
                jiaquan_tv1.setText("——");
                jiaquan.setText("——");
                jiaquan_1.setText("——");
                jiaquan_2.setText("——");
            }

            if (listBean.getTvoc() != null && !listBean.getTvoc().equals("")) {
                tvoc.setText(listBean.getTvoc().trim().equals("0") ? "——" : listBean.getTvoc());
                tvoc_1.setText(listBean.getTvoc().trim().equals("0") ? "——" : listBean.getTvoc());
                tvoc_2.setText(listBean.getTvoc().trim().equals("0") ? "——" : listBean.getTvoc());
                Constan.TVOC(tvoc_tv, Double.parseDouble(listBean.getTvoc()), iv4);
                Constan.TVOC(tvoc_tv1, Double.parseDouble(listBean.getTvoc()), tvoc_ll);
                Constan.TVOC(tvoc_tv2, Double.parseDouble(listBean.getTvoc()), tvoc_ll2);
            } else {
                tvoc_tv.setText("——");
                tvoc_tv2.setText("——");
                tvoc_tv1.setText("——");
                tvoc.setText("——");
                tvoc_1.setText("——");
                tvoc_2.setText("——");
            }
        } else {
            main_place.setText("——");
            main_pm_tv.setText("——");
            main_pm.setText("——");
            main_humidity.setText("——");

            tvoc_tv.setText("——");
            tvoc_tv2.setText("——");
            tvoc_tv1.setText("——");
            tvoc.setText("——");
            tvoc_1.setText("——");
            tvoc_2.setText("——");

            jiaquan_tv.setText("——");
            jiaquan_tv2.setText("——");
            jiaquan_tv1.setText("——");
            jiaquan.setText("——");
            jiaquan_1.setText("——");
            jiaquan_2.setText("——");

            pm10_tv.setText("——");
            pm10_tv1.setText("——");
            pm10_tv2.setText("——");
            pm10_1.setText("——");
            pm10_2.setText("——");
            pm10.setText("——");

            co2.setText("——");
            co2_1.setText("——");
            co2_2.setText("——");
            co2_tv.setText("——");
            co2_tv1.setText("——");
            co2_tv2.setText("——");
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
                if (mList.size() - 1 > mark) {
                    mark++;
                    listBean = mList.get(mark);
                    Log.e("MainAcitvity1", "切换设备:" + mList.size() + "" + mark + "" + listBean.getDeviceName());
                } else if (mList.size() >= mark && mList.size() != 0) {
                    mark = 0;
                    listBean = mList.get(0);
                    Log.e("MainAcitvity2", "切换设备" + listBean.getDeviceName());
                } else {
                    listBean = null;
                }
                initdata();
                MyApplication.newInstance().setListBean(listBean);
                Log.e("MainAcitvity3", "切换设备" + listBean.getDeviceName() + ":" + listBean.getDeviceid());
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
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private void inView() {
        boolean pm25 = SpUtils.getBoolean("pm25", true);

        boolean chart = SpUtils.getBoolean("chart", true);

        boolean jiaquan = SpUtils.getBoolean("jiaquan", true);

        boolean co2 = SpUtils.getBoolean("co2", true);

        boolean pm10 = SpUtils.getBoolean("pm10", true);

        boolean tvoc = SpUtils.getBoolean("tvoc", true);

        if (SpUtils.getBoolean("chart", true)) {
            quxiantu_ll.setVisibility(View.VISIBLE);
            pager.setVisibility(View.VISIBLE);
            data_ll.setVisibility(View.VISIBLE);
            temperature_ll.setVisibility(View.GONE);
            humidity_ll.setVisibility(View.GONE);
            main_ll.setVisibility(View.VISIBLE);
            co2_ll.setVisibility(View.GONE);
            jiaquan_ll.setVisibility(View.GONE);
            pm10_ll.setVisibility(View.GONE);
            tvoc_ll.setVisibility(View.GONE);
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                //竖屏
            } else {
                //横屏
                //   data_ll.setVisibility(View.VISIBLE);
                co2_rl.setVisibility(co2 ? View.VISIBLE : View.GONE);
                pm10_rl.setVisibility(pm10 ? View.VISIBLE : View.GONE);
                jiaquan_rl.setVisibility(jiaquan ? View.VISIBLE : View.GONE);
                tvoc_rl.setVisibility(tvoc ? View.VISIBLE : View.GONE);
                pm_ll.setVisibility(pm25 ? View.VISIBLE : View.GONE);
            }
        } else {
            pager.setVisibility(View.GONE);
            data_ll.setVisibility(View.GONE);
            temperature_ll.setVisibility(View.VISIBLE);
            humidity_ll.setVisibility(View.VISIBLE);
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                //竖屏
                if (pm25 && jiaquan && co2 && pm10 && tvoc) {
                } else {

                }
            } else {
                if (pm25 && jiaquan && co2 && pm10 && tvoc) {
                    main_ll.setVisibility(View.VISIBLE);
                    quxiantu_ll.setVisibility(View.VISIBLE);
                    co2_ll.setVisibility(View.GONE);
                    jiaquan_ll.setVisibility(View.GONE);
                    pm10_ll.setVisibility(View.GONE);
                    tvoc_ll.setVisibility(View.GONE);
                } else {
                    quxiantu_ll.setVisibility(View.GONE);
                    co2_ll.setVisibility(co2 ? View.VISIBLE : View.GONE);
                    pm_ll.setVisibility(pm25 ? View.VISIBLE : View.GONE);
                    jiaquan_ll.setVisibility(jiaquan ? View.VISIBLE : View.GONE);
                    pm10_ll.setVisibility(pm10 ? View.VISIBLE : View.GONE);
                    tvoc_ll.setVisibility(tvoc ? View.VISIBLE : View.GONE);
                }
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {

            case KeyEvent.KEYCODE_DPAD_DOWN:
                //切换设备
                if (mList.size() - 1 > mark) {
                    mark++;
                    listBean = mList.get(mark);
                    Log.e("MainAcitvity1", "切换设备:" + mList.size() + "" + mark + "" + listBean.getDeviceName());
                } else if (mList.size() >= mark && mList.size() != 0) {
                    mark = 0;
                    listBean = mList.get(mark);
                    Log.e("MainAcitvity2", "切换设备" + listBean.getDeviceName());
                } else {
                    listBean = null;
                }
                setMark();
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (indext == 0) {
                    indext = 4;
                } else {
                    indext--;
                }
                setPager(indext);
                // toastor.showSingletonToast("你按下左方向键");
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (indext == 4) {
                    indext = 0;
                } else {
                    indext++;
                }
                setPager(indext);

                // toastor.showSingletonToast("你按下右方向键");
                break;

            case KeyEvent.KEYCODE_DPAD_UP:
                if (mark > 0 && mList.size() != 0) {
                    mark--;
                    listBean = mList.get(mark);
                    Log.e("MainAcitvity1", "切换设备:" + mList.size() + "" + mark + "" + listBean.getDeviceName());
                } else if (mark == 0 && mList.size() != 0) {
                    mark = mList.size() - 1;
                    listBean = mList.get(mark);
                    Log.e("MainAcitvity2", "切换设备" + listBean.getDeviceName());
                } else {
                    listBean = null;
                }
                setMark();
                //toastor.showSingletonToast("你按下上方向键");
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setPager(int indext) {
        handler.removeCallbacks(dataRunnable);
        pager.setCurrentItem(indext);
        //切换页面历史数据
        handler.postDelayed(dataRunnable, (dataTime * 1000));
    }

    private void setMark() {
        handler.removeCallbacks(deviceRunnable);
        handler.removeCallbacks(dataRunnable);

        initdata();
        MyApplication.newInstance().setListBean(listBean);
        Log.e("MainAcitvity3", "切换设备" + listBean.getDeviceName() + ":" + listBean.getDeviceid());
        Intent intent = new Intent();
        intent.setAction(ACTION_BLE_NOTIFY_DATA);
        sendBroadcast(intent);
        handler.postDelayed(deviceRunnable, (deviceTime * 60 * 1000));
        handler.postDelayed(dataRunnable, (dataTime * 1000));
    }
}
