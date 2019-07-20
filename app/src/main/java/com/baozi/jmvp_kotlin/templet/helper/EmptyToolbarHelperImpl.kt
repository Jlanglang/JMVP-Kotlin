package com.linfeng.mvp.templet.helper

import android.view.View

import com.linfeng.mvp.templet.options.ToolbarOptions

/**
 *
 */
class EmptyToolbarHelperImpl : ToolbarHelper() {

    override fun setScrollFlag(viewId: Int, flag: Int): Boolean {
        return false
    }

    override fun <V : View> findViewFromAppBar(viewId: Int): V? {
        return null
    }

    override fun setToolbarOptions(toolbarOptions: ToolbarOptions) {

    }

}
