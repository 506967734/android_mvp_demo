package cn.zhudi.mvpdemo.impl;

import com.google.gson.Gson;

import org.json.JSONException;

public interface JsonObjectParserInterface {
    static final Gson gson = new Gson();
    Object parse(String json) throws JSONException;
}
