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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import myapplication.com.piaoaihd.base.BaseActivity;
import myapplication.com.piaoaihd.base.BaseFragment;


public class MainActivity extends BaseActivity {


    private Fragment[] frags = new Fragment[3];
    protected BaseFragment baseFragment;
    private ChartFragment dataFragment;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        initData();
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
}
