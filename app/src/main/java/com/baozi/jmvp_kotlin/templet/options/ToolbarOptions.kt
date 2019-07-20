package com.baozi.jmvp_kotlin.templet.options

import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import com.baozi.jmvp_kotlin.templet.helper.ToolbarHelper


/**
 * Created by baozi on 2017/10/25.
 */

class ToolbarOptions private constructor() : Cloneable {
    //其他字体大小,颜色
    var otherTextSize: Int = 0
    @ColorInt
    var otherTextColor: Int = 0
    //标题字体大小,颜色
    var titleSize: Int = 0
    @ColorInt
    var titleColor: Int = 0
    //toolbar配置
    var toolbarHeight: Int = 0
    @ColorInt
    var toolbarColor: Int = 0
    @LayoutRes
    var toolbarLayout = ToolbarHelper.TOOLBAR_TEMPLATE_DEFAULT
    @DrawableRes
    var toolbarDrawable: Int = 0
    @DrawableRes
    var statusDrawable: Int = 0
    //返回图标
    @DrawableRes
    var backDrawable: Int = 0

    var noBack: Boolean = false
    var elevation: Float = 0.toFloat()

    public override fun clone(): ToolbarOptions {
        try {
            return super.clone() as ToolbarOptions
        } catch (e: CloneNotSupportedException) {
            throw RuntimeException(e)
        }

    }

    companion object {
        fun create(): ToolbarOptions {
            return ToolbarOptions()
        }
    }
}
