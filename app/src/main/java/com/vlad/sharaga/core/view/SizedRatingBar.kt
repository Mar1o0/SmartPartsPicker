package com.vlad.sharaga.core.view

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.vlad.sharaga.R
import kotlin.math.floor
import kotlin.math.min

class SizedRatingBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var numStars: Int = 5
        set(value) {
            field = value
            requestLayout()
        }

    var starSize: Int = 20
        set(value) {
            field = value
            requestLayout()
        }
    var gapSize: Int = 5
        set(value) {
            field = value
            requestLayout()
        }
    var fillColor: Int = Color.YELLOW
        set(value) {
            field = value
            invalidate()
        }
    var rating: Float = 5f
        set(value) {
            field = min(value, numStars.toFloat())
            invalidate()
        }

    private var emptyStar: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_star_empty)
    private val paint = Paint().apply {
        style = Paint.Style.FILL
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    }
    private val colorFilter by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) BlendModeColorFilter(
            fillColor,
            BlendMode.SRC_IN
        )
        else PorterDuffColorFilter(fillColor, PorterDuff.Mode.SRC_IN)
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SizedRatingBar,
            0, 0
        ).apply {
            try {
                numStars = getInt(R.styleable.SizedRatingBar_numStars, numStars)
                starSize = getDimensionPixelSize(R.styleable.SizedRatingBar_starSize, starSize)
                gapSize = getDimensionPixelSize(R.styleable.SizedRatingBar_gapSize, gapSize)
                fillColor = getColor(R.styleable.SizedRatingBar_fillColor, fillColor)
                rating = min(
                    getFloat(R.styleable.SizedRatingBar_rating, numStars.toFloat()),
                    numStars.toFloat()
                )
            } finally {
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = numStars * starSize + (numStars - 1) * gapSize
        setMeasuredDimension(width, starSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = fillColor

        val progressLevel: Float = rating / numStars
        val progressWidth: Float = (starSize * numStars) * progressLevel + floor(rating) * gapSize

        for (i in 0 until numStars) {
            val left = i * starSize + i * gapSize
            val right = left + starSize

            // Draw empty star
            emptyStar?.setBounds(left, 0, right, starSize)
            emptyStar?.draw(canvas)

            // Draw filled color up to the current progress level
            if (progressWidth > left) {
                val filledRight = progressWidth.coerceAtMost(right.toFloat()).toInt()
                canvas.save()
                canvas.clipRect(left, 0, filledRight, starSize)

                emptyStar?.colorFilter = colorFilter
                emptyStar?.draw(canvas)
                emptyStar?.clearColorFilter()
                canvas.restore()
            }
        }
    }
}