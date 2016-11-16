package cn.zhudi.mvpdemo.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by mengyangyang on 2016-08-11.
 */
public class ToastSingle {
    private static HashMap<Context, Toast> toasts = new HashMap<Context, Toast>();

    /**
     * 土司
     *
     * @param context 上下文对象
     * @param content 内容（String）
     */
    public synchronized static void showToast(Context context, String content) {
        Toast toast = toasts.get(context);
        if (null == toast) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            toasts.put(context, toast);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setText(content);
        toast.show();
    }

    public static void showToast(Context context, Object obj, int time) {
        if (null != obj && null != context) {
            Toast toast = new Toast(context);
            toast.setDuration(time);
            toast.setGravity(Gravity.CENTER, 0, 0);
            TextView tv = new TextView(context);
            tv.setTextColor(Color.WHITE);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(Color.argb(200, 0, 0, 0));
            tv.setPadding(20, 10, 20, 10);
            tv.setText(obj.toString());
            toast.setView(tv);
            toast.show();
        }
    }

    public static void showShortToast(Context context, Object obj) {
        showToast(context, obj, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(Context context, Object obj) {
    }
}
