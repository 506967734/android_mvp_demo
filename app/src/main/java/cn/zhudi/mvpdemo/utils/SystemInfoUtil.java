package cn.zhudi.mvpdemo.utils;

/**
 * Created by geely on 2015-09-28.
 */

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 获取硬件信息工具
 *
 * @author zhudi
 * @date 2015/09/28
 */
public class SystemInfoUtil {
    private final static String TAG = "ZhudiSystemInfoUtil";
    /**
     * mac 地址返回没有冒号
     */
    public final static int MAC_NO_COLON = 0;
    /**
     * mac 地址返回有冒号
     */
    public final static int MAC_HAVE_COLON = 1;
    /**
     * ip 地址返回无ip地址格式
     */
    public final static int IP_NO = 0;
    /**
     * mac 地址返回ip地址格式
     */
    public final static int IP_TES = 1;


    /**
     * 检查手机网络连接
     *
     * @param context
     * @return <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     */
    public static boolean checkNetWorkStatus(Context context) {
        boolean result;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnected()) {
            result = true;
            Log.i("NetStatus", "The net was connected");
        } else {
            result = false;
            Log.i("NetStatus", "The net was bad!");
        }
        return result;
    }

    /**
     * 检查手机网络状态
     *
     * @param context
     * @return {@link Boolean} true表示wifi false表示3g或没有连接
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     */
    public static boolean checkNetWorkType(Context context) {
        boolean result;
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            Log.i("NetStatus", "The net was connected");
            Log.i("NetStatus", String.valueOf(info.getType()));
            int type = info.getType();
            //0 手机网络，或者1 wifi
            if (type == 1) {
                return true;
            } else {
                return false;
            }
        } else {
            result = false;
            Log.i("NetStatus", "The net was bad!");
        }
        return result;
    }


    /**
     * 回到home，后台运行
     */
    public static void goHome(Context context) {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(mHomeIntent);
    }

    /**
     * 获取设备的可用内存大小
     *
     * @param context 应用上下文对象context
     * @return 当前内存大小
     */
    public static int getDeviceUsableMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        // 返回当前系统的可用内存
        return (int) (mi.availMem / (1024 * 1024));
    }

    /**
     * 获取设备mac地址
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     *
     * @param context 上下文对象
     * @param type    类型
     * @return {@link String} mac
     */
    public static String getMacAddress(Context context, int type) {
        String macAddress;
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        macAddress = info.getMacAddress();
        System.out.println("macAddress is null: " + (macAddress == null));
        if (null == macAddress) {
            return "";
        }
        if (type == MAC_NO_COLON) {
            macAddress = macAddress.replace(":", "");
        }
        return macAddress;
    }

    /**
     * 获取设备Ip地址
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     *
     * @param context 上下文对象
     * @param type    类型
     * @return {@link String} ip
     */
    public static String getIpAddress(Context context, int type) {
        String ipAddress;
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        ipAddress = String.valueOf(info.getIpAddress());
        if (null == ipAddress) {
            return "";
        }
        if (type == IP_TES) {
            ipAddress = intToIp(info.getIpAddress());
        }
        return ipAddress;
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    /**
     * 获取设备IMEI地址
     * 需要加入权限<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     *
     * @param context 上下文对象
     * @return {@link String} IMEI
     */
    public static String getDeviceImei(Context context) {
        String imei;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();
        if (null == imei) {
            return "";
        }
        return imei;
    }


    /**
     * 获取SIM卡的序列号
     *
     * @param context 上下文对象
     * @return {@link String} IMEI
     */
    public static String getDeviceSim(Context context) {
        String sim;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        sim = telephonyManager.getSimSerialNumber();
        if (null == sim) {
            return "";
        }
        return sim;
    }

    /**
     * 获取手机号
     *
     * @param context 上下文对象
     * @return {@link String} number
     */
    public static String getDeviceNumber(Context context) {
        String sim;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        sim = telephonyManager.getLine1Number();
        if (null == sim) {
            return "";
        }
        return sim;
    }

    /**
     * 获取设备操作系统的版本
     *
     * @param context 上下文对象
     * @return {@link String} Android Code
     */
    public static String getDeviceVersion(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = null;
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (Exception e) {
            return "1";
        }
    }


    /**
     * 获取设备操作系统的版本
     *
     * @param context 上下文对象
     * @return {@link String} Android Code
     */
    public static int getDeviceCode(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = null;
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * 获取设备型号
     *
     * @param context 上下文对象
     * @return {@link String} 手机型号
     */
    public static String getDeviceModel(Context context) {
        String model;
        model = android.os.Build.MODEL;
        if (null == model) {
            return "";
        }
        return model;
    }

    /**
     * 获取Android版本号
     *
     * @param context 上下文对象
     * @return {@link String} Android版本号
     */
    public static String getSystemVersion(Context context) {
        String version;
        version = android.os.Build.VERSION.RELEASE;
        if (null == version) {
            return "";
        }
        return version;
    }


    /**
     * 获取手机服务商信息
     * 需要加入权限<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     *
     * @param context 上下文对象
     * @return {@link String} 运营商
     */
    public static String getProvidersName(Context context) {
        String ProvidersName = null;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // 返回唯一的用户ID;就是这张卡的编号神马的
        String IMSI = telephonyManager.getSimOperator();
        // IMSI号前面3位460是国家，紧接着后面2位00 02 07是中国移动，01是中国联通，03是中国电信。
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
            ProvidersName = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            ProvidersName = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            ProvidersName = "中国电信";
        } else {
            ProvidersName = "未知";
        }
        return ProvidersName;
    }

}
