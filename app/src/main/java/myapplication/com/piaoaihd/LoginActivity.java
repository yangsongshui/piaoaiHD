package myapplication.com.piaoaihd;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import myapplication.com.piaoaihd.base.BaseActivity;

public class LoginActivity extends BaseActivity {
    Button login_tv;
    int id;
    Toastor toastor;
    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        toastor=new Toastor(this);
        login_tv = (Button) findViewById(R.id.login_tv);
        login_tv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    id = R.id.login_tv;
                    toastor.showSingletonToast("按键获取焦点");
                    login_tv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    login_tv.setTextColor(getResources().getColor(R.color.white));
                }else {
                    login_tv.setBackgroundColor(getResources().getColor(R.color.orange_transparent));
                    login_tv.setTextColor(getResources().getColor(R.color.black_opaque));
                }
            }
        });
        login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                toastor.showSingletonToast("你按下中间键");
                if (id==R.id.login_tv){
                    startActivity(new Intent(this,MainActivity.class));
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
