/*
 * Copyright 2016 czy1121
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zd.layout;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


public class LoadingLayout extends FrameLayout {
    /**
     * 提示文字的字体颜色
     */
    private final int HINTTEXTCOLOR = 0xff999999;
    /**
     * 提示文字的字体大小
     */
    private final int HINTTEXTSIZE = 14;
    /**
     * 加载错误按键文字的字体颜色
     */
    private final int BUTTONTEXTCOLOR = 0xff999999;
    /**
     * 提示文字的字体大小
     */
    private final int BUTTONTEXTSIZE = 12;


    public interface OnInflateListener {
        void onInflate(View inflated);
    }

    public static LoadingLayout wrap(Activity activity) {
        return wrap(((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0));
    }

    public static LoadingLayout wrap(Fragment fragment) {
        return wrap(fragment.getView());
    }

    public static LoadingLayout wrap(View view) {
        if (view == null) {
            throw new RuntimeException("content view can not be null");
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent == null) {
            throw new RuntimeException("parent view can not be null");
        }
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        int index = parent.indexOfChild(view);
        parent.removeView(view);

        LoadingLayout layout = new LoadingLayout(view.getContext());
        parent.addView(layout, index, lp);
        layout.addView(view);
        layout.setContentView(view);
        return layout;
    }

    int mEmptyImage;
    CharSequence mEmptyText;

    int mErrorImage;
    View.OnClickListener mRetryListener;

    int mLoadingProgressBar;
    /**
     * retry以后是否显示加载
     */
    boolean needShowLoading;
    CharSequence mErrorText, mRetryText;
    View.OnClickListener mRetryButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mRetryListener != null) {
                mRetryListener.onClick(v);
                if (needShowLoading) {
                    showLoading();
                }
            }
        }
    };


    OnInflateListener mOnEmptyInflateListener;
    OnInflateListener mOnErrorInflateListener;

    int mTextColor, mTextSize;
    int mButtonTextColor, mButtonTextSize;
    int mButtonBackground = NO_ID;
    int mEmptyResId = NO_ID, mLoadingResId = NO_ID, mErrorResId = NO_ID;
    int mContentId = NO_ID;

    Map<Integer, View> mLayouts = new HashMap<>();


    public LoadingLayout(Context context) {
        this(context, null, R.attr.styleLoadingLayout);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.styleLoadingLayout);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mInflater = LayoutInflater.from(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout, defStyleAttr, R.style.LoadingLayout_Style);
        /**
         * 空的布局
         */
        mEmptyImage = a.getResourceId(R.styleable.LoadingLayout_llEmptyImage, NO_ID);
        mEmptyText = a.getString(R.styleable.LoadingLayout_llEmptyText);
        /**
         * 错误的布局
         */
        mErrorImage = a.getResourceId(R.styleable.LoadingLayout_llErrorImage, NO_ID);
        mErrorText = a.getString(R.styleable.LoadingLayout_llErrorText);
        mRetryText = a.getString(R.styleable.LoadingLayout_llRetryText);

        mLoadingProgressBar = a.getResourceId(R.styleable.LoadingLayout_llLoadingResId, NO_ID);

        /**
         * 提示文字的属性
         */
        mTextColor = a.getColor(R.styleable.LoadingLayout_llTextColor, HINTTEXTCOLOR);
        mTextSize = a.getDimensionPixelSize(R.styleable.LoadingLayout_llTextSize, dp2px(HINTTEXTSIZE));

        /**
         * 按键的属性设置
         */
        mButtonTextColor = a.getColor(R.styleable.LoadingLayout_llButtonTextColor, BUTTONTEXTCOLOR);
        mButtonTextSize = a.getDimensionPixelSize(R.styleable.LoadingLayout_llButtonTextSize, dp2px(BUTTONTEXTSIZE));
        mButtonBackground = a.getResourceId(R.styleable.LoadingLayout_llButtonBackground ,NO_ID);

        mEmptyResId = a.getResourceId(R.styleable.LoadingLayout_llEmptyResId, R.layout._loading_layout_empty);
        mLoadingResId = a.getResourceId(R.styleable.LoadingLayout_llLoadingResId, R.layout._loading_layout_loading);
        mErrorResId = a.getResourceId(R.styleable.LoadingLayout_llErrorResId, R.layout._loading_layout_error);

        a.recycle();
    }

    int dp2px(float dp) {
        return (int) (getResources().getDisplayMetrics().density * dp);
    }


    LayoutInflater mInflater;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0) {
            return;
        }
        if (getChildCount() > 1) {
            removeViews(1, getChildCount() - 1);
        }
        View view = getChildAt(0);
        setContentView(view);
        showLoading();
    }

    private void setContentView(View view) {
        mContentId = view.getId();
        mLayouts.put(mContentId, view);
    }

    public LoadingLayout setLoading(@LayoutRes int id) {
        if (mLoadingResId != id) {
            remove(mLoadingResId);
            mLoadingResId = id;
        }
        return this;
    }


    public LoadingLayout setEmpty(@LayoutRes int id) {
        if (mEmptyResId != id) {
            remove(mEmptyResId);
            mEmptyResId = id;
        }
        return this;
    }

    public LoadingLayout setOnEmptyInflateListener(OnInflateListener listener) {
        mOnEmptyInflateListener = listener;
        if (mOnEmptyInflateListener != null && mLayouts.containsKey(mEmptyResId)) {
            listener.onInflate(mLayouts.get(mEmptyResId));
        }
        return this;
    }

    public LoadingLayout setOnErrorInflateListener(OnInflateListener listener) {
        mOnErrorInflateListener = listener;
        if (mOnErrorInflateListener != null && mLayouts.containsKey(mErrorResId)) {
            listener.onInflate(mLayouts.get(mErrorResId));
        }
        return this;
    }

    /**
     * 设置加载进度框样式
     * @param resId 样式资源
     * @return View
     */
    public LoadingLayout setLoadingProgressBar(@DrawableRes int resId){
        mLoadingProgressBar = resId;
        progressBar(mLoadingResId,R.id.loading_progressBar,mLoadingProgressBar);
        return this;
    }

    /**
     * 设置空界面的图片
     * @param resId 图片资源Id
     * @return View
     */
    public LoadingLayout setEmptyImage(@DrawableRes int resId) {
        mEmptyImage = resId;
        image(mEmptyResId, R.id.empty_image, mEmptyImage);
        return this;
    }

    /**
     * 设置空界面的提示文字
     * @param value 文字
     * @return View
     */
    public LoadingLayout setEmptyText(String value) {
        mEmptyText = value;
        text(mEmptyResId, R.id.empty_text, mEmptyText);
        return this;
    }

    /**
     * 设置出现错误时候图片
     * @param resId 图片资源Id
     * @return View
     */
    public LoadingLayout setErrorImage(@DrawableRes int resId) {
        mErrorImage = resId;
        image(mErrorResId, R.id.error_image, mErrorImage);
        return this;
    }

    /**
     * 设置出现错误提示文字
     * @param value 文字
     * @return View
     */
    public LoadingLayout setErrorText(String value) {
        mErrorText = value;
        text(mErrorResId, R.id.error_text, mErrorText);
        return this;
    }

    /**
     * 设置出现错误点击按键的提示文字
     * @param text 文字
     * @return View
     */
    public LoadingLayout setRetryText(String text) {
        mRetryText = text;
        text(mErrorResId, R.id.retry_button, mRetryText);
        return this;
    }

    /**
     * 设置出现错误点击事件监听
     * @param listener
     * @param needShowLoading 是否点击以后显示加载进度
     * @return View
     */
    public LoadingLayout setRetryListener(OnClickListener listener, boolean needShowLoading) {
        mRetryListener = listener;
        this.needShowLoading = needShowLoading;
        return this;
    }

    public void showLoading() {
        show(mLoadingResId);
    }

    public void showEmpty() {
        show(mEmptyResId);
    }

    public void showError() {
        show(mErrorResId);
    }

    public void showContent() {
        show(mContentId);
    }

    private void show(int layoutId) {
        for (View view : mLayouts.values()) {
            view.setVisibility(GONE);
        }
        layout(layoutId).setVisibility(VISIBLE);
    }

    private void remove(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            View vg = mLayouts.remove(layoutId);
            removeView(vg);
        }
    }

    private View layout(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            return mLayouts.get(layoutId);
        }
        View layout = mInflater.inflate(layoutId, this, false);
        layout.setVisibility(GONE);
        addView(layout);
        mLayouts.put(layoutId, layout);

        if (layoutId == mEmptyResId) {
            ImageView img = (ImageView) layout.findViewById(R.id.empty_image);
            if (img != null) {
                img.setImageResource(mEmptyImage);
            }
            TextView view = (TextView) layout.findViewById(R.id.empty_text);
            if (view != null) {
                view.setText(mEmptyText);
                view.setTextColor(mTextColor);
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            }
            if (mOnEmptyInflateListener != null) {
                mOnEmptyInflateListener.onInflate(layout);
            }
        } else if (layoutId == mErrorResId) {
            ImageView img = (ImageView) layout.findViewById(R.id.error_image);
            if (img != null) {
                img.setImageResource(mErrorImage);
            }
            TextView txt = (TextView) layout.findViewById(R.id.error_text);
            if (txt != null) {
                txt.setText(mErrorText);
                txt.setTextColor(mTextColor);
                txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            }
            TextView btn = (TextView) layout.findViewById(R.id.retry_button);
            if (btn != null) {
                btn.setText(mRetryText);
                btn.setTextColor(mButtonTextColor);
                btn.setTextSize(TypedValue.COMPLEX_UNIT_PX, mButtonTextSize);
                //btn.setBackground(mButtonBackground);
                if (mButtonBackground != NO_ID) {
                    btn.setBackgroundResource(mButtonBackground);
                }
                btn.setOnClickListener(mRetryButtonClickListener);
            }
            if (mOnErrorInflateListener != null) {
                mOnErrorInflateListener.onInflate(layout);
            }
        }
        return layout;
    }

    /**
     * 显示textview
     * @param layoutId 元素
     * @param ctrlId 显示的控件id
     * @param value  内容
     */
    private void text(int layoutId, int ctrlId, CharSequence value) {
        if (mLayouts.containsKey(layoutId)) {
            TextView view = (TextView) mLayouts.get(layoutId).findViewById(ctrlId);
            if (view != null) {
                view.setText(value);
            }
        }
    }

    /**
     * 显示image
     * @param layoutId 元素
     * @param ctrlId 显示的控件id
     * @param resId 资源id
     */
    private void image(int layoutId, int ctrlId, int resId) {
        if (mLayouts.containsKey(layoutId)) {
            ImageView view = (ImageView) mLayouts.get(layoutId).findViewById(ctrlId);
            if (view != null) {
                view.setImageResource(resId);
            }
        }
    }

    /**
     * 显示ProgressBar
     * @param layoutId 元素
     * @param ctrlId 显示的控件id
     * @param resId 资源id
     */
    private void progressBar(int layoutId, int ctrlId, int resId) {
        if (mLayouts.containsKey(layoutId)) {
            ProgressBar view = (ProgressBar) mLayouts.get(layoutId).findViewById(ctrlId);
            if (view != null) {
                view.setIndeterminateDrawable(getResources().getDrawable(resId));
            }
        }
    }
}
