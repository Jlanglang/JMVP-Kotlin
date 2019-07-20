package com.baozi.jmvp_kotlin.view

import android.support.annotation.LayoutRes
import com.baozi.jmvp_kotlin.templet.helper.ToolbarHelper
import com.baozi.jmvp_kotlin.templet.options.ToolbarOptions

/**
 * @author jlanglang  2017/3/4 17:44
 * @版本 2.0
 * @Change
 */

interface ToolbarView : BaseView {
    /**
     * 获得ToolbarHelper,Presenter可以通过ToolbarHelper的来控制toolbar
     */
    fun toolbarHelper(): ToolbarHelper

    fun toolbarOptions(): ToolbarOptions?

    /**
     * 通过这个修改toolbar的样式layout,不需要可以传0或者-1;
     *
     * @return
     */
    @LayoutRes
    fun toolbarLayout(): Int

    /**
     * 回退
     */
    fun onBackPressed()


}
