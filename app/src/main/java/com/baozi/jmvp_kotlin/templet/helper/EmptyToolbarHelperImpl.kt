package com.baozi.jmvp_kotlin.templet.helper

import android.view.View

import com.baozi.jmvp_kotlin.templet.options.ToolbarOptions

/**
 *
 */
class EmptyToolbarHelperImpl : ToolbarHelper() {
    override fun setToolbarOptions(toolbarOptions: ToolbarOptions) {

    }

    override fun setScrollFlag(viewId: Int, flag: Int): Boolean {
        return false
    }

    override fun <V : View> findViewFromAppBar(viewId: Int): V? {
        return null
    }

}
