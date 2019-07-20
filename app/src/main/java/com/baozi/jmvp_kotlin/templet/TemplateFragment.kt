package com.baozi.jmvp_kotlin.templet

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baozi.jmvp_kotlin.MVPManager
import com.baozi.jmvp_kotlin.R
import com.baozi.jmvp_kotlin.base.BaseFragment
import com.baozi.jmvp_kotlin.presenter.BasePresenter
import com.baozi.jmvp_kotlin.templet.helper.ToolbarHelper
import com.baozi.jmvp_kotlin.templet.options.ToolbarOptions
import com.baozi.jmvp_kotlin.view.ToolbarView

/**
 * 模版Fragment
 *
 * @param <T>
</T> */
open class TemplateFragment<T : BasePresenter<*>> : BaseFragment<T>(), ToolbarView {

    private lateinit var rootView: ViewGroup

    /**
     * 请在ui线程中调用
     *
     * @return
     */
    val toolbarHelper: ToolbarHelper by lazy {
        ToolbarHelper.create(this)
    }

    /**
     * 请在ui线程中调用
     *
     * @return
     */
    val toolbarOptions: ToolbarOptions by lazy {
        MVPManager.toolbarOptions
    }

    override fun toolbarLayout(): Int = toolbarOptions().toolbarLayout
    override fun toolbarHelper(): ToolbarHelper = toolbarHelper
    override fun toolbarOptions(): ToolbarOptions = toolbarOptions

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

     open fun wrapperContentView(view: View): View {
        return view
    }

}
