package myapplication.com.piaoaihd.presenter;

import android.content.Context;

import myapplication.com.piaoaihd.base.BasePresenterImp;
import myapplication.com.piaoaihd.bean.User;
import myapplication.com.piaoaihd.model.LoginModelImp;
import myapplication.com.piaoaihd.view.LoginView;


/**
 * 描述：MVP中的P实现类
 */
public class LoginPresenterImp extends BasePresenterImp<LoginView,User> implements LoginPresenter{
    //传入泛型V和T分别为WeatherView、WeatherInfoBean表示建立这两者之间的桥梁
    private Context context = null;
    private LoginModelImp loginModelImp = null;
    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author ys
     * @date 2017/6/13 15:12
     */
    public LoginPresenterImp(LoginView view, Context context) {
        super(view);
        this.context = context;
        this.loginModelImp = new LoginModelImp(context);
    }

    @Override
    public void loadLogin(String phoneNumber, String psw) {
        loginModelImp.loadLogin(phoneNumber,psw, this);
    }

    @Override
    public void unSubscribe() {
        loginModelImp.onUnsubscribe();
    }
}
