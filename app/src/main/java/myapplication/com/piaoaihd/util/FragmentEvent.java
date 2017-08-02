package myapplication.com.piaoaihd.util;

import java.util.List;

import myapplication.com.piaoaihd.bean.Facility;

/**
 * Created by ys on 2017/6/21.
 */

public class FragmentEvent {
    List<Facility.ResBodyBean.ListBean> mList;
    public FragmentEvent( List<Facility.ResBodyBean.ListBean> list) {

        mList = list;
    }
    public  List<Facility.ResBodyBean.ListBean> getMsg(){
        return mList;
    }
}
