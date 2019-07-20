package com.linfeng.mvp.presenter

import com.baozi.jmvp_kotlin.view.UIView

/**
 * Created by baozi on 2017/12/20.
 */

class EmptyPresenter : BasePresenter<UIView>() {

    override fun onCreate() {}

    override fun onRefreshData() {

    }

    override fun cancelRequest() {

    }

    override fun netWorkError(throwable: Throwable) {

    }
}
