package cn.zhudi.mvpdemo.impl;

public interface OnRequestListener<T> {
    void onSuccess(T data);
    void onFailed(int code,String msg);
}
