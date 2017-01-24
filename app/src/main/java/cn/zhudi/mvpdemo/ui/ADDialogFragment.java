package cn.zhudi.mvpdemo.ui;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import cn.zhudi.mvpdemo.R;

/**
 * 类名称：android_mvp_demo
 * 类描述：
 * 创建人：zhudi
 * 创建时间：2016/12/21 10:23
 * 修改人：${user}
 * 修改时间：${date} ${time}
 * 修改备注：
 */
public class ADDialogFragment extends DialogFragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加这句话去掉自带的标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialogfragment_person, null);
        //init(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        //设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public interface OnDialogListener {
        void onDialogClick(String person);
    }
    public void setOnDialogListener(OnDialogListener dialogListener){
        this.mlistener = dialogListener;
    }


    public OnDialogListener mlistener;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                mlistener.onDialogClick("1");
                dismiss();
                break;
            case R.id.tv2:
                mlistener.onDialogClick("2");
                dismiss();
                break;
            case R.id.tv3:
                mlistener.onDialogClick("3");
                dismiss();
                break;
            case R.id.tv4:
                mlistener.onDialogClick("4");
                dismiss();
                break;
        }
    }
}
