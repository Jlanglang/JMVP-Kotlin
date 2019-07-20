package com.baozi.jmvp_kotlin.view

import android.content.Context
import android.view.View

/**
 * @author jlanglang  2017/7/7 15:19
 * @版本 2.0
 * @Change
 */
/**
 * Created by baozi on 2016/11/24.
 */
interface BaseView {
    /**
     * 上下文
     */
    val viewContext: Context
    /**
     * 主要视图View
     *
     * @return
     */
    val viewContent: View?
}
