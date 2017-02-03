package cn.zhudi.mvpdemo;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import cn.zhudi.mvpdemo.utils.DebugLog;

public class JDMainActivity extends FragmentActivity {
     final String TAG = JDMainActivity.class.getName();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jdmain);
//        GlideBuilder builder = new GlideBuilder(this);
//        builder.setDiskCache(new DiskLruCacheFactory());
//
//        Glide.with(this).setBu();
        DebugLog.d("asdf");
        Log.v(TAG, Environment.getExternalStorageDirectory().getAbsolutePath());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.on, new BottomFragment());
        ft.commit();

//        Log.v(TAG, Environment.getExternalStorageDirectory().getAbsolutePath());
//        Log.v(TAG, StorageUtils.getCacheDirectory(this).getAbsolutePath());
//        Log.v(TAG, getCacheDir().getPath());
//        String dir = getCacheDir().getPath() + "/zhudi";
//        File destDir = new File(dir);
//        if (!destDir.exists()) {
//            destDir.mkdirs();
//        }
        Log.v(TAG,getFilesDir().getPath());
//        Log.v(TAG,getDir("files", Context.MODE_PRIVATE).getPath());
//        getDir("files", Context.MODE_PRIVATE);
    }
}
