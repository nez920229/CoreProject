package com.boco.app.core.widget.menu

import com.boco.app.core.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by walle on 2017/10/12.
 */
class SecondMenuAdapter(resId: Int, data: List<String>?, private val selectedPos: Int)
    : BaseQuickAdapter<String, BaseViewHolder>(resId, data) {
    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.menuLabel, item)
        val indexOf = data.indexOf(item)
        if (selectedPos >= 0 && selectedPos == indexOf) {
            /*if (indexOf == 0 && isTitle) {
                helper.setTextColor(R.id.menuLabel, config!!.textColor)
                        .setBackgroundColor(R.id.secondItem, config!!.itemBgColor)
                return
            }*/
            helper.setTextColor(R.id.menuLabel, config!!.selectedTextColor)
                    .setImageDrawable(R.id.imageView, config!!.drawableRight)
                    .setBackgroundColor(R.id.secondItem, config!!.selectedItemBgColor)
        }else{
            helper.setTextColor(R.id.menuLabel, config!!.textColor)
                    .setBackgroundColor(R.id.secondItem, config!!.itemBgColor)
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