package myapplication.com.piaoaihd.model;

import android.content.Context;

import myapplication.com.piaoaihd.api.ServiceApi;
import myapplication.com.piaoaihd.base.BaseModel;
import myapplication.com.piaoaihd.base.IBaseRequestCallBack;
import myapplication.com.piaoaihd.bean.User;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ys on 2017/6/13.
 */

public class LoginModelImp extends BaseModel implements LoginModel<User>{

    private Context context = null;
    private ServiceApi serviceApi;
    private CompositeSubscription mCompositeSubscription;

    public LoginModelImp(Context mContext) {
        super();
        context = mContext;
        serviceApi = retrofitManager.getService();
        mCompositeSubscription = new CompositeSubscription();
    }
    @Override
    public void loadLogin(String phoneNumber, String psw, final IBaseRequestCallBack<User> iBaseRequestCallBack) {
        mCompositeSubscription.add(serviceApi.loadLoginInfo(phoneNumber,psw)
                .observeOn(AndroidSchedulers.mainThread())//指定事件消费线程
                .subscribeOn(Schedulers.io())   //指定 subscribe() 发生在 IO 线程
                .subscribe(new Subscriber<User>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        //onStart它总是在 subscribe 所发生的线程被调用 ,如果你的subscribe不是主线程，则会出错，则需要指定线程;
                        iBaseRequestCallBack.beforeRequest();
                    }

                    @Override
                    public void onCompleted() {
                        //回调接口：请求已完成，可以隐藏progress
                        iBaseRequestCallBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //回调接口：请求异常
                        iBaseRequestCallBack.requestError(e);
                    }

                    @Override
                    public void onNext(User msg) {
                        iBaseRequestCallBack.requestSuccess(msg);

                    }

                })
        );
    }

    @Override
    public void onUnsubscribe() {

    }
}
