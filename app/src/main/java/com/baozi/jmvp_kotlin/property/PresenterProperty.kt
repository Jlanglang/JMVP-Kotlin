package com.baozi.jmvp_kotlin.property

import com.baozi.jmvp_kotlin.presenter.BasePresenter
import com.baozi.jmvp_kotlin.view.UIView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by baozi on 2017/10/20.
 *
 */
class PresenterProperty<T : BasePresenter<*>>(private val uiView: UIView) : ReadWriteProperty<Any?, T> {
    private var presenter: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return presenter ?: throw IllegalStateException("presenter 为null")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.presenter = value
        value.attach(uiView)
    }
}