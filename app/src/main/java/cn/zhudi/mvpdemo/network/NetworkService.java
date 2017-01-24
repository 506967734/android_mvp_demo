package cn.zhudi.mvpdemo.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import cn.zhudi.mvpdemo.impl.JsonObjectParserInterface;
import cn.zhudi.mvpdemo.utils.DebugLog;


/**
 * 网络请求服务
 *
 * @param <T>
 * @author zhudi
 * @date 2015/09/28
 */
public class NetworkService<T> {
    // 默认Content-Type
    public static final String DEFAULT_CONTENT_TYPE = "application/json;charset=utf-8";
    private JsonObjectParserInterface jsonParser = null;
    private ProgressDialog pd = null;
    /**
     * 服务器错误的返回code全部小于0
     */
    public static final int ERROR_CODO = -1;
    /**
     * 服务器返回数据出错
     */
    private static final String NETWORKDATA_MSG = "服务器错误";
    /**
     * 数据解析错误提示内容
     */
    private static final String ERRORJSON_MSG = "数据解析错误";
    /**
     * URL错误提示内容
     */
    private static final String ERRORURL_MSG = "URL错误";
    private NetworkListener<T> listener;
    /**
     * 是否显示遮罩层
     */
    private boolean needValidate = true;
    private String actionName;
    private Context mContext;

    /**
     * 初始化
     *
     * @param actionId 方法名
     * @param listener
     */
    public NetworkService(String actionId, NetworkListener<T> listener) {
        actionName = actionId;
        this.listener = listener;
    }

    // 公有方法-------------------------------------------------------------------------------------------
    public interface NetworkListener<T> {
        void onReceiveResult(T result);

        void onFailure(int code, String msg);

        void onFinish();

        void reLoginResult();
    }

    /**
     * 显示进度条
     *
     * @param b
     */
    public void configNeedValidate(boolean b) {
        needValidate = b;
    }


    /**
     * 设置解析类
     *
     * @param jsonParser 解析类
     */
    public void configAnalyze(JsonObjectParserInterface jsonParser) {
        this.jsonParser = jsonParser;
    }

    /**
     * 设置上下文对象
     *
     * @param context
     */
    public void configContext(Context context) {
        mContext = context;
    }


    /**
     * post提交
     *
     * @param url
     * @param params 请求参数
     */
    public void postSubmit(String url, RequestParams params) {
        send(url, params, null, 2);
    }

    /**
     * post提交
     *
     * @param url
     * @param params 请求参数
     */
    public void postSubmit(String url, RequestParams params, HttpEntity entiy) {
        send(url, params, entiy, 2);
    }

    /**
     * get提交
     *
     * @param url
     */
    public void getSubmit(String url) {
        send(url, null, null, 1);
    }

