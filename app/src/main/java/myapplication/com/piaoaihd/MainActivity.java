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

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import myapplication.com.piaoaihd.base.BaseActivity;
import myapplication.com.piaoaihd.base.BaseFragment;
import myapplication.com.piaoaihd.util.Toastor;

import static myapplication.com.piaoaihd.R.id.login_tv;


public class MainActivity extends BaseActivity {


    private Fragment[] frags = new Fragment[3];
    protected BaseFragment baseFragment;
    private ChartFragment dataFragment;
    private TextView main_time, main_place, main_pm_tv, main_pm, main_temperature, main_humidity;
    private TextView co2, co2_tv, pm10, pm10_tv, jiaquan, jiaquan_tv, tvoc, tvoc_tv;
    private Button main_set;
    int id = 0;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        toastor = new Toastor(this);
        initView();
        initData();
    }

    private void initView() {
        main_time = (TextView) findViewById(R.id.main_time);
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

        main_set = (Button) findViewById(R.id.main_set);
        main_set.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    id = login_tv;
                    toastor.showSingletonToast("按键获取焦点");
                   // main_set.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                }else {
                   // main_set.setBackgroundColor(getResources().getColor(R.color.background_gradient_end));

                }
            }
        });
        main_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                toastor.showSingletonToast("你按下中间键");
                if (id==R.id.main_set){
                    startActivity(new Intent(this,SettingActivity.class));
                }
                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:
                toastor.showSingletonToast("你按下下方向键");
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                toastor.showSingletonToast("你按下左方向键");
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                toastor.showSingletonToast("你按下右方向键");
                break;

            case KeyEvent.KEYCODE_DPAD_UP:
                toastor.showSingletonToast("你按下上方向键");
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
