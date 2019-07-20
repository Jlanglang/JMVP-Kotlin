package com.baozi.jmvp_kotlin.templet.helper

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.baozi.jmvp_kotlin.R
import com.baozi.jmvp_kotlin.templet.options.ToolbarOptions
import com.baozi.jmvp_kotlin.view.ToolbarView

/**
 * @author jlanglang  2017/2/21 16:31
 * @版本 2.0
 * @Change
 */
abstract class ToolbarHelper {


    /**
     * 获取AppBarLayout
     *
     * @return
     */
    open val appBarLayout: AppBarLayout?
        get() = null

    /**
     * 获取Toolbar
     *
     * @return
     */
    open val toolbar: Toolbar?
        get() = null

    /**
     * 设置滑动Flag
     *
     * @param viewId
     * @param flag
     * @return
     */
    abstract fun setScrollFlag(@IdRes viewId: Int, @AppBarLayout.LayoutParams.ScrollFlags flag: Int): Boolean

    /**
     * 获取AppBarLayout中的View
     *
     * @param viewId
     * @param <V>
     * @return
    </V> */
    abstract fun <V : View> findViewFromAppBar(@IdRes viewId: Int): V?

    /**
     * 获取Toolbar配置
     *
     * @return
     */
    abstract fun setToolbarOptions(toolbarOptions: ToolbarOptions)

    /**
     * 设置title
     *
     * @param str
     */
    open fun setTitle(str: String): ToolbarHelper {
        return this
    }

    open fun setTitle(@StringRes str: Int): ToolbarHelper {
        return this
    }


    open fun setCanBack(canBack: Boolean): ToolbarHelper {
        return this
    }

    open fun setLeading(leading: String): ToolbarHelper {
        return this
    }

    open fun setLeading(@DrawableRes leadRes: Int): ToolbarHelper {
        return this
    }

    open fun addActions(view: View): ToolbarHelper {
        return this
    }

    open fun setTextSize(size: Int): ToolbarHelper {
        return this
    }

    open fun setTitleSize(size: Int): ToolbarHelper {
        return this
    }

    companion object {
         const val TOOLBAR_TEMPLATE_DEFAULT = R.layout.toolbar_template_default

        fun create(toolbarView: ToolbarView): ToolbarHelper {
            val toolbarLayout = toolbarView.toolbarLayout
            return when {
                toolbarLayout <= 0 -> EmptyToolbarHelperImpl()
                toolbarLayout == TOOLBAR_TEMPLATE_DEFAULT -> SimpleToolbarHelperImpl(toolbarView)
                else -> BaseToolBarHelperImpl(toolbarView)
            }
        }

        /**
         * 快速设置Toolbar,取消边距,隐藏所有默认的显示.
         * setDisplayShowCustomEnabled(),setDisplayHomeAsUpEnabled()
         * setDisplayShowTitleEnabled(),setDisplayShowHomeEnable()
         * 都将设置为false
         *
         * @param context 继承Appcompat的activity的上下文
         * @param toolbar 将要设置的Toolbar
         */

        fun simpleInitToolbar(context: Context, toolbar: Toolbar?, isMaterialDesign: Boolean) {
            if (context is AppCompatActivity) {
                toolbar?.setContentInsetsAbsolute(0, 0)
                context.setSupportActionBar(toolbar)
                val supportActionBar = context.supportActionBar
                if (supportActionBar != null) {
                    supportActionBar.setDisplayShowCustomEnabled(isMaterialDesign)
                    supportActionBar.setDisplayHomeAsUpEnabled(isMaterialDesign)
                    supportActionBar.setDisplayShowTitleEnabled(isMaterialDesign)
                    supportActionBar.setDisplayShowHomeEnabled(isMaterialDesign)
                }
            }
        }
    }
}
