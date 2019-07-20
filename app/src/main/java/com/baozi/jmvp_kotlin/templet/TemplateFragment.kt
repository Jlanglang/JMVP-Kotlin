package com.baozi.jmvp_kotlin.templet

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baozi.jmvp_kotlin.MVPManager
import com.baozi.jmvp_kotlin.R
import com.baozi.jmvp_kotlin.base.BaseFragment
import com.baozi.jmvp_kotlin.presenter.BasePresenter
import com.baozi.jmvp_kotlin.templet.helper.ToolbarHelper
import com.baozi.jmvp_kotlin.templet.options.ContentOptions
import com.baozi.jmvp_kotlin.templet.options.ToolbarOptions
import com.baozi.jmvp_kotlin.view.ToolbarView

/**
 * 模版Fragment
 *
 * @param <T>
</T> */
class TemplateFragment<T : BasePresenter<*>> : BaseFragment<T>(), ToolbarView {


    private lateinit var rootView: ViewGroup

    /**
     * 默认使用base_toolbar
     * 如果不需要toolbar,请复写,并返回0.或者-1
     *
     * @return
     */
    override val toolbarLayout: Int
        get() = ToolbarHelper.TOOLBAR_TEMPLATE_DEFAULT

    override val toolbarOptions: ToolbarOptions
        get() = MVPManager.toolbarOptions

    protected val contentOptions: ContentOptions
        get() = MVPManager.contentOptions

    override val isMaterialDesign: Boolean
        get() = false

    /**
     * 如果设置的主题不是NoActionBar或者initToolbar()返回是0,则返回null.
     *
     * @return mToolbar 可能为null.
     */
    val toolbar: Toolbar?
        get() = toolbarHelper.toolbar

    /**
     * @return
     */
    override val toolbarHelper: ToolbarHelper by lazy {
        ToolbarHelper.create(this)
    }

    override fun onBackPressed() {
        onBack()
    }

    /**
     * @param inflater
     * @param savedInstanceState
     * @return
     */
    override fun initView(inflater: LayoutInflater, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.template_layout, null) as ViewGroup
        val initView = super.initView(inflater, savedInstanceState) ?: return rootView
        val view = wrapperContentView(initView)
        rootView.addView(view, 1)

        val layoutParams = view.layoutParams as? CoordinatorLayout.LayoutParams
        layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams?.behavior = AppBarLayout.ScrollingViewBehavior()
        view.requestLayout()
        return rootView
    }

    protected open fun wrapperContentView(view: View): View {
        return view
    }

}
