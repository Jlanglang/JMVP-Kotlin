package com.baozi.jmvp_kotlin.annotation

import android.support.annotation.LayoutRes
import java.lang.annotation.Inherited

@Inherited//可继承
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class JView(
    @LayoutRes val layout: Int = 0
)