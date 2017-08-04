package myapplication.com.piaoaihd.presenter;

import android.content.Context;

import java.util.Map;

import myapplication.com.piaoaihd.base.BasePresenterImp;
import myapplication.com.piaoaihd.bean.PMBean;
import myapplication.com.piaoaihd.model.BindingPresenter;
import myapplication.com.piaoaihd.model.PMDataModelImp;
import myapplication.com.piaoaihd.view.PMView;


/**
 * 描述：MVP中的P实现类
 */
public class PMdataPresenterImp extends BasePresenterImp<PMView,PMBean> implements BindingPresenter {
    private Context context = null;
    private PMDataModelImp pmDataModelImp = null;
    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author dc
     * @date 2017/6/13 15:12
     */
    public PMdataPresenterImp(PMView view, Context context) {
        super(view);
        this.context = context;
        this.pmDataModelImp = new PMDataModelImp(context);

    }

    @Override
    public void binding(Map<String, String> map) {

            pmDataModelImp.GetData(map, this);
    }

    @Override
    public void unSubscribe() {
        pmDataModelImp.onUnsubscribe();

    }
}

