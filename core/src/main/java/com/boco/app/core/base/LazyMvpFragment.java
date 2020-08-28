package com.boco.app.core.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.boco.app.core.R;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public abstract class LazyMvpFragment<V extends MvpView,P extends MvpPresenter<V> > extends BaseMvpFragment<V,P> {
    private FrameLayout mRootView;
    private boolean mIsInited;
    private View contentView;
    private boolean isViewCreated = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = (FrameLayout)inflater.inflate(R.layout.fragment_lazy_mvp, container, false);
        contentView = inflater.inflate(getLayoutRes(), mRootView, false);
        contentView.setVisibility(View.GONE);
        mRootView.addView(contentView);
        return mRootView;
    }

    @CallSuper
    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void onTimeUpdate(@NotNull String time, @NotNull String timeType) {
        super.onTimeUpdate(time, timeType);
    }

    public abstract int getLayoutRes();

    private void lazyLoad() {
        Log.i("LazyMvpFragment","lazyLoad getUserVisibleHint()="+getUserVisibleHint()+"--mIsInited="+mIsInited);
        if (getUserVisibleHint() && !mIsInited&&presenter!=null&&isViewCreated) {
            //异步初始化，在初始化后显示正常UI
            mIsInited = loadData();
            Log.i("LazyMvpFragment","lazyLoad mIsInited="+mIsInited);
            if(mIsInited) {
//                mRootView.findViewById(R.id.empty_tv).setVisibility(View.GONE);
                contentView.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * @param refresh 是否刷新数据
     */
    public void lazyLoad(boolean refresh){
        if(refresh)
            mIsInited = false;
        lazyLoad();
    }

    /**
     * 子类重写这个方法实现数据加载,
     * 一般不要直接在子类中调用这个方法，而是去调用lazyLoad()
     * @return 数据是否加载成功
     */
    protected boolean loadData() {
        return false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        Log.i("LazyMvpFragment","setUserVisibleHint="+isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad();
        }
    }

}