package cn.zhudi.mvpdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zhudi.mvpdemo.base.BaseFragment;
import cn.zhudi.mvpdemo.utils.DebugLog;

public class BottomFragment extends BaseFragment {
    @Bind({R.id.ivHome, R.id.ivCategory, R.id.ivFaxian, R.id.ivCar, R.id.ivPersonal})
    List<ImageView> tabImage;

    public BottomFragment newInstance() {
        return new BottomFragment();
    }


    protected int getLayoutView() {
        return R.layout.app_jd_navigation_xml;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
    }

    @OnClick({R.id.ivHome, R.id.ivCategory, R.id.ivFaxian, R.id.ivCar, R.id.ivPersonal})
    public void click(View view) {
        for (int i = 0; i < tabImage.size(); i++) {
            if (tabImage.get(i).getId() == view.getId()) {
                DebugLog.d(String.valueOf(i));
                ((JDMainActivity) getActivity()).change(i);
            }
        }
    }
}
