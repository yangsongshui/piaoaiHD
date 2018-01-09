package myapplication.com.piaoaihd;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import myapplication.com.piaoaihd.base.BaseActivity;
import myapplication.com.piaoaihd.util.SpUtils;

import static myapplication.com.piaoaihd.R.id.data_20;

public class SettingActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnFocusChangeListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    RadioGroup device_rg, data_rg;
    ImageButton set_back_ib;
    CheckBox cb_chart, cb_jiaquan, cb_tvoc, cb_pm10, cb_o2, cb_pm25;
    Button out_tv;
    public static final int RESULT_CODE_QR_SCAN = 0xA1;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {
        device_rg = (RadioGroup) findViewById(R.id.device_rg);
        data_rg = (RadioGroup) findViewById(R.id.data_rg);
        set_back_ib = (ImageButton) findViewById(R.id.set_back_ib);
        out_tv = (Button) findViewById(R.id.out_tv);
        initView();
        findViewById(R.id.device_1).setOnFocusChangeListener(this);
        findViewById(R.id.device_15).setOnFocusChangeListener(this);
        findViewById(R.id.device_30).setOnFocusChangeListener(this);
        findViewById(R.id.device_1h).setOnFocusChangeListener(this);
        findViewById(R.id.device_2h).setOnFocusChangeListener(this);
        cb_chart = (CheckBox) findViewById(R.id.cb_chart);
        cb_jiaquan = (CheckBox) findViewById(R.id.cb_jiaquan);
        cb_tvoc = (CheckBox) findViewById(R.id.cb_tvoc);
        cb_pm10 = (CheckBox) findViewById(R.id.cb_pm10);
        cb_o2 = (CheckBox) findViewById(R.id.cb_o2);
        cb_pm25 = (CheckBox) findViewById(R.id.cb_pm25);
        out_tv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    out_tv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    out_tv.setTextColor(getResources().getColor(R.color.white));
                } else {
                    out_tv.setBackgroundColor(getResources().getColor(R.color.orange_transparent));
                    out_tv.setTextColor(getResources().getColor(R.color.black_opaque));
                }
            }
        });
        cb_pm25.setChecked(SpUtils.getBoolean("pm25", true));
        cb_chart.setChecked(SpUtils.getBoolean("chart", true));
        cb_jiaquan.setChecked(SpUtils.getBoolean("jiaquan", true));
        cb_o2.setChecked(SpUtils.getBoolean("o2", true));
        cb_pm10.setChecked(SpUtils.getBoolean("pm10", true));
        cb_tvoc.setChecked(SpUtils.getBoolean("tvoc", true));
        out_tv.setOnClickListener(this);
        findViewById(data_20).setOnFocusChangeListener(this);
        findViewById(R.id.data_40).setOnFocusChangeListener(this);
        findViewById(R.id.data_1).setOnFocusChangeListener(this);
        findViewById(R.id.data_2).setOnFocusChangeListener(this);
        findViewById(R.id.data_5).setOnFocusChangeListener(this);
        findViewById(R.id.out_tv).setOnClickListener(this);
        set_back_ib.setOnFocusChangeListener(this);
        set_back_ib.setOnClickListener(this);
        data_rg.setOnCheckedChangeListener(this);
        device_rg.setOnCheckedChangeListener(this);

        cb_pm25.setOnCheckedChangeListener(this);
        cb_jiaquan.setOnCheckedChangeListener(this);
        cb_tvoc.setOnCheckedChangeListener(this);
        cb_pm10.setOnCheckedChangeListener(this);
        cb_o2.setOnCheckedChangeListener(this);
        cb_chart.setOnCheckedChangeListener(this);

    }

    private void initView() {
        int deviceTime = SpUtils.getInt("device", 15);
        int dataTime = SpUtils.getInt("data", 40);
        switch (deviceTime) {
            case 1:
                device_rg.check(R.id.device_1);
                break;
            case 15:
                device_rg.check(R.id.device_15);
                break;
            case 30:
                device_rg.check(R.id.device_30);
                break;
            case 60:
                device_rg.check(R.id.device_1h);
                break;
            case 120:
                device_rg.check(R.id.device_2h);
                break;
        }
        switch (dataTime) {
            case 20:
                data_rg.check(R.id.data_20);
                break;
            case 40:
                data_rg.check(R.id.data_40);
                break;
            case 60:
                data_rg.check(R.id.data_1);
                break;
            case 120:
                data_rg.check(R.id.data_2);
                break;
            case 300:
                data_rg.check(R.id.data_5);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.device_1:
                SpUtils.putInt("device", 1);
                break;
            case R.id.device_15:
                SpUtils.putInt("device", 15);
                break;
            case R.id.device_30:
                SpUtils.putInt("device", 30);
                break;
            case R.id.device_1h:
                SpUtils.putInt("device", 60);
                break;
            case R.id.device_2h:
                SpUtils.putInt("device", 120);
                break;
            case data_20:
                SpUtils.putInt("data", 20);
                break;
            case R.id.data_40:
                SpUtils.putInt("data", 40);
                break;
            case R.id.data_1:
                SpUtils.putInt("data", 60);
                break;
            case R.id.data_2:
                SpUtils.putInt("data", 120);
                break;
            case R.id.data_5:
                SpUtils.putInt("data", 300);
                break;
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.set_back_ib) {
            if (hasFocus)
                set_back_ib.setBackgroundColor(getResources().getColor(R.color.background_gradient_end));
            else
                set_back_ib.setBackgroundColor(Color.argb(00, 255, 255, 255));
        } else {
            if (hasFocus) {
                ((RadioButton) v).setTextColor(getResources().getColor(R.color.spindle2));
            } else {
                ((RadioButton) v).setTextColor(getResources().getColor(R.color.white));
            }
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.out_tv) {
            this.setResult(RESULT_CODE_QR_SCAN);
        }
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_pm25:
                SpUtils.putBoolean("pm25", isChecked);
                break;
            case R.id.cb_chart:
                SpUtils.putBoolean("chart", isChecked);
                break;
            case R.id.cb_jiaquan:
                SpUtils.putBoolean("jiaquan", isChecked);
                break;
            case R.id.cb_o2:
                SpUtils.putBoolean("o2", isChecked);
                break;
            case R.id.cb_pm10:
                SpUtils.putBoolean("pm10", isChecked);
                break;
            case R.id.cb_tvoc:
                SpUtils.putBoolean("tvoc", isChecked);
                break;
        }
    }
}
