package cn.zhudi.mvpdemo.mvp.presenter;

import android.content.Context;

import cn.zhudi.mvpdemo.base.BasePresenter;
import cn.zhudi.mvpdemo.mvp.view.MainView;
import cn.zhudi.mvpdemo.utils.SystemInfoUtil;

/**
 * 类描述：
 * 创建人：zhudi
 * 创建时间：2016/11/16 13:48
 */
public class MainPresenter extends BasePresenter<MainView> {
    private Context context;

    public MainPresenter(Context context) {
        this.context = context;
    }

    /**
     * 显示软件版本
     */
    public void setVersion(){
        mView.showVersion("软件版本" +  SystemInfoUtil.getDeviceVersion(context));

    }
}
