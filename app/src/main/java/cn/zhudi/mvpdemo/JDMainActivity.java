package cn.zhudi.mvpdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class JDMainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jdmain);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.on, new BottomFragment());
        ft.commit();
    }
}