    private void send(String url, RequestParams params, HttpEntity entity, int type) {
        // 判断网络是否连接
//        if (!SystemInfoUtil.checkNetWorkStatus(MainApplication.context)) {
//            handleErrorResult(ERROR_CODO, mContext.getString(R.string.data_load_fail));
//            handleFinishResult();
//            return;
//        }
        if (url == null) {
            handleErrorResult(0, ERRORURL_MSG);
            return;
        }
        if (needValidate && mContext instanceof Activity && !((Activity) mContext).isFinishing()) {
            showLoadingDialog();
        }
//        if (TextUtils.isEmpty(actionName)) {
//            AsyncHttpNetCenter.getInstance().setHeader("gtoken", SharedPreferenceUtil.getSharedPreferences(mContext, Variables.TOKEN, Variables.TOKEN));
//        } else {
//            AsyncHttpNetCenter.getInstance().removeHeader("gtoken");
//        }
        DebugLog.d(url + (params != null ? params.toString() : ""));
        if (params != null) {
            AsyncHttpNetCenter.getInstance().sendRequest(mContext, 2, url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    handleBaseResult(new String(responseBody));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable exception) {
                    failureResult(statusCode, exception);
                }

                @Override
                public void onFinish() {
                    dismissPd();
                    handleFinishResult();
                }
            });
        } else if (entity != null) {
            AsyncHttpNetCenter.getInstance().sendRequest(mContext, 2, url, entity, DEFAULT_CONTENT_TYPE, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    handleBaseResult(new String(responseBody));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable exception) {
                    failureResult(statusCode, exception);
                }

                @Override
                public void onFinish() {
                    dismissPd();
                    handleFinishResult();
                }
            });
        } else {
            AsyncHttpNetCenter.getInstance().sendRequest(mContext, 1, url, null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    handleBaseResult(new String(responseBody));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable exception) {
                    failureResult(statusCode, exception);
                }

                @Override
                public void onFinish() {
                    dismissPd();
                    handleFinishResult();
                }
            });
        }
    }

    /**
     * 处理错误的返回
     *
     * @param statusCode 错误码
     * @param exception  错误信息
     */
    private void failureResult(int statusCode, Throwable exception) {
        int exceptionCode = statusCode;
        Throwable cause = exception;
        String errorMessage = "";
        if (cause instanceof SocketTimeoutException) {
            errorMessage = "连接超时";
        } else if (cause instanceof TimeoutException) {
            errorMessage = "连接超时";
        } else if (cause instanceof ConnectException) {
            errorMessage = "连接失败";
        } else if (exceptionCode == 500) {
            errorMessage = "服务器内部错误";
        } else if (exceptionCode == 404) {
            errorMessage = "连接地址未找到，请联系管理员检查服务是否启动";
        } else if (cause instanceof IOException) {
            errorMessage = "IO异常,请检查主机IP是否正确";
        } else {
            errorMessage = "未知错误";
        }
        handleErrorResult(ERROR_CODO, errorMessage);
    }


    /**
     * 处理返回字符串
     *
     * @param result
     */
    private void handleBaseResult(String result) {
        DebugLog.d(result);
        try {
            JSONObject root = new JSONObject(result);
            if (root.getInt("status") == 0) {
                if (root.has("data")) {
                    try {
                        handleResultBody(root.getString("data"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //数据解析错误
                        handleErrorResult(ERROR_CODO, ERRORJSON_MSG);
                    }
                }
            } else {
                if (root.has("errCode")) {
                    String errorCode;
                    try {
                        errorCode = root.getString("errCode");
//                        if (errorCode.equals(Constants.INVALID_TOKEN) || errorCode.equals(Constants.NULL_TOKEN)) {
//                            //INVALID_TOKEN表示TOKEN失效  NULL_TOKEN表示TOKEN为空
//                            reLogin();
//                            return;
//                        } else if (errorCode.equals(Constants.PRODUCER_TOKEN)) {
//                            if (mContext != null) {
//                                goLoginActivity();
//                            }
//                            return;
//                        } else if (errorCode.equals(Constants.NOT_AUTHORIZE_USE)) {
//                            String content = root.getString("msg");
//                            handleErrorResult(Constants.NOT_AUTHORIZE_USE_CODE, TextUtils.isEmpty(content) ? NETWORKDATA_MSG : content);
//                        } else if (root.has("data")) {
                            String content = root.getString("msg");
                            handleErrorResult(root.getInt("status"), TextUtils.isEmpty(content) ? NETWORKDATA_MSG : content);
//                        } else {
//                            handleErrorResult(ERROR_CODO, NETWORKDATA_MSG); //服务器错误
//                        }
                    } catch (JSONException e) {
                        handleErrorResult(ERROR_CODO, ERRORJSON_MSG); //数据解析错误
                    }
                } else {
                    handleErrorResult(ERROR_CODO, NETWORKDATA_MSG);   //服务器错误
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handleErrorResult(ERROR_CODO, ERRORJSON_MSG); //数据解析错误
        }
    }

    /**
     * 返回登录界面
     */
    private void goLoginActivity() {
//        if (mContext != null) {
//            AsyncHttpNetCenter.getInstance().clearAllRequestQueue();
//            ToastSingle.showToast(mContext, mContext.getString(R.string.logout));
//            SharedPreferenceUtil.clearSharedPreferences(mContext, Variables.TOKEN);
//            Intent intent = new Intent(mContext, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            mContext.startActivity(intent);
//        }
    }


    /**
     * 解析字符串
     *
     * @param data 解析的数据
     */
    private void handleResultBody(String data) throws JSONException {

        if (listener != null) {
            if (jsonParser != null) {
                if (data.equals("null")) {
                    listener.onReceiveResult(null);
                } else {
                    try {
                        listener.onReceiveResult((T) jsonParser.parse(data));
                    } catch (JsonSyntaxException e) {
                        handleErrorResult(ERROR_CODO, ERRORJSON_MSG); //数据解析错误
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 处理错误结果
     *
     * @param code    错误码
     * @param message 错误提示信息
     */
    private void handleErrorResult(int code, String message) {
        try {
            if (listener != null && mContext != null) {
                listener.onFailure(code, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理完成的返回
     */
    private void handleFinishResult() {
        try {
            if (listener != null && mContext != null) {
                listener.onFinish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理完成的返回
     */
    private void handleReLoginResult() {
        try {
            if (listener != null && mContext != null) {
                listener.reLoginResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 显示加载Dialog
     */
    private void showLoadingDialog() {
        try {
            pd = ProgressDialog.show(mContext, null, "请稍等...");
            pd.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dismissPd();
                        AsyncHttpNetCenter.getInstance().clearRequestQueue(mContext);
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 取消进度的显示
     */
    private void dismissPd() {
        try {
            if (pd != null && mContext != null) {
                pd.dismiss();
                pd = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    /**
//     * 调用免登录接口
//     */
//    private void reLogin() {
//        String url = URLs.RELOGIN_URL;
//        RequestParams params = new RequestParams();
//        params.add("companyId", SharedPreferenceUtil.getSharedPreferences(mContext, Variables.USER_TABLE, Variables.USER_COMPANY));
//        params.add("userCode", SharedPreferenceUtil.getSharedPreferences(mContext, Variables.USER_TABLE, Variables.USER_CODE));
//        params.add("stub", SharedPreferenceUtil.getSharedPreferences(mContext, Variables.USER_TABLE, Variables.STUB));
//        DebugLog.d("免登陆接口开始请求-------->" + params.toString());
//        AsyncHttpNetCenter.getInstance().sendRequest(mContext, 2, url, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                DebugLog.d("免登陆接口返回数据-------->" + new String(responseBody));
//                try {
//                    JSONObject root = new JSONObject(new String(responseBody));
//                    if (root.getInt("status") == 0) {
//                        if (root.has("data")) {
//                            try {
//                                User user = (User) new ModelParser(User.class).parse(root.getString("data"));
//                                if (user == null || TextUtils.isEmpty(user.getToken())) {
//                                    goLoginActivity();
//                                } else {
//                                    SharedPreferenceUtil.setSharedPreferences(mContext, Variables.TOKEN, Variables.TOKEN, user.getToken());
//                                    //原来的数据重新请求
//                                    DebugLog.d("免登陆接口返回成功");
//                                    handleReLoginResult();
//                                }
//                            } catch (JSONException e) {
//                                goLoginActivity();
//                            }
//                        } else {
//                            goLoginActivity();
//                        }
//                    } else {
//                        goLoginActivity();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    goLoginActivity();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                goLoginActivity();
//            }
//        });
//        //requestHandles.add(rh);
//    }
}
