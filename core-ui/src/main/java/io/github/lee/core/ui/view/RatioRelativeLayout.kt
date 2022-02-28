package io.github.lee.core.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import io.github.lee.core.ui.R


class RatioRelativeLayout : RelativeLayout {
    companion object {
        const val VERTICAL = 0
        const val HORIZONTAL = 1
    }

    /**设置图像比例*/
    var ration: Float = 0F
        set(value) {
            field = value
            invalidate()
        }

    /**设置尺寸改变的方向，高改变或者宽改变*/
    var orientation: Int = 0
        set(value) {
            if (value == VERTICAL || value == HORIZONTAL) {
                field = value
                invalidate()
            } else {
                throw IllegalArgumentException("Unrecognized orientation parameter, Please pass 'vertical' or 'horizontal'")
            }
        }


    //===Desc:=====================================================================================
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.RatioRelativeLayout)
        this.ration = ta.getFloat(R.styleable.RatioRelativeLayout_rrl_ration, 0F)
        this.orientation = ta.getInt(R.styleable.RatioRelativeLayout_rrl_orientation, VERTICAL)
        ta.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (orientation == VERTICAL) { //vertical设置高度动态变化
            var hms = heightMeasureSpec
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val widthMode = MeasureSpec.getMode(widthMeasureSpec)
            if (widthMode == MeasureSpec.EXACTLY && ration != 0F) {
                val height = width / ration
                hms = MeasureSpec.makeMeasureSpec(height.toInt(), MeasureSpec.EXACTLY)
            }
            super.onMeasure(widthMeasureSpec, hms)
        } else {
            var wms = widthMeasureSpec
            val height = MeasureSpec.getSize(heightMeasureSpec)
            val heightMode = MeasureSpec.getMode(heightMeasureSpec)
            if (heightMode == MeasureSpec.EXACTLY && ration != 0F) {
                val width = height / ration
                wms = MeasureSpec.makeMeasureSpec(width.toInt(), MeasureSpec.EXACTLY)
            }
            super.onMeasure(wms, heightMeasureSpec)
        }
    }

}