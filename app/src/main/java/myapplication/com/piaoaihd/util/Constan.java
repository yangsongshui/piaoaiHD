package myapplication.com.piaoaihd.util;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import myapplication.com.piaoaihd.R;

/**
 * 描述：全局常量
 */
public class Constan {

    public static final String BASE_URL = "http://47.52.24.148:8080/t_user_app/";
    public static final String WEATHER_URL = "http://route.showapi.com/";
    public static final String ACTION_BLE_NOTIFY_DATA = "myapplication.com.piaoaihd.ACTION_BLE_NOTIFY_DATA";

    public static void TVOC(TextView textView, double tvoc, ImageView imageView) {
        if (tvoc >= 0 && tvoc <= 0.6) {
            textView.setText("良好");
            imageView.setImageResource(R.drawable.heng_1);
        } else if (tvoc > 0.6 && tvoc <= 1.0) {
            textView.setText("轻度污染");
            imageView.setImageResource(R.drawable.heng_2);
        } else if (tvoc > 1.0 && tvoc <= 1.6) {
            textView.setText("中度污染");
            imageView.setImageResource(R.drawable.heng_5);
        } else if (tvoc > 1.6) {
            textView.setText("重度污染");
            imageView.setImageResource(R.drawable.heng_7);
        }
    }
    public static void TVOC(TextView textView, double tvoc,LinearLayout linearLayout) {
        if (tvoc >= 0 && tvoc <= 0.6) {
            textView.setText("良好");
            linearLayout.setBackgroundResource(R.drawable.heng_1);
        } else if (tvoc > 0.6 && tvoc <= 1.0) {
            textView.setText("轻度污染");
            linearLayout.setBackgroundResource(R.drawable.heng_2);
        } else if (tvoc > 1.0 && tvoc <= 1.6) {
            textView.setText("中度污染");
            linearLayout.setBackgroundResource(R.drawable.heng_5);
        } else if (tvoc > 1.6) {
            textView.setText("重度污染");
            linearLayout.setBackgroundResource(R.drawable.heng_7);
        }
    }
    public static void PM2_5(TextView textView, double pm, LinearLayout linearLayout) {
        //Log.e("PM2_5", " " + pm);
        if (pm >= 0 && pm <= 35) {
            textView.setText("优");
            linearLayout.setBackgroundResource(R.drawable.heng_1);
        } else if (pm > 35 && pm <= 75) {
            textView.setText("良");
            linearLayout.setBackgroundResource(R.drawable.heng_2);
        } else if (pm > 75 && pm <= 115) {
            textView.setText("轻度污染");
            linearLayout.setBackgroundResource(R.drawable.heng_3);
        } else if (pm > 115 && pm <= 150) {
            textView.setText("中度污染");
            linearLayout.setBackgroundResource(R.drawable.heng_4);
        } else if (pm > 150 && pm <= 250) {
            textView.setText("重度污染");
            linearLayout.setBackgroundResource(R.drawable.heng_5);
        } else if (pm > 250) {
            textView.setText("严重污染");
            linearLayout.setBackgroundResource(R.drawable.heng_7);
        }
    }

    public static void PM10(TextView textView, double pm, ImageView imageView) {
        //Log.e("PM10", " " + pm);
        if (pm >= 0 && pm <= 50) {
            textView.setText("优");
            imageView.setImageResource(R.drawable.fang_1);
        } else if (pm > 51 && pm <= 100) {
            textView.setText("良");
            imageView.setImageResource(R.drawable.fang_2);
        } else if (pm > 100 && pm <= 200) {
            textView.setText("轻度污染");
            imageView.setImageResource(R.drawable.fang_3);
        } else if (pm > 200 && pm <= 300) {
            textView.setText("中度污染");
            imageView.setImageResource(R.drawable.fang_5);
        } else if (pm > 300) {
            textView.setText("重度污染");
            imageView.setImageResource(R.drawable.fang_7);
        }
    }

