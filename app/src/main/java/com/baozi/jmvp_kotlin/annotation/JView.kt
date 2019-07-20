package com.baozi.jmvp_kotlin.annotation

import com.baozi.jmvp_kotlin.presenter.BasePresenter
import com.baozi.jmvp_kotlin.presenter.EmptyPresenter
import java.lang.annotation.Inherited
import kotlin.reflect.KClass

@Inherited//可继承
@Target(AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class JView(
    /**
     * @return presenter的class
     */
    val p: KClass<out BasePresenter<*>> = EmptyPresenter::class,
    /**
     * @return 布局id
     */
    val layout: Int = 0
)
