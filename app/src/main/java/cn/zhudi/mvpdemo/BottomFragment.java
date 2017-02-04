package cn.zhudi.mvpdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zhudi.mvpdemo.base.BaseFragment;

public class BottomFragment extends BaseFragment {
    @Bind({R.id.ivHome, R.id.ivCategory, R.id.ivFaxian, R.id.ivCar, R.id.ivPersonal})
    List<ImageView> tabImage;
    private int currentTab = 0;
    private int[] selectorImageId = new int[]{R.drawable.meun_home_selector, R.drawable.meun_category_selector, R.drawable.meun_faxian_selector, R.drawable.meun_car_selector, R.drawable.meun_personal_selector};
    private int[] normalImage = new int[]{R.drawable.ic_home_normal, R.drawable.ic_category_normal, R.drawable.ic_faxian_normal, R.drawable.ic_car_normal, R.drawable.ic_personal_normal};
    private int[] focusImage = new int[]{R.drawable.ic_home_focus, R.drawable.ic_category_focus, R.drawable.ic_faxian_focus, R.drawable.ic_car_focus, R.drawable.ic_personal_focus};

    private String[] noralNetImage = new String[]{"https://m.360buyimg.com/mobilecms/jfs/t3904/136/1433247296/6342/2628b8da/58775f09N723d8651.png",
            "https://m.360buyimg.com/mobilecms/jfs/t3856/272/1413133087/6107/dfed2992/58775f18Nb1b5ffae.png",
            "https://m.360buyimg.com/mobilecms/jfs/t3841/358/3156171195/16160/b21d7187/58788959Nbdc141f9.png",
            "https://m.360buyimg.com/mobilecms/jfs/t3160/198/5669519111/6612/c4b238d8/58775f33N9f758888.png",
            "https://m.360buyimg.com/mobilecms/jfs/t4000/157/1433886256/6541/66b99b41/58775f5fNc55fac11.png"};
    private String[] focusNetImage = new String[]{"https://m.360buyimg.com/mobilecms/jfs/t3214/314/5570923115/6344/69cdb335/58775f09Nf0bbd29c.png",
            "https://m.360buyimg.com/mobilecms/jfs/t3283/350/5647116711/6099/317617a3/58775f18N81e053dc.png",
            "https://m.360buyimg.com/mobilecms/jfs/t3097/352/5671690463/16717/6766b0fd/58788959N3f593b57.png",
            "https://m.360buyimg.com/mobilecms/jfs/t3988/159/1441680394/6611/d4d60964/58775f32Nb2a03583.png",
            "https://m.360buyimg.com/mobilecms/jfs/t3055/96/5677409281/6551/3cfe11c9/58775f5fN2fa311c4.png"};

    private List<String> noralNetList;
    private List<String> focusNetList;

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
        getData();
    }


    private void initView() {
        for (int i = 0; i < tabImage.size(); i++) {
            tabImage.get(i).setImageResource(selectorImageId[i]);
            if (i == currentTab) {
                tabImage.get(i).setSelected(true);
            } else {
                tabImage.get(i).setSelected(false);
            }
        }
    }

    private AsyncHttpClient mAsyncHttpClient;

    private void getData() {
        mAsyncHttpClient = new AsyncHttpClient();
        // 设置连接超时时间
        mAsyncHttpClient.setConnectTimeout(1500);
        // 设置最大连接数
        mAsyncHttpClient.setMaxConnections(1);
        // 设置重连次数以及间隔时间
        mAsyncHttpClient.setMaxRetriesAndTimeout(1, 1);
        // 设置响应超时时间
        mAsyncHttpClient.setResponseTimeout(1500);
        mAsyncHttpClient.get(getActivity(), "http://www.baidu.com", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                noralNetList = new ArrayList<>();
                focusNetList = new ArrayList<>();
                for (int i = 0; i < noralNetImage.length; i++) {
                    noralNetList.add(noralNetImage[i]);
                    focusNetList.add(focusNetImage[i]);
                }
                setNetImageView();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                noralNetList = null;
                focusNetList = null;
            }
        });
    }

    @OnClick({R.id.ivHome, R.id.ivCategory, R.id.ivFaxian, R.id.ivCar, R.id.ivPersonal})
    public void click(View view) {
        for (int i = 0; i < tabImage.size(); i++) {
            if (tabImage.get(i) == view) {
                currentTab = i;
                ((JDMainActivity) getActivity()).change(i);
            }
        }
        clickButtonView();
    }

    /**
     * 设置网络图片的底部按键
     */
    private void setNetImageView() {
        for (int i = 0; i < tabImage.size(); i++) {
            int resourceId;
            String showImageUrl;
            if (currentTab == i) {
                resourceId = focusImage[i];
                showImageUrl = focusNetList.get(i);
            } else {
                resourceId = normalImage[i];
                showImageUrl = noralNetList.get(i);
            }
            Glide.with(this)
                    .load(showImageUrl)
                    .placeholder(resourceId)
                    .error(resourceId)
                    .into(tabImage.get(i));
        }

    }

    /**
     * 改变按键的状态
     */
    private void clickButtonView() {
        if (noralNetList != null && focusNetList != null) {
            setNetImageView();
        } else {
            for (int i = 0; i < tabImage.size(); i++) {
                if (currentTab == i) {
                    tabImage.get(i).setSelected(true);
                } else {
                    tabImage.get(i).setSelected(false);
                }
            }
        }
    }
}
