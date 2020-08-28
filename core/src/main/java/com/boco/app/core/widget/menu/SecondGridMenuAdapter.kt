package com.boco.app.core.widget.menu

import com.boco.app.core.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by walle on 2017/10/12.
 */
class SecondGridMenuAdapter(resId: Int, data: List<String>?, private val selectedPos: Int)
    : BaseQuickAdapter<String, BaseViewHolder>(resId, data) {
    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.menu_label, item)
        val indexOf = data.indexOf(item)
        if (selectedPos >= 0 && selectedPos == indexOf) {
            /*if (indexOf == 0 && isTitle) {
                helper.setTextColor(R.id.menu_label, config!!.textColor)
                        .setBackgroundRes(R.id.menu_label,R.drawable.item_second_grid_menu_unselected)
                return
            }*/
            helper.setTextColor(R.id.menu_label, config!!.selectedTextColor)
                    .setBackgroundRes(R.id.menu_label,R.drawable.item_second_grid_menu_selected)
        }else{
            helper.setTextColor(R.id.menu_label, config!!.textColor)
                    .setBackgroundRes(R.id.menu_label,R.drawable.item_second_grid_menu_unselected)
        }
    }

    private var isTitle: Boolean = false
    fun setFirstChildIsTitle(title: Boolean) {
        isTitle = title
    }

    private var config: Config? = null
    fun setConfig(config: Config) {
        this.config = config
    }
}