    public static void PM10(TextView textView, double pm, LinearLayout linearLayout) {
        //Log.e("PM10", " " + pm);
        if (pm >= 0 && pm <= 50) {
            textView.setText("优");
            linearLayout.setBackgroundResource(R.drawable.heng_1);
        } else if (pm > 51 && pm <= 100) {
            textView.setText("良");
            linearLayout.setBackgroundResource(R.drawable.heng_2);
        } else if (pm > 100 && pm <= 200) {
            textView.setText("轻度污染");
            linearLayout.setBackgroundResource(R.drawable.heng_3);
        } else if (pm > 200 && pm <= 300) {
            textView.setText("中度污染");
            linearLayout.setBackgroundResource(R.drawable.heng_5);
        } else if (pm > 300) {
            textView.setText("重度污染");
            linearLayout.setBackgroundResource(R.drawable.heng_7);
        }
    }
    public static void CO2(TextView textView, double co2, ImageView imageView) {
        if (co2 >= 0 && co2 <= 700) {
            textView.setText("清新");
            imageView.setImageResource(R.drawable.fang_1);
        } else if (co2 > 700 && co2 <= 1000) {
            textView.setText("较好");
            imageView.setImageResource(R.drawable.fang_2);
        } else if (co2 > 1000 && co2 <= 1500) {
            textView.setText("较浊");
            imageView.setImageResource(R.drawable.fang_5);
        } else if (co2 > 1500) {
            textView.setText("浑浊");
            imageView.setImageResource(R.drawable.fang_7);
        }
    }
    public static void CO2(TextView textView, double co2, LinearLayout linearLayout) {
        if (co2 >= 0 && co2 <= 700) {
            textView.setText("清新");
            linearLayout.setBackgroundResource(R.drawable.heng_1);
        } else if (co2 > 700 && co2 <= 1000) {
            textView.setText("较好");
            linearLayout.setBackgroundResource(R.drawable.heng_2);
        } else if (co2 > 1000 && co2 <= 1500) {
            textView.setText("较浊");
            linearLayout.setBackgroundResource(R.drawable.heng_5);
        } else if (co2 > 1500) {
            textView.setText("浑浊");
            linearLayout.setBackgroundResource(R.drawable.heng_7);
        }
    }
       public static void jiaquan(TextView textView, double jiaquan, ImageView imageView) {
        if (jiaquan >= 0 && jiaquan <= 0.03) {
            textView.setText("优");
            imageView.setImageResource(R.drawable.fang_1);
        } else if (jiaquan > 0.03 && jiaquan <= 0.1) {
            textView.setText("良");
            imageView.setImageResource(R.drawable.fang_2);
        } else if (jiaquan > 0.1 && jiaquan <= 0.2) {
            textView.setText("轻度污染");
            imageView.setImageResource(R.drawable.fang_3);
        } else if (jiaquan > 0.2 && jiaquan <= 0.3) {
            textView.setText("中度污染");
            imageView.setImageResource(R.drawable.fang_4);
        } else if (jiaquan > 0.3 && jiaquan <= 0.8) {
            textView.setText("重度污染");
            imageView.setImageResource(R.drawable.fang_5);
        } else if (jiaquan > 0.8) {
            textView.setText("严重污染");
            imageView.setImageResource(R.drawable.fang_7);
        }
    }

    public static void jiaquan(TextView textView, double jiaquan,  LinearLayout linearLayout) {
        if (jiaquan >= 0 && jiaquan <= 0.03) {
            textView.setText("优");
            linearLayout.setBackgroundResource(R.drawable.heng_1);
        } else if (jiaquan > 0.03 && jiaquan <= 0.1) {
            textView.setText("良");
            linearLayout.setBackgroundResource(R.drawable.heng_2);
        } else if (jiaquan > 0.1 && jiaquan <= 0.2) {
            textView.setText("轻度污染");
            linearLayout.setBackgroundResource(R.drawable.heng_3);
        } else if (jiaquan > 0.2 && jiaquan <= 0.3) {
            textView.setText("中度污染");
            linearLayout.setBackgroundResource(R.drawable.heng_4);
        } else if (jiaquan > 0.3 && jiaquan <= 0.8) {
            textView.setText("重度污染");
            linearLayout.setBackgroundResource(R.drawable.heng_5);
        } else if (jiaquan > 0.8) {
            textView.setText("严重污染");
            linearLayout.setBackgroundResource(R.drawable.heng_7);
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
