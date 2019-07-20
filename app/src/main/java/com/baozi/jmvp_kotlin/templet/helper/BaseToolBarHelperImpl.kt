package com.baozi.jmvp_kotlin.templet.helper

import android.content.Context
import android.os.Build
import android.support.annotation.IdRes
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.Toolbar
import android.util.SparseArray
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import com.baozi.jmvp_kotlin.R
import com.baozi.jmvp_kotlin.templet.options.ToolbarOptions

import com.baozi.jmvp_kotlin.view.ToolbarView
import com.baozi.jmvp_kotlin.MVPManager

/**
 * @author jlanglang  2017/2/22 16:58
 */
open class BaseToolBarHelperImpl(protected var mToolbarView: ToolbarView) : ToolbarHelper() {
    private val mContext: Context = mToolbarView.viewContext
    final override var toolbar: Toolbar? = null
    final override var appBarLayout: AppBarLayout? = mToolbarView.contentView.findViewById(R.id.app_bar)
    private val mViews: SparseArray<View> = SparseArray()

    init {
        //初始化AppBarLayout
        appBarLayout?.removeAllViews()
        //将toolbarLayout添加到AppBarLayout中
        val inflate = LayoutInflater.from(mContext).inflate(mToolbarView.toolbarLayout, appBarLayout, true)
        //如果find不为null,则设置toolbar
        toolbar = inflate.findViewById(R.id.tl_custom)
        if (toolbar != null) {
            this.initToolbar()
            var mToolbarOptions = mToolbarView.toolbarOptions
            if (mToolbarOptions == null) {
                mToolbarOptions = MVPManager.toolbarOptions
            }
            this.setToolbarOptions(mToolbarOptions)
        }
    }

    override fun setToolbarOptions(toolbarOptions: ToolbarOptions) {
        if (toolbar == null) {
            return
        }
        val toolbarColor = toolbarOptions.getToolbarColor()
        val toolbarDrawable = toolbarOptions.getToolbarDrawable()
        val toolbarHeight = toolbarOptions.getToolbarHeight()
        if (toolbarColor != 0) {
            toolbar!!.setBackgroundColor(toolbarColor)
        }
        if (toolbarOptions.getToolbarDrawable() != 0) {
            toolbar!!.setBackgroundResource(toolbarDrawable)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBarLayout!!.elevation = toolbarOptions.getElevation()
            appBarLayout!!.translationZ = toolbarOptions.getElevation()
            appBarLayout!!.invalidate()
        }
        if (toolbarHeight > 0) {
            val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toolbarHeight.toFloat(),
                    mContext.resources.displayMetrics)
            toolbar!!.layoutParams.height = Math.round(px)
            toolbar!!.requestLayout()
        }
    }

    open fun initToolbar() {

    }

    /**
     * 从AppBarLayout中获取控件
     *
     * @param viewId 控件Id
     * @param <V>    返回泛型,减少强转操作
     * @return 可能为null
    </V> */
    override fun <V : View> findViewFromAppBar(@IdRes viewId: Int): V? {
        var view: View? = mViews.get(viewId)
        if (view == null && appBarLayout != null) {
            view = appBarLayout!!.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as? V
    }

    /**
     * 设置控件滑动效果
     *
     * @param viewId view id
     * @param flag   设置的滑动flag
     * @return 设置成功返回true, 设置失败返回false
     */
    override fun setScrollFlag(@IdRes viewId: Int, @AppBarLayout.LayoutParams.ScrollFlags flag: Int): Boolean {
        val view = findViewFromAppBar<View>(viewId)

        try {
            val layoutParams = view?.layoutParams as? AppBarLayout.LayoutParams
            layoutParams?.scrollFlags = flag
        } catch (e: ClassCastException) {
            e.printStackTrace()
            return false
        } catch (e: NullPointerException) {
            e.printStackTrace()
            return false
        }

        return true
    }

    fun <T : View> findView(id: Int): T {
        return toolbar!!.findViewById(id)
    }


}
