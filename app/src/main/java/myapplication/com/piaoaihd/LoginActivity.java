package myapplication.com.piaoaihd;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import myapplication.com.piaoaihd.base.BaseActivity;
import myapplication.com.piaoaihd.bean.User;
import myapplication.com.piaoaihd.presenter.LoginPresenterImp;
import myapplication.com.piaoaihd.util.MD5;
import myapplication.com.piaoaihd.util.Toastor;
import myapplication.com.piaoaihd.view.LoginView;

public class LoginActivity extends BaseActivity implements LoginView {
    Button login_tv;
    int id;
    Toastor toastor;
    private ProgressDialog progressDialog = null;
    private LoginPresenterImp loginPresenterImp = null;
    String psw;
    EditText login_phone, login_psw;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        toastor = new Toastor(this);
        login_tv = (Button) findViewById(R.id.login_tv);
        login_phone = (EditText) findViewById(R.id.login_phone);
        login_psw = (EditText) findViewById(R.id.login_psw);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("登陆中,请稍后");
        loginPresenterImp = new LoginPresenterImp(this, this);
        login_tv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    id = R.id.login_tv;
                   // toastor.showSingletonToast("按键获取焦点");
                    login_tv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    login_tv.setTextColor(getResources().getColor(R.color.white));
                } else {
                    login_tv.setBackgroundColor(getResources().getColor(R.color.orange_transparent));
                    login_tv.setTextColor(getResources().getColor(R.color.black_opaque));
                }
            }
        });
        login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = login_phone.getText().toString().trim();
                psw = login_psw.getText().toString().trim();
                if (phone.length() == 11 && psw.length() >= 6)
                    loginPresenterImp.loadLogin(phone, MD5.getMD5(psw));
                else
                    toastor.showSingletonToast("登陆信息有误");
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                toastor.showSingletonToast("你按下中间键");
                if (id == R.id.login_tv) {
                    String phone = login_phone.getText().toString().trim();
                    psw = login_psw.getText().toString().trim();
                    if (phone.length() == 11 && psw.length() >= 6)
                        loginPresenterImp.loadLogin(phone, MD5.getMD5(psw));
                    else
                        toastor.showSingletonToast("登陆信息有误");
                }
                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:
              //  toastor.showSingletonToast("你按下下方向键");
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
               // toastor.showSingletonToast("你按下左方向键");
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
               // toastor.showSingletonToast("你按下右方向键");
                break;

            case KeyEvent.KEYCODE_DPAD_UP:
                //toastor.showSingletonToast("你按下上方向键");
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showProgress() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

    }

    @Override
    public void disimissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void loadDataSuccess(User tData) {
        toastor.showSingletonToast(tData.getResMessage());
        if (tData.getResCode().equals("0")) {
            tData.getResBody().setPassWord(psw);
            MyApplication.newInstance().setUser(tData);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        toastor.showSingletonToast("服务器连接失败");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    0);
            //判断是否需要向用户解释，为什么要申请该权限（用户点击了不再提醒）
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
            }
        }else {
            if (MyApplication.newInstance().getUser() != null) {
                User user = MyApplication.newInstance().getUser();
                psw = user.getResBody().getPassWord();
                loginPresenterImp.loadLogin(user.getResBody().getPhoneNumber(), MD5.getMD5(psw));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == 0) {
            if (MyApplication.newInstance().getUser() != null) {
                User user = MyApplication.newInstance().getUser();
                psw = user.getResBody().getPassWord();
                loginPresenterImp.loadLogin(user.getResBody().getPhoneNumber(), MD5.getMD5(psw));
            }
        }
    }
}
