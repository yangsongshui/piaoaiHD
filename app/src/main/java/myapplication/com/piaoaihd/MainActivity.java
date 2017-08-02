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
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import myapplication.com.piaoaihd.base.BaseActivity;
import myapplication.com.piaoaihd.base.BaseFragment;
import myapplication.com.piaoaihd.bean.Facility;
import myapplication.com.piaoaihd.presenter.FacilityPresenerImp;
import myapplication.com.piaoaihd.util.Constan;
import myapplication.com.piaoaihd.util.FragmentEvent;
import myapplication.com.piaoaihd.util.Toastor;
import myapplication.com.piaoaihd.view.FacilityView;

import static android.R.attr.id;


public class MainActivity extends BaseActivity implements FacilityView {
    private final static String TAG = MainActivity.class.getSimpleName();

    private Fragment[] frags = new Fragment[3];
    protected BaseFragment baseFragment;
    private ChartFragment dataFragment;
    private TextView main_place, main_pm_tv, main_pm, main_temperature, main_humidity;
    private TextView co2, co2_tv, pm10, pm10_tv, jiaquan, jiaquan_tv, tvoc, tvoc_tv;
    private Handler handler;
    private Runnable myRunnable;
    private Toastor toastor;
    private FacilityPresenerImp facilityPresenerImp;
    private ProgressDialog progressDialog = null;
    private boolean isOne = true;
    List<Facility.ResBodyBean.ListBean> mList;
    Facility.ResBodyBean.ListBean listBean;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        mList = new ArrayList<>();
        handler = new Handler();
        facilityPresenerImp = new FacilityPresenerImp(this, this);
        toastor = new Toastor(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据更新中,请稍后");
        initView();
        initData();
        myRunnable = new Runnable() {
            @Override
            public void run() {
                facilityPresenerImp.findUserDevice(MyApplication.newInstance().getUser().getResBody().getPhoneNumber());
                handler.postDelayed(myRunnable, 60000);
            }
        };
    }

    private void initView() {


        main_place = (TextView) findViewById(R.id.main_place);
        main_pm_tv = (TextView) findViewById(R.id.main_pm_tv);
        main_pm = (TextView) findViewById(R.id.main_pm);
        main_temperature = (TextView) findViewById(R.id.main_temperature);
        main_humidity = (TextView) findViewById(R.id.main_humidity);

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
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });
    }

    private void initData() {
        if (dataFragment == null) {
            dataFragment = new ChartFragment();
        }

        if (!dataFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.main_fl, dataFragment).commit();
            baseFragment = dataFragment;
        }
    }

    private Fragment getFrag(int index) {
        switch (index) {
            case 0:
                if (dataFragment != null)
                    return dataFragment;
                else
                    return new ChartFragment();
            case 1:
                return new ChartFragment();
            case 2:
                return new ChartFragment();
            case 3:
                return new ChartFragment();
            default:
                return null;
        }
    }

    /**
     * 添加或者显示 fragment
     *
     * @param transaction
     * @param fragment
     */
    protected void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (baseFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(baseFragment).add(R.id.main_fl, fragment).commit();
        } else {
            transaction.hide(baseFragment).show(fragment).commit();
        }

        baseFragment = (BaseFragment) fragment;
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
        Log.e(TAG, tData.toString());
        if (tData.getResCode().equals("0")) {
            mList = tData.getResBody().getList();
            EventBus.getDefault().post(new FragmentEvent(tData.getResBody().getList()));
            if (listBean == null) {
                listBean = mList.get(0);
                initdata();
            }

        } else {
            toastor.showSingletonToast(tData.getResMessage());
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        toastor.showSingletonToast("网络连接异常");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                toastor.showSingletonToast("你按下中间键");
                if (id == R.id.main_set) {
                    startActivity(new Intent(this, SettingActivity.class));
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(myRunnable);
        handler = null;
        facilityPresenerImp = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        facilityPresenerImp.findUserDevice(MyApplication.newInstance().getUser().getResBody().getPhoneNumber());
        handler.postDelayed(myRunnable, 60000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(myRunnable);
    }

    private void initdata() {
        if (listBean != null) {
            main_place.setText(listBean.getDeviceName().trim().equals("") ? "——" : listBean.getDeviceName());
            main_pm_tv.setText(listBean.get_$Pm25267().trim().equals("") ? "——" : listBean.get_$Pm25267());
            Constan.PM2_5(main_pm, Integer.parseInt(listBean.get_$Pm25267()));
            main_temperature.setText("——");
            main_humidity.setText(listBean.getShidu().trim().equals("") ? "——" : listBean.getShidu());
            co2.setText(listBean.getCo2().trim().equals("") ? "——" : listBean.getCo2());
            Constan.CO2(co2_tv, Integer.parseInt(listBean.getCo2()));
            pm10.setText(listBean.getPm10().trim().equals("") ? "——" : listBean.getPm10());
            Constan.PM10(pm10_tv, Integer.parseInt(listBean.getPm10()));
            jiaquan.setText(listBean.getJiaquan().trim().equals("") ? "——" : listBean.getJiaquan());
            Constan.jiaquan(jiaquan_tv, Integer.parseInt(listBean.getJiaquan()));
            tvoc.setText(listBean.getTvoc().trim().equals("") ? "——" : listBean.getTvoc());
            Constan.TVOC(jiaquan_tv, Integer.parseInt(listBean.getTvoc()));

        }
    }


}
