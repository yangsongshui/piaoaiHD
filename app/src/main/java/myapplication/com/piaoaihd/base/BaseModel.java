package myapplication.com.piaoaihd.base;


import myapplication.com.piaoaihd.helper.RetrofitManager;

/**
 * 描述：业务对象的基类
 * 作者：dc on 2017/2/16 14:02
 * 邮箱：597210600@qq.com
 */
public class BaseModel {
    //retrofit请求数据的管理类
    public RetrofitManager retrofitManager;

    public BaseModel() {
        retrofitManager = RetrofitManager.builder();
    }
}
