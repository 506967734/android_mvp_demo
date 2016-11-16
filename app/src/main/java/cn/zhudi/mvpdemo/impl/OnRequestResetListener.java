package cn.zhudi.mvpdemo.impl;

public interface OnRequestResetListener<T> extends OnRequestListener<T> {
    void reset();
}
