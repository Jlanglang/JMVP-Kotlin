package com.baozi.jmvp_kotlin.application

import android.app.Application

/**
 * Created by baozi on 2017/10/20.
 */
open class BaseApp : Application() {
    companion object {
        lateinit var app: BaseApp
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}