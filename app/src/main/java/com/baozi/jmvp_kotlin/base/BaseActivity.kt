package com.baozi.jmvp_kotlin.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.linfeng.mvp.MVPManager
import com.baozi.jmvp_kotlin.annotation.JView
import com.linfeng.mvp.presenter.BasePresenter
import com.linfeng.mvp.property.PresenterProperty
import com.baozi.jmvp_kotlin.view.UIView
import java.lang.reflect.ParameterizedType

/**
 * @author jlanglang  2016/1/5 9:42
 */
@Suppress("LeakingThis")
abstract class BaseActivity<T : BasePresenter<*>> : AppCompatActivity(), UIView {

    override val mContext: Context
        get() = this
    val isFinish: Boolean
        get() = isFinishing

    private var mPresenter by PresenterProperty<T>(this)
    private var statusBarView: View? = null

    protected open val statusBarDrawable: Int
        @DrawableRes
        @ColorRes
        get() = MVPManager.toolbarOptions.getStatusDrawable()

    private var mContentView: View? = null

    override fun getContentView(): View? {
        return mContentView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //创建presenter
        mPresenter = initPresenter()
        lifecycle.addObserver(mPresenter)
        //初始化ContentView
        mContentView = initView(layoutInflater, savedInstanceState)
        super.setContentView(mContentView)
    }

    override fun onNewThrowable(throwable: Throwable) {

    }

    @SuppressLint("ResourceType")
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        //初始化Activity
        init(savedInstanceState)
        onPresentersCreate()
        if (statusBarDrawable > 0) {
            initStatusBar()
            window?.decorView?.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                initStatusBar()
            }
        }
        mPresenter.onRefreshData()
    }

    open fun initStatusBar() {
        if (statusBarView == null) {
            val identifier = resources.getIdentifier("statusBarBackground", "id", "android")
            statusBarView = window?.findViewById(identifier)
        }
        statusBarView?.setBackgroundResource(statusBarDrawable)
    }

    /**
     * 扩展除了默认的presenter的其他Presenter初始化
     */
    protected open fun onPresentersCreate() {

    }

    /**
     * 运行在initView之后
     * 已经setContentView
     * 可以做一些初始化操作
     *
     * @return
     */
    protected open fun init(savedInstanceState: Bundle?) {

    }

    override fun onDetachedFromWindow() {
        mPresenter.onDetach()
        super.onDetachedFromWindow()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mPresenter.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onBack() {
        onBackPressed()
    }

    override fun resources(): Resources = resources

    override fun appcompatActivity(): AppCompatActivity = this

    override fun window(): Window = window

    override fun supportActionBar(): ActionBar? = supportActionBar

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter.onActivityResult(requestCode, resultCode, data)
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
    protected open fun initView(inflater: LayoutInflater, savedInstanceState: Bundle?): View {
        val initView = initView(savedInstanceState)
        return inflater.inflate(initView, null)
    }

    /**
     * 初始化ContentView
     * 建议不要包含toolbar
     * @param savedInstanceState
     * @return
     */
    protected open fun initView(savedInstanceState: Bundle?): Int {
        val annotation = this.javaClass.getAnnotation(JView::class.java)
        if (annotation != null) {
            return annotation.layout
        }
        return 0
    }

    override fun finishActivity() {
        finish()
    }

    protected open fun initPresenter(): T {
        // 通过反射机制获取子类传递过来的实体类的类型信息
        val type = this.javaClass.genericSuperclass as ParameterizedType
        return (type.actualTypeArguments[0] as Class<T>).newInstance()
    }
}
//class M {
//    var isLogin = false
//        set(value) {
//            if (field != value) {
//                VM().setVLogin(value)
//            }
//        }
//}
//class VM {
//    fun setVLogin(value: Boolean) {
//        V().setLogin(value)
//    }
//    fun setMLogin(value: Boolean) {
//        M().isLogin = value
//    }
//}
//class V {
//    fun setLogin(b: Boolean) {
//        VM().setMLogin(b)
//    }
//}