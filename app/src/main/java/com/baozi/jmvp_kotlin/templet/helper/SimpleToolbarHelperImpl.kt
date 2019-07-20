package com.linfeng.mvp.templet.helper

import android.support.annotation.ColorInt
import android.text.TextUtils
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView

import com.baozi.mvp.R
import com.baozi.mvp.tempalet.options.ToolbarOptions
import com.baozi.mvp.view.ToolbarView

/**
 * @author jlanglang  2017/2/21 16:44
 * @版本 2.0
 * @Change
 */

class SimpleToolbarHelperImpl internal constructor(uiView: ToolbarView) : BaseToolBarHelperImpl(uiView) {
    private var mTitleView: TextView? = null
    @ColorInt
    private var mOtherTextColor: Int = 0
    private var mOtherTextSize: Int = 0
    private var endActions: LinearLayout? = null

    override fun initToolbar() {
        mTitleView = findView(R.id.tv_title)
        endActions = findView(R.id.ll_end_group)
        ToolbarHelper.SimpleInitToolbar(mToolbarView.getContext(), toolbar!!, false)
    }

    override fun setToolbarOptions(options: ToolbarOptions?) {
        super.setToolbarOptions(options)
        val mTitleSize = options!!.getTitleSize()
        val mTitleColor = options!!.getTitleColor()
        mOtherTextColor = options!!.getOtherTextColor()
        mOtherTextSize = options!!.getOtherTextSize()
        if (mOtherTextColor != 0 || mOtherTextSize != 0) {
            notifyToolbarText()
        }
        val noBack = options!!.isNoBack()
        if (mTitleSize != 0) {
            mTitleView!!.textSize = mTitleSize.toFloat()
        }
        if (mTitleColor != 0) {
            mTitleView!!.setTextColor(mTitleColor)
        }
        if (!noBack) {
            var backDrawable = options!!.getBackDrawable()
            if (backDrawable == 0) {
                backDrawable = R.drawable.back
            }
            setLeading(backDrawable)
        }
    }

    private fun notifyToolbarText() {
        val childCount = toolbar!!.childCount
        for (i in 0 until childCount) {
            val childAt = toolbar!!.getChildAt(childCount)
            if (childAt is TextView) {
                if (mOtherTextColor != 0) {
                    childAt.setTextColor(mOtherTextColor)
                }
                if (mOtherTextSize != 0) {
                    childAt.textSize = mOtherTextSize.toFloat()
                }
            }
        }
    }

    override fun setTextSize(size: Int): ToolbarHelper {
        return this
    }

    override fun setTitleSize(size: Int): ToolbarHelper {
        mTitleView!!.textSize = size.toFloat()
        return this
    }


    override fun setTitle(titleView: String): ToolbarHelper {
        mTitleView!!.text = titleView
        return this
    }

    override fun setTitle(titleId: Int): ToolbarHelper {
        val title = mToolbarView.getContext().getResources().getString(titleId)
        setTitle(title)
        return this
    }


    override fun setCanBack(canBack: Boolean): ToolbarHelper {
        super.setCanBack(canBack)
        findView(R.id.ll_start_group).setVisibility(if (canBack) View.VISIBLE else View.GONE)
        return this
    }

    override fun setLeading(leading: String): ToolbarHelper {
        val view = findView(R.id.tv_start)
        if (TextUtils.isEmpty(leading)) {
            view.setVisibility(View.GONE)
            return this
        }
        view.setVisibility(View.VISIBLE)
        view.setText(leading)
        view.setOnClickListener(View.OnClickListener { mToolbarView.onBackPressed() })
        return this
    }

    override fun setLeading(leadRes: Int): ToolbarHelper {
        val view = findView(R.id.ib_start)
        view.setVisibility(View.VISIBLE)
        view.setImageResource(leadRes)
        view.setOnClickListener(View.OnClickListener { mToolbarView.onBackPressed() })
        return this
    }

    override fun addActions(view: View?): ToolbarHelper {
        if (endActions != null && view != null) {
            if (view is TextView) {
                if (mOtherTextColor != 0) {
                    view.setTextColor(mOtherTextColor)
                }
                if (mOtherTextSize != 0) {
                    view.textSize = mOtherTextSize.toFloat()
                }
            }
            endActions!!.addView(view)
        }
        return this
    }

    fun setEndText(str: String?, clickListener: View.OnClickListener) {
        if (endActions != null) {
            val view = findView(R.id.tv_start)
            if (str == null) {
                view.setVisibility(View.GONE)
                return
            }
            view.setVisibility(View.VISIBLE)
            view.setText(str)
            view.setOnClickListener(clickListener)
        }
    }

    /**
     * @param drawable      设置0则隐藏
     * @param clickListener 点击监听
     */
    fun setEndImage(drawable: Int, clickListener: View.OnClickListener) {
        if (endActions != null) {
            val view = findView(R.id.tv_start)
            if (drawable == 0) {
                view.setVisibility(View.GONE)
                return
            }
            view.setVisibility(View.VISIBLE)
            view.setImageResource(drawable)
            view.setOnClickListener(clickListener)
        }
    }
}
