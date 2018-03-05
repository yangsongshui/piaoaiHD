package myapplication.com.piaoaihd;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import myapplication.com.piaoaihd.bean.Facility;


/**
 * Created by omni20170501 on 2017/6/6.
 */

public class SixAdapter extends RecyclerView.Adapter<SixAdapter.ViewHolder> {
    private List<Facility.ResBodyBean.ListBean> data;
    private Context context;

    public SixAdapter(Context context) {
        data = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_six, parent, false);
        return new SixAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Facility.ResBodyBean.ListBean listBean = data.get(position);
        if (listBean != null){
            if (listBean.get_$Pm25267() != null&&listBean.getStatusDevice().equals("开启")) {
                holder.name.setTextColor(context.getResources().getColor(R.color.red));
                holder.state.setTextColor(context.getResources().getColor(R.color.red));
                if (listBean.get_$Pm25267() != null || !listBean.get_$Pm25267().trim().equals(""))
                    holder.pm2.setText(listBean.get_$Pm25267());
                if (listBean.getWendu() != null || !listBean.getWendu().trim().equals(""))
                    holder.wendu.setText(listBean.getWendu());
                if (listBean.getShidu() != null || !listBean.getShidu().trim().equals(""))
                    holder.shidu.setText(listBean.getShidu());
                if (listBean.getJiaquan() != null || !listBean.getJiaquan().trim().equals(""))
                    holder.jiaquan.setText(listBean.getJiaquan());
                if (listBean.getTvoc() != null || !listBean.getTvoc().trim().equals(""))
                    holder.tvoc.setText(listBean.getTvoc());
                if (listBean.getCo2() != null || !listBean.getCo2().trim().equals(""))
                    holder.co2.setText(listBean.getCo2());
            } else {
                holder.name.setTextColor(context.getResources().getColor(R.color.blued));
                holder.state.setTextColor(context.getResources().getColor(R.color.blued));
            }
            holder.name.setText(listBean.getDeviceName());
            holder.state.setText(listBean.getStatusDevice());
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, state, wendu, shidu, jiaquan, pm2, tvoc, co2;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            state = (TextView) itemView.findViewById(R.id.state);
            wendu = (TextView) itemView.findViewById(R.id.wendu_tv);
            shidu = (TextView) itemView.findViewById(R.id.shidu_tv);
            jiaquan = (TextView) itemView.findViewById(R.id.jiaquan_tv);
            pm2 = (TextView) itemView.findViewById(R.id.pm_tv);
            tvoc = (TextView) itemView.findViewById(R.id.tvoc_tv);
            co2 = (TextView) itemView.findViewById(R.id.co2_tv);

        }
    }

    public void setList(List<Facility.ResBodyBean.ListBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

}
