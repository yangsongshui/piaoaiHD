package myapplication.com.piaoaihd;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import myapplication.com.piaoaihd.base.BaseActivity;

public class SettingActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnFocusChangeListener {
    RadioGroup device_rg, data_rg;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {
        device_rg = (RadioGroup) findViewById(R.id.device_rg);
        data_rg = (RadioGroup) findViewById(R.id.data_rg);
        findViewById(R.id.device_1).setOnFocusChangeListener(this);
        findViewById(R.id.device_15).setOnFocusChangeListener(this);
        findViewById(R.id.device_30).setOnFocusChangeListener(this);
        findViewById(R.id.device_1h).setOnFocusChangeListener(this);
        findViewById(R.id.device_2h).setOnFocusChangeListener(this);

        findViewById(R.id.data_20).setOnFocusChangeListener(this);
        findViewById(R.id.data_40).setOnFocusChangeListener(this);
        findViewById(R.id.data_1).setOnFocusChangeListener(this);
        findViewById(R.id.data_2).setOnFocusChangeListener(this);
        findViewById(R.id.data_5).setOnFocusChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        switch (checkedId) {
            case R.id.device_1:
                break;
            case R.id.device_15:
                break;
            case R.id.device_30:
                break;
            case R.id.device_1h:
                break;
            case R.id.device_2h:
                break;
            case R.id.data_20:
                break;
            case R.id.data_40:
                break;
            case R.id.data_1:
                break;
            case R.id.data_2:
                break;
            case R.id.data_5:
                break;
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            ( (RadioButton) v).setTextColor(getResources().getColor(R.color.spindle2));
        } else {
            ( (RadioButton) v).setTextColor(getResources().getColor(R.color.white));
        }
    }

}
