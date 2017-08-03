package myapplication.com.piaoaihd;

import android.app.Activity;
import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import myapplication.com.piaoaihd.bean.User;
import myapplication.com.piaoaihd.util.AppContextUtil;
import myapplication.com.piaoaihd.util.Log;
import myapplication.com.piaoaihd.util.SpUtils;

/**
 * Created by omni20170501 on 2017/6/8.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    public User user;
    private static MyApplication instance;
    public static List<Activity> activitiesList = new ArrayList<Activity>(); // 活动管理集合


    /**
     * 获取单例
     *
     * @return MyApplication
     */
    public static MyApplication newInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        user = new User();
        if (user.getResBody() == null)
            user.setResBody(new User.ResBodyBean());
        AppContextUtil.init(this);
        SpUtils.init(this);

    }

    /**
     * 把活动添加到活动管理集合
     *
     * @param activity
     */
    public void addActyToList(Activity activity) {
        if (!activitiesList.contains(activity))
            activitiesList.add(activity);
    }
    public RequestManager getGlide() {

        return Glide.with(this);


    }

    /**
     * 把活动从活动管理集合移除
     *
     * @param activity
     */
    public void removeActyFromList(Activity activity) {
        if (activitiesList.contains(activity))
            activitiesList.remove(activity);
    }

    /**
     * 程序退出
     */
    public void clearAllActies() {
        for (Activity acty : activitiesList) {
            if (acty != null)
                acty.finish();
        }

    }

    public void setUser(User user) {
        this.user = user;
        Log.e("user", this.user.toString());
        Boolean IsRemember = SpUtils.getBoolean("remember", true);
        if (IsRemember) {
            SpUtils.putString("phone", user.getResBody().getPhoneNumber());
            SpUtils.putString("password", user.getResBody().getPassWord());
            Log.e("------", user.toString());
        }

    }

    public User getUser() {
        if (user.getResBody().getPhoneNumber() != null)
            return user;
        String phone = SpUtils.getString("phone", "");
        String password = SpUtils.getString("password", "");
        Log.e("------", phone + " " + password);
        if (phone.equals("") || password.equals(""))
            return null;
        user.getResBody().setPhoneNumber(phone);
        user.getResBody().setPassWord(password);
        return user;


    }
}
