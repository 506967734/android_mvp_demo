package cn.zhudi.mvpdemo.entity;


import org.json.JSONException;

import cn.zhudi.mvpdemo.impl.JsonObjectParserInterface;

/**
 * 类名称：G_Assistant
 * 类描述：
 * 创建人：zhudi
 * 创建时间：16/9/2 14:58
 * 修改人：${user}
 * 修改时间：${date} ${time}
 * 修改备注：
 */
public class ModelParser implements JsonObjectParserInterface {
    private Class tclass;

    public ModelParser(Class tclass) {
        this.tclass = tclass;
    }

    @Override
    public Object parse(String json) throws JSONException {
        return gson.fromJson(json, tclass);
    }
}
