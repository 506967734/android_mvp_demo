package cn.zhudi.mvpdemo.impl;

public interface OnRequestListener<T> {
    void onSuccess(T data);
    void onFailed(String msg);
}
