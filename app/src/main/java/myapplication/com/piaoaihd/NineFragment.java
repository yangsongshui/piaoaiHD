package myapplication.com.piaoaihd;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import myapplication.com.piaoaihd.base.BaseFragment;
import myapplication.com.piaoaihd.bean.Facility;
import myapplication.com.piaoaihd.util.FragmentEvent;

/**
 * 作者：omni20170501
 */

public class NineFragment extends BaseFragment {
    RecyclerView nineRv;
    NineAdapter nineAdapter;

    List<Facility.ResBodyBean.ListBean> mList;
    List<List<Facility.ResBodyBean.ListBean>> data;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        nineAdapter = new NineAdapter(getActivity());
        data = new ArrayList<>();
        mList = new ArrayList<>();
        nineRv = (RecyclerView) layout.findViewById(R.id.nine_rv);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        nineRv.setLayoutManager(manager);
        nineRv.setHasFixedSize(true);
        nineRv.setAdapter(nineAdapter);

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_nine;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FragmentEvent event) {
        mList.clear();
        mList.addAll(event.getMsg());
        initList();
    }

    int max = 0;
    int indext = 0;

    private void initList() {
        int le = 16 - mList.size() % 16;
        Log.e("initList", le + " " + mList.size());
        List<Facility.ResBodyBean.ListBean> list = new ArrayList<>();
        if (le != 0) {
            for (int i = 0; i < le; i++) {
                mList.add(new Facility.ResBodyBean.ListBean());
            }
        }

        Log.e("initList", le + " " + mList.size());
        max = mList.size() / 16;
        for (int i = 0; i < mList.size(); i++) {
            list.add(mList.get(i));
            if (list.size() % 16 == 0) {
                List<Facility.ResBodyBean.ListBean> li = new ArrayList<>();
                li.addAll(list);
                data.add(li);
                list.clear();
            }
        }
        indext++;
        if (indext > max)
            indext = 0;
        nineAdapter.setList(data.get(indext));
    }
}
