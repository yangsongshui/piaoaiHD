package myapplication.com.piaoaihd.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    protected View layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(getContentView(), null);

        initData(layout,savedInstanceState);
        return layout;
    }

    protected abstract void initData(View layout, Bundle savedInstanceState);

    protected abstract int getContentView();
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
