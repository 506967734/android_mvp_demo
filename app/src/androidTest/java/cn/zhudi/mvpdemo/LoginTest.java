package cn.zhudi.mvpdemo;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

import cn.zhudi.mvpdemo.ui.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * 类名称：android_mvp_demo
 * 类描述：
 * 创建人：zhudi
 * 创建时间：2016/11/24 16:53
 * 修改人：${user}
 * 修改时间：${date} ${time}
 * 修改备注：
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @org.junit.Test
    public void getUserName() throws Exception {
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.etUserName)).perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
//        onView(withId(R.id.etUserName)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.etUserPassword)).perform(typeText("1"), closeSoftKeyboard());
//        onView(withId(R.id.etUserName)).perform(typeText("zhudi"));
        onView(withId(R.id.btnLogin)).perform(click());
        // onView(withId(R.id.tvShow)).check(matches(withText("success")));
//        onView(withId(R.id.etUserName)).check(matches(withText("登录")));
    }
}
