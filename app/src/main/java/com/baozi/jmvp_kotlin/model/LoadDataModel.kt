package com.baozi.jmvp_kotlin.model

/**
 * Created by baozi on 2017/12/5.
 */

interface LoadDataModel<T> {
    fun loadData(pageNum: Int, pageSize: Int, isRefresh: Boolean): T
}
