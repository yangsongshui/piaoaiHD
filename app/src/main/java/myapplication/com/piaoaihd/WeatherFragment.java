package myapplication.com.piaoaihd;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.math.BigDecimal;

import myapplication.com.piaoaihd.api.ServiceApi;
import myapplication.com.piaoaihd.base.BaseFragment;
import myapplication.com.piaoaihd.bean.Weather;
import myapplication.com.piaoaihd.util.Toastor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static myapplication.com.piaoaihd.util.Constan.WEATHER_URL;


public class WeatherFragment extends BaseFragment {
    TextView weather_pm, weatherTv, temperature_tv, city_tv, weather_pm_tv, weather_pm2_tv, weather_pm2, weather_no2_tv, weather_pm10_tv, weather_so2_tv, weather_o3_tv, weather_co_tv;
    ImageView weather_iv;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    ProgressDialog progressDialog;
    Retrofit retrofit;
    Toastor toastor;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("数据查询中...");
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //获取一次定位结果
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setInterval(600000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(WEATHER_URL)
                .build();
        toastor = new Toastor(getActivity());
        progressDialog.show();

        weather_pm = (TextView) layout.findViewById(R.id.weather_pm);
        weather_pm_tv = (TextView) layout.findViewById(R.id.weather_pm_tv);
        weatherTv = (TextView) layout.findViewById(R.id.weatherTv);
        temperature_tv = (TextView) layout.findViewById(R.id.temperature_tv);
        city_tv = (TextView) layout.findViewById(R.id.city_tv);

        weather_pm2_tv = (TextView) layout.findViewById(R.id.weather_pm2_tv);
        weather_pm2 = (TextView) layout.findViewById(R.id.weather_pm2);
        weather_no2_tv = (TextView) layout.findViewById(R.id.weather_no2_tv);
        weather_pm10_tv = (TextView) layout.findViewById(R.id.weather_pm10_tv);
        weather_so2_tv = (TextView) layout.findViewById(R.id.weather_so2_tv);
        weather_o3_tv = (TextView) layout.findViewById(R.id.weather_o3_tv);
        weather_co_tv = (TextView) layout.findViewById(R.id.weather_co_tv);

        weather_iv = (ImageView) layout.findViewById(R.id.weather_iv);


        city_tv.setText("深圳");
        ServiceApi service = retrofit.create(ServiceApi.class);
        Call<Weather> call = service.getWeather("深圳", "1");
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                //请求成功操作
                Weather weather = response.body();
                Log.e("weather", weather.toString());
                if (weather.getShowapi_res_code() == 0) {
                    initWeather(weather);
                } else {
                    toastor.showSingletonToast("天气查询失败");
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                //请求失败操作
                progressDialog.dismiss();
                toastor.showSingletonToast("天气查询失败");
            }
        });

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_weather;
    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    Log.e("定位数据", aMapLocation.getCity());
                    city_tv.setText(aMapLocation.getCity());
                    ServiceApi service = retrofit.create(ServiceApi.class);
                    Call<Weather> call = service.getWeather(aMapLocation.getCity(), "1");
                    call.enqueue(new Callback<Weather>() {
                        @Override
                        public void onResponse(Call<Weather> call, Response<Weather> response) {
                            //请求成功操作
                            Weather weather = response.body();
                            Log.e("weather", weather.toString());
                            if (weather.getShowapi_res_code() == 0) {
                                initWeather(weather);
                            } else {
                                toastor.showSingletonToast("天气查询失败");
                                progressDialog.dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<Weather> call, Throwable t) {
                            //请求失败操作
                            progressDialog.dismiss();
                            toastor.showSingletonToast("天气查询失败");
                        }
                    });
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                    toastor.showSingletonToast("定位失败:" + aMapLocation.getErrorInfo());
                    progressDialog.dismiss();
                }
            }
        }
    };

    private void initWeather(Weather weather) {
        MyApplication.newInstance().getGlide().load(weather.getShowapi_res_body().getNow().getWeather_pic()).into(weather_iv);
        int pm = Integer.parseInt(weather.getShowapi_res_body().getNow().getAqiDetail().getPm2_5());
        weather_pm2_tv.setText(weather.getShowapi_res_body().getNow().getSd());
        weather_pm_tv.setText(weather.getShowapi_res_body().getNow().getAqiDetail().getPm2_5());
        BigDecimal b = new BigDecimal(weather.getShowapi_res_body().getNow().getAqiDetail().getCo());
        double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        weather_co_tv.setText(f1 + "");
        weather_no2_tv.setText(weather.getShowapi_res_body().getNow().getAqiDetail().getNo2());
        weather_pm10_tv.setText(weather.getShowapi_res_body().getNow().getAqiDetail().getPm10());
        weather_so2_tv.setText(weather.getShowapi_res_body().getNow().getAqiDetail().getSo2());
        weather_o3_tv.setText(weather.getShowapi_res_body().getNow().getAqiDetail().getO3());
        temperature_tv.setText(weather.getShowapi_res_body().getNow().getTemperature() + "℃");
        weatherTv.setText(weather.getShowapi_res_body().getNow().getWeather());
        if (pm >= 0 || pm <= 35) {
            weather_pm.setText("优");
            weather_pm2.setText("优");
        } else if (pm > 35 || pm <= 75) {
            weather_pm.setText("良");
            weather_pm2.setText("良");
            weather_pm.setBackground(getResources().getDrawable(R.drawable.pm_liang));
        } else if (pm > 75 || pm <= 115) {
            weather_pm.setText("轻度污染");
            weather_pm.setBackground(getResources().getDrawable(R.drawable.pm_qingdu));
        } else if (pm > 116 || pm <= 150) {
            weather_pm.setText("中度污染");
            weather_pm2.setText("中度污染");
            weather_pm.setBackground(getResources().getDrawable(R.drawable.pm_zhongdu));
        } else if (pm > 151 || pm <= 250) {

            weather_pm.setText("重度污染");
            weather_pm2.setText("重度污染");
            weather_pm.setBackground(getResources().getDrawable(R.drawable.pm_zhong));
        } else if (pm > 251 || pm <= 500) {
            weather_pm.setText("严重污染");
            weather_pm2.setText("严重污染");
            weather_pm.setBackground(getResources().getDrawable(R.drawable.pm_yanzhong));
        } else {
            weather_pm.setText("污染爆表");
            weather_pm2.setText("污染爆表");
            weather_pm.setBackground(getResources().getDrawable(R.drawable.pm_yanzhong));
        }
        progressDialog.dismiss();
    }
}
