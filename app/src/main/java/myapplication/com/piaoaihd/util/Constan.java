package myapplication.com.piaoaihd.util;

import android.widget.TextView;

/**
 * 描述：全局常量
 */
public class Constan {

    public static final String BASE_URL = "http://47.52.24.148:8080/t_user_app/";
    public static final String WEATHER_URL = "http://route.showapi.com/";
    public static final String ACTION_BLE_NOTIFY_DATA = "myapplication.com.piaoaihd.ACTION_BLE_NOTIFY_DATA";

    public static void TVOC(TextView textView, double tvoc) {
        if (tvoc >= 0 && tvoc <= 60) {
            textView.setText("良好");
        } else if (tvoc > 60 && tvoc <= 100) {
            textView.setText("轻度污染");
        } else if (tvoc > 110 && tvoc <= 160) {
            textView.setText("中度污染");
        } else if (tvoc > 160) {
            textView.setText("重度污染");
        }
    }

    public static void PM2_5(TextView textView, double pm) {
        Log.e("PM2_5", " " + pm);
        if (pm >= 0 && pm <= 35) {
            textView.setText("优");
        } else if (pm > 35 && pm <= 75) {
            textView.setText("良");
        } else if (pm > 75 && pm <= 115) {
            textView.setText("轻度污染");
        } else if (pm > 116 && pm <= 150) {
            textView.setText("中度污染");
        } else if (pm > 151 && pm <= 250) {
            textView.setText("重度污染");
        } else if (pm > 251 && pm <= 500) {
            textView.setText("严重污染");
        } else {
            textView.setText("严重污染");
        }
    }

    public static void PM10(TextView textView, double pm) {
        Log.e("PM10", " " + pm);
        if (pm >= 0 && pm <= 50) {
            textView.setText("优");
        } else if (pm > 51 && pm <= 100) {
            textView.setText("良");
        } else if (pm > 100 && pm <= 150) {
            textView.setText("轻度污染");
        } else if (pm > 150 && pm <= 200) {
            textView.setText("中度污染");
        } else if (pm > 200) {
            textView.setText("重度污染");
        }
    }

    public static void CO2(TextView textView, double co2) {
        if (co2 >= 0 && co2 <= 485) {
            textView.setText("空气清新");
        } else if (co2 >= 486 && co2 <= 600) {
            textView.setText("优");
        } else if (co2 > 600 && co2 <= 800) {
            textView.setText("良");
        } else if (co2 > 800 && co2 <= 1000) {
            textView.setText("轻度污染");
        } else if (co2 > 1000 && co2 <= 1200) {
            textView.setText("中度污染");
        } else if (co2 > 1200 && co2 <= 1500) {
            textView.setText("重度污染");
        } else if (co2 > 1500) {
            textView.setText("严重污染");
        }
    }

    public static void jiaquan(TextView textView, double jiaquan) {
        if (jiaquan >= 0 && jiaquan <= 35) {
            textView.setText("优");
        } else if (jiaquan > 35 && jiaquan <= 75) {
            textView.setText("良");
        } else if (jiaquan > 75 && jiaquan <= 115) {
            textView.setText("轻度污染");
        } else if (jiaquan > 116 && jiaquan <= 150) {
            textView.setText("中度污染");
        } else if (jiaquan > 151 && jiaquan <= 250) {
            textView.setText("重度污染");
        } else if (jiaquan > 251 && jiaquan <= 500) {
            textView.setText("严重污染");
        } else {
            textView.setText("严重污染");
        }
    }

    public static void wendu(TextView textView, double wendu) {
        if (wendu <= -20) {
            textView.setText("极寒");
        } else if (wendu > -20 && wendu <= 0) {
            textView.setText("寒冷");
        } else if (wendu > 0 && wendu <= 10) {
            textView.setText("偏寒");
        } else if (wendu > 10 && wendu <= 15) {
            textView.setText("偏冷");
        } else if (wendu > 15 && wendu <= 22) {
            textView.setText("天凉");
        } else if (wendu >= 23 && wendu <= 28) {
            textView.setText("舒适");
        } else if (wendu > 28 && wendu <= 36) {
            textView.setText("炎热");
        } else if (wendu > 36 && wendu <= 39) {
            textView.setText("高温");
        } else if (wendu > 39) {
            textView.setText("炙热");

        }
    }

    public static void shidu(TextView textView, int shidu) {
        if (shidu >= 41 && shidu <= 60) {
            textView.setText("正常");

        } else if (shidu >= 0 && shidu <= 40) {
            textView.setText("干燥");

        } else if (shidu > 60 && shidu <= 100) {
            textView.setText("潮湿");

        }
    }
}
