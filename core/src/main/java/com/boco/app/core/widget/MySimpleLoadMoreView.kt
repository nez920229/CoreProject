package com.boco.app.core.widget

import com.boco.app.core.R
import com.chad.library.adapter.base.loadmore.LoadMoreView

/**
 * Created by walle on 2017/10/16.
 */
class MySimpleLoadMoreView : LoadMoreView() {

    override fun getLayoutId(): Int {
        return R.layout.quick_view_load_more
    }

    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_load_fail_view
    }

    override fun getLoadEndViewId(): Int {
        return R.id.load_more_load_end_view
    }
}