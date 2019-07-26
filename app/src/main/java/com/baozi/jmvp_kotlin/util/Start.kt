package com.baozi.jmvp_kotlin.util

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.baozi.jmvp_kotlin.MVPManager

inline fun <reified T : Fragment> newInstance(block: Bundle.(b: Bundle) -> Unit): T {
    val instance = T::class.java.newInstance()
    val bundle = Bundle().apply {
        block(this)
    }
    instance.arguments = bundle
    return instance
}


inline fun <reified T : Activity> Activity.startAt(block: Intent.(b: Intent) -> Unit) {
    val zClass = T::class.java
    Intent(this, zClass).apply {
        block(this)
    }
}


inline fun <reified T : Activity> Fragment.startAt(block: Intent.(b: Intent) -> Unit) {
    val zClass = T::class.java
    Intent(this.context, zClass).apply {
        block(this)
    }
}


inline fun <reified T : Fragment> AppCompatActivity.startFg(
    block: Bundle.(b: Bundle) -> Unit,
    tag: String,
    enterAnim: Int = MVPManager.enterAnim,
    exitAnim: Int = MVPManager.exitAnim,
    popEnter: Int = MVPManager.enterPopAnim,
    popExit: Int = MVPManager.exitPopAnim,
    isAddBack: Boolean = true
) {
    val newInstance = newInstance<T>(block)
    supportFragmentManager?.beginTransaction()?.apply {
        setCustomAnimations(enterAnim, exitAnim, popEnter, popExit)
        add(android.R.id.content, newInstance, tag)
        if (isAddBack) {
            addToBackStack(tag)
        }
        commitAllowingStateLoss()
    }
}

inline fun <reified T : Fragment> Fragment.startFg(
    block: Bundle.(b: Bundle) -> Unit,
    tag: String? = null,
    enterAnim: Int? = MVPManager.enterAnim,
    exitAnim: Int? = MVPManager.exitAnim,
    popEnter: Int? = MVPManager.enterPopAnim,
    popExit: Int? = MVPManager.exitPopAnim,
    isAddBack: Boolean? = true
) {
    val newInstance = newInstance<T>(block)
    val fragment = this
    fragmentManager?.beginTransaction()?.apply {
        setCustomAnimations(enterAnim ?: 0, exitAnim ?: 0, popEnter ?: 0, popExit ?: 0)
        hide(fragment)
        add(android.R.id.content, newInstance, tag)
        if (isAddBack != false) {
            addToBackStack(tag)
        }
        commitAllowingStateLoss()
    }
}