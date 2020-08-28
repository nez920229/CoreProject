package com.boco.app.core.widget.menu

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boco.app.core.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by walle on 2017/10/10.
 */
class RootViewAdapter(private val baseView: View, resId: Int, data: List<Entity>?)
    : BaseQuickAdapter<Entity, BaseViewHolder>(resId, data) {
    override fun convert(helper: BaseViewHolder, item: Entity) {
        helper.itemView.setOnClickListener {
            val popView = LayoutInflater.from(mContext).inflate(R.layout.drop_down_menu_pop, null)
            popView.setBackgroundColor(Color.WHITE)
            val popWindow = PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            popWindow.isOutsideTouchable = true
            popWindow.setBackgroundDrawable(BitmapDrawable())

            popWindow.showAsDropDown(baseView,0,5)
            val secondMenu = popView.findViewById(R.id.secondMenu) as RecyclerView
            if (item.type=="1") {
//                secondMenu.addItemDecoration(LinearLayoutInnerDivider(mContext))
                val manager = LinearLayoutManager(mContext)
                secondMenu.layoutManager = manager
                val secondAdapter = SecondMenuAdapter(R.layout.item_second_menu_adapter, item.menuList, item.selectedPos)
                secondAdapter.setConfig(config!!)
                secondAdapter.setFirstChildIsTitle(isTitle)
                secondAdapter.setOnItemClickListener { adapter, view, position ->
                    helper.setText(R.id.title, secondAdapter.data[position])
                    item.selectedPos = position
                    popWindow.dismiss()
                    dropMenuListener?.onItemClick(data.indexOf(item), position, secondAdapter.data[position])
                }
                secondMenu.adapter = secondAdapter
            }else if (item.type=="2"){
                val manager1= GridLayoutManager(mContext,3)
                manager1.isAutoMeasureEnabled=true
                secondMenu.layoutManager=manager1
                secondMenu.setHasFixedSize(true)
                val secondGridAdapter=SecondGridMenuAdapter(R.layout.item_second_grid_menu,item.menuList,item.selectedPos)

                secondGridAdapter.setConfig(config!!)
                secondGridAdapter.setFirstChildIsTitle(isTitle)
                secondGridAdapter.setOnItemClickListener { adapter, view, position ->
                    helper.setText(R.id.title, secondGridAdapter.data[position])
                    item.selectedPos = position
                    popWindow.dismiss()
                    dropMenuListener?.onItemClick(data.indexOf(item), position, secondGridAdapter.data[position])
                }
                secondMenu.adapter = secondGridAdapter
            }
        }
        helper.setText(R.id.title, item.title)
    }

    private var dropMenuListener: DropDownMenu.OnItemClickListener<String>? = null

    fun setMenuItemClickListener(listener: DropDownMenu.OnItemClickListener<String>?) {
        dropMenuListener = listener
    }

    private var isTitle: Boolean = false

    fun setFirstChildIsTitle(title: Boolean) {
        isTitle = title
    }

    private var config:Config?=null

    fun setConfig(config: Config) {
        this.config = config
    }

}