package cn.zhudi.mvpdemo.network;

/**
 * 类名称：android_mvp_demo
 * 类描述：
 * 创建人：zhudi
 * 创建时间：2016/11/16 13:29
 * 修改人：${user}
 * 修改时间：${date} ${time}
 * 修改备注：
 */
public class URLs {
    public final static String HTTP = "http://";
    public final static String HTTPS = "https://";

      public final static String HOST = "118.178.31.159/appcenter";//正式地址
//    public final static String HOST = "121.43.187.244/appcenter";//测试外网地址
    private final static String PATH = "/api";

    private final static String URL_API_HOST = HTTP + HOST + PATH;
    /**
     * 登录接口
     */
    public final static String LOGIN_URL = URL_API_HOST + "/passport/login/dms";
}
