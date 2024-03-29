package com.baozi.jmvp_kotlin.view

import android.content.res.Resources
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Window

/**
 * Created by baozi on 2017/2/20.
 * 用户页面,操作页面，对应Activity,frgament...
 */

interface UIView : BaseView {
    /**
     * res资源获取
     *
     * @return
     */
    fun resources(): Resources

    /**
     * 获取Activity
     *
     * @return
     */
    fun appcompatActivity(): AppCompatActivity

    fun window(): Window

    fun supportActionBar(): ActionBar?

    /**
     * 回退
     */

    fun onBack()

    /**
     * 处理异常
     *
     * @param throwable
     */
    fun onNewThrowable(throwable: Throwable)

    fun setSupportActionBar(toolbar: Toolbar?)

    fun finish()
}
