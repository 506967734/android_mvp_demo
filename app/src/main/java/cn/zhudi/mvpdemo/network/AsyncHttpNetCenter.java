package cn.zhudi.mvpdemo.network;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;

import cn.zhudi.mvpdemo.utils.DebugLog;


/**
 * 基于AsyncHttp封装的网络访问类
 * 网络访问控制中心 用于统一管理网络访问及初始化网络相关配置
 *
 * @author Ht
 */
public class AsyncHttpNetCenter {

    public static final int GET = 1;
    public static final int POST = 2;
    // 连接超时时间
    static final int CONNECT_TIMEOUT = 10 * 1000;
    // 最大连接数
    static final int MAX_CONNECTIONS = 1;
    // 失败重连次数
    static final int MAX_RETRIES = 1;
    // 失败重连间隔时间
    static final int RETRIES_TIMEOUT = 1;
    // 响应超时时间
    static final int RESPONSE_TIMEOUT = 10 * 1000;
    // 默认Content-Type
    public static final String DEFAULT_CONTENT_TYPE = "application/json";

    private static AsyncHttpNetCenter instance;
    private AsyncHttpClient mAsyncHttpClient;

    private AsyncHttpNetCenter() {
        initHttpClient();
    }

    public static AsyncHttpNetCenter getInstance() {
        if (instance == null) {
            instance = new AsyncHttpNetCenter();
            DebugLog.d("AsyncHttpNetCenter-----getInstance()");
        }
        return instance;
    }

    void initHttpClient() {
        mAsyncHttpClient = new AsyncHttpClient();
        // 设置连接超时时间
        mAsyncHttpClient.setConnectTimeout(CONNECT_TIMEOUT);
        // 设置最大连接数
        mAsyncHttpClient.setMaxConnections(MAX_CONNECTIONS);
        // 设置重连次数以及间隔时间
        mAsyncHttpClient.setMaxRetriesAndTimeout(MAX_RETRIES, RETRIES_TIMEOUT);
        // 设置响应超时时间
        mAsyncHttpClient.setResponseTimeout(RESPONSE_TIMEOUT);
        DebugLog.d("mAsyncHttpClient" + mAsyncHttpClient);
    }

    /**
     * 以key,value设置一个请求头
     *
     * @param header
     * @param value
     */
    public void setHeader(String header, String value) {
        DebugLog.d("gtoken------->" + value);
        mAsyncHttpClient.addHeader(header, value);
    }

//    /**
//     * 设置预设的所有请求头
//     */
//    private void insertAllHeaders() {
//        Set<String> headerKey = baseHeader.keySet();
//        Iterator<String> iterator = headerKey.iterator();
//        while (iterator.hasNext()) {
//            String key = iterator.next();
//            String value = baseHeader.get(key);
//
//            mAsyncHttpClient.addHeader(key, value);
//        }
//    }

    /**
     * 移除指定key的请求头
     *
     * @param header
     */
    public void removeHeader(String header) {
        mAsyncHttpClient.removeHeader(header);
    }

//    /**
//     * 移除所有请求头
//     */
//    void removeAllHeaders() {
//        baseHeader.clear();
//        mAsyncHttpClient.removeAllHeaders();
//    }

    /**
     * 发起可设置请求参数的网络请求(RequestParams形式)
     *
     * @param context
     * @param type
     * @param url
     * @param requestParams
     * @param responseHandler
     */
    public void sendRequest(Context context, int type,
                            String url, RequestParams requestParams, AsyncHttpResponseHandler responseHandler) {

        sendRequest(context, type, url, requestParams, null, null, responseHandler);
    }


    /**
     * 发起可设置请求参数的网络请求(直接设置请求体形式)
     *
     * @param context
     * @param type
     * @param url
     * @param entity
     * @param contentType
     * @param responseHandler
     */
    public void sendRequest(Context context, int type,
                            String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {

        sendRequest(context, type, url, null, entity, contentType, responseHandler);
    }

    /**
     * 发起网络请求
     *
     * @param context
     * @param type
     * @param url
     * @param requestParams
     * @param entity
     * @param contentType
     * @param responseHandler
     */
    void sendRequest(Context context, int type,
                     String url, RequestParams requestParams, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {

        if (requestParams != null) {
            // 根据传入类型调用不同请求方法,可自行扩展
            // 传入Context以便与生命周期联动
            switch (type) {
                case GET:
                    // 发起get请求,获取请求处理器
                    mAsyncHttpClient.get(context, url, requestParams, responseHandler);
                    break;
                case POST:
                    // 发起post请求,获取请求处理器
                    mAsyncHttpClient.post(context, url, requestParams, responseHandler);
                    break;
                default:
                    // 默认发起get请求
                    mAsyncHttpClient.get(context, url, requestParams, responseHandler);
                    break;
            }
        } else if (entity != null) {
            // 根据传入类型调用不同请求方法,可自行扩展
            // 传入Context以便与生命周期联动
            switch (type) {
                case GET:
                    // 发起get请求,获取请求处理器
                    mAsyncHttpClient.get(context, url, entity, contentType, responseHandler);
                    break;
                case POST:
                    // 发起post请求,获取请求处理器
                    mAsyncHttpClient.post(context, url, entity, contentType, responseHandler);
                    break;
                default:
                    // 默认发起get请求
                    mAsyncHttpClient.get(context, url, entity, contentType, responseHandler);
                    break;
            }
        } else {
            // 根据传入类型调用不同请求方法,可自行扩展
            // 传入Context以便与生命周期联动
            switch (type) {
                case GET:
                    // 发起get请求,获取请求处理器
                    mAsyncHttpClient.get(context, url, entity, contentType, responseHandler);
                    break;
                case POST:
                    // 发起post请求,获取请求处理器
                    mAsyncHttpClient.post(context, url, entity, contentType, responseHandler);
                    break;
                default:
                    // 默认发起get请求
                    mAsyncHttpClient.get(context, url, entity, contentType, responseHandler);
                    break;
            }
        }

    }

    /**
     * 销毁只能Context的请求
     *
     * @param context Activity
     */
    public void clearRequestQueue(Context context) {
        // 销毁指定Context的请求, 第二个参数true代表强制结束
        try {
            mAsyncHttpClient.cancelRequests(context, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 销毁所有的请求
     */
    public void clearAllRequestQueue() {
        try {
            mAsyncHttpClient.cancelAllRequests(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
