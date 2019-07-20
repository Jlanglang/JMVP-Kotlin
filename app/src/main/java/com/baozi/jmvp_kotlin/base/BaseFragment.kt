package com.baozi.jmvp_kotlin.base

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Looper
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.baozi.jmvp_kotlin.annotation.JView
import com.baozi.jmvp_kotlin.presenter.BasePresenter
import com.baozi.jmvp_kotlin.property.PresenterProperty
import com.baozi.jmvp_kotlin.view.UIView
import java.lang.reflect.ParameterizedType

/**
 * @author jlanglang  2016/1/5 9:42
 */
abstract class BaseFragment<T : BasePresenter<*>> : Fragment(), UIView {
    val TAG: String = this.javaClass.simpleName
    override lateinit var viewContext: Context
    protected var mPresenter: T by PresenterProperty(this)
    protected open lateinit var mBundle: Bundle
    private var mContentView: View? = null
    private var isInit: Boolean = false
    val isFinish: Boolean
        get() = isDetached

    /**
     * 绑定activity

     * @param context
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewContext = context
        //应该只创建一次Presenter.
        if (!isInit) {
            mPresenter = initPresenter()
        }
    }

    /**
     * 运行在onAttach之后
     * 可以接受别人传递过来的参数,实例化对象.
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //获取bundle,并保存起来
        mBundle = savedInstanceState?.getBundle("bundle") ?: arguments ?: Bundle()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBundle("bundle", mBundle)
        super.onSaveInstanceState(outState)
    }

    /**
     * 运行在onCreate之后
     * 生成view视图

     * @param inflater
     * *
     * @param container
     * *
     * @param savedInstanceState
     * *
     * @return
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null == mContentView) {
            mContentView = initView(inflater, savedInstanceState)
        } else {
            //缓存的ContentView需要判断是否已有parent， 如果有parent需要从parent删除，否则会抛出异常。
            val parent = mContentView?.parent as? ViewGroup
            parent?.removeView(mContentView)
        }
        return mContentView
    }

    /**
     * 运行在onCreateView之后
     * 加载数据,初始化Presenter

     * @param savedInstanceState
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //View做一些初始化操作.
        init(savedInstanceState)
        //初始化Presenter,应该只初始化一次
        if (!isInit) {
            isInit = true
            mPresenter.onCreate()
            //可见时再加载数据刷新视图
            Looper.myQueue().addIdleHandler {
                mPresenter.onRefreshData()
                false
            }
        }
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            mPresenter.onResume()
        } else {
            //相当于Fragment的onPause
            mPresenter.onPause()
        }
    }

    override fun onDetach() {
        mPresenter.onDetach()
        super.onDetach()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mPresenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun getContentView(): View? {
        return mContentView
    }

    /**
     * 类似Activity的OnBackgress
     * fragment进行回退
     */
    override fun onBack() {
        fragmentManager?.popBackStackImmediate()
    }


    /**
     * 初始化ContentView
     * 建议不要包含toolbar

     * @param inflater
     * *
     * @param savedInstanceState
     * *
     * @return
     */
    protected open fun initView(inflater: LayoutInflater, savedInstanceState: Bundle?): View? {
        val layout = initView(savedInstanceState)
        return inflater.inflate(layout, null)
    }

    /**
     * 建议不要包含toolbar

     * @param savedInstanceState
     * *
     * @return 布局layout
     */
    @LayoutRes
    protected open fun initView(savedInstanceState: Bundle?): Int {
        val annotation = this.javaClass.getAnnotation(JView::class.java)
        if (annotation != null) {
            return annotation.layout
        }
        return 0
    }

    /**
     * 运行在initView之后
     * 此时已经setContentView
     * 可以做一些初始化操作
     */
    open fun init(savedInstanceState: Bundle?) {

    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        appcompatActivity().setSupportActionBar(toolbar)
    }

    override fun resources(): Resources = viewContext.resources

    override fun appcompatActivity(): AppCompatActivity = viewContext as AppCompatActivity

    override fun window(): Window = appcompatActivity().window

    override fun supportActionBar(): ActionBar? = appcompatActivity().supportActionBar

    override fun onNewThrowable(throwable: Throwable) {
    }

    override fun finish() {
        appcompatActivity().finish()
    }

    /**
     * 初始化Presenter
     * @return
     */
    protected open fun initPresenter(): T {
        // 通过反射机制获取子类传递过来的实体类的类型信息
        val type = this.javaClass.genericSuperclass as ParameterizedType
        try {
            return (type.actualTypeArguments[0] as Class<T>).newInstance()
        } catch (e: Exception) {

        }
        val annotation = this.javaClass.getAnnotation(JView::class.java)
        return annotation?.p?.java?.newInstance() as T
    }
}
