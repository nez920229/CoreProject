package com.boco.app.core.widget.menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.boco.app.core.R
import com.boco.app.core.common.layoutmanager.HLayoutManager

/**
 * Created by walle on 2017/10/10.
 */
class DropDownMenu(context: Context, attributeSet: AttributeSet, defStyle: Int) : FrameLayout(context, attributeSet, defStyle) {
    constructor(context: Context, attributeSet: AttributeSet) : this(context, attributeSet, 0)

    private var rootViewAdapter: RootViewAdapter? = null

    private var rootMenu: RecyclerView
    private var config:Config?=null
    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.drop_down_menu, this, true)
        rootMenu = inflate.findViewById(R.id.rootMenu) as RecyclerView
    }

    fun setConfig(config: Config){
        this.config = config
    }

    fun setData(data: List<Entity>) {
        if(config==null)
            config = initConfig()
        val manager = HLayoutManager(context, data.size)
        rootMenu.layoutManager = manager
        rootViewAdapter = RootViewAdapter(rootMenu, R.layout.item_drop_down_menu, data)
        rootViewAdapter?.setConfig(config!!)
        rootViewAdapter?.setMenuItemClickListener(dropMenuListener)
        rootViewAdapter?.setFirstChildIsTitle(isTitle)
        rootMenu.adapter = rootViewAdapter
    }

    private fun initConfig(): Config {
        var _config = Config()
        _config.itemView=R.layout.item_drop_down_menu
        _config.drawableRight = context.resources.getDrawable(R.drawable.nav_tick3)
        return _config
    }

    private var dropMenuListener: DropDownMenu.OnItemClickListener<String>? = null
    /**
     * setData前调用
     */
    fun setOnItemClickListener(listener: DropDownMenu.OnItemClickListener<String>) {
        dropMenuListener = listener
    }


    interface OnItemClickListener<T> {
        fun onItemClick(parentPos: Int, childPos: Int, obj: T)
    }

    private var isTitle: Boolean = false
    /**
     * setData前调用，
     * 菜单列表的第一个是否是标题，是标题则点击颜色不变
     */
    fun setFirstChildIsTitle(isTitle: Boolean) {
        this.isTitle = isTitle
    }

}