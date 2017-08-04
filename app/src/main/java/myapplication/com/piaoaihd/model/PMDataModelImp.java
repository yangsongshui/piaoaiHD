package myapplication.com.piaoaihd.model;

import android.content.Context;

import java.util.Map;

import myapplication.com.piaoaihd.api.ServiceApi;
import myapplication.com.piaoaihd.base.BaseModel;
import myapplication.com.piaoaihd.base.IBaseRequestCallBack;
import myapplication.com.piaoaihd.bean.PMBean;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ys on 2017/6/19.
 */

public class PMDataModelImp extends BaseModel implements PMDataModel<PMBean> {
    private Context context = null;
    private ServiceApi serviceApi;
    private CompositeSubscription mCompositeSubscription;

    public PMDataModelImp(Context mContext) {
        super();
        context = mContext;
        serviceApi = retrofitManager.getService();
        mCompositeSubscription = new CompositeSubscription();
    }


    @Override
    public void GetData(Map<String, String> map, final IBaseRequestCallBack<PMBean> iBaseRequestCallBack) {
        mCompositeSubscription.add(serviceApi.getHistoryDataByPM2_5(map)
                .observeOn(AndroidSchedulers.mainThread())//指定事件消费线程
                .subscribeOn(Schedulers.io())   //指定 subscribe() 发生在 IO 线程
                .subscribe(new Subscriber<PMBean>() {

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
                    public void onNext(PMBean msg) {
                        iBaseRequestCallBack.requestSuccess(msg);

                    }

                })
        );
    }

    @Override
    public void onUnsubscribe() {

    }
}
