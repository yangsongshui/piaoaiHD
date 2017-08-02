package myapplication.com.piaoaihd.presenter;

import android.content.Context;

import myapplication.com.piaoaihd.base.BasePresenterImp;
import myapplication.com.piaoaihd.bean.Facility;
import myapplication.com.piaoaihd.model.FacilityModelImp;
import myapplication.com.piaoaihd.view.FacilityView;


/**
 * Created by ys on 2017/6/16.
 */

public class FacilityPresenerImp extends BasePresenterImp<FacilityView, Facility> implements FacilityPresener {
    private Context context = null;
    private FacilityModelImp facilityModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author ys
     * @date 2017/6/13 15:12
     */
    public FacilityPresenerImp(FacilityView view, Context context) {
        super(view);
        this.context = context;
        this.facilityModelImp = new FacilityModelImp(context);
    }

    @Override
    public void findUserDevice(String phoneNumber) {
        facilityModelImp.findUserDevice(phoneNumber, this);
    }

    @Override
    public void unSubscribe() {
        facilityModelImp.onUnsubscribe();
    }
}
