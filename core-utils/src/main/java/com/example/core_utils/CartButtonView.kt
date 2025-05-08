package com.example.core_utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CartButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private var widthPx: Float = 0f
    private var heightPx: Float = 0f

    private var inCartCount: Int = 0
    private var isDataLoaded: Boolean = false

    var updateProductInCartCount: ((Int) -> Unit)? = null

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = TEXT_SIZE_NORMAL
        textAlign = Paint.Align.CENTER
    }
    private val buttonRect: RectF by lazy { RectF() }

    private val handler = Handler(Looper.getMainLooper())
    private var repeatInterval = INITIAL_INTERVAL
    private var isIncrementing = false
    private var isDecrementing = false

    private val counterChangeRunnable = object : Runnable {
        override fun run() {
            when {
                isIncrementing -> {
                    incrementCount()
                }

                isDecrementing -> {
                    decrementCount()
                }
            }
            repeatInterval = (repeatInterval - INTERVAL_DECREMENT).coerceAtLeast(MIN_INTERVAL)
            handler.postDelayed(this, repeatInterval)
        }
    }

    init {
        isClickable = true
    }

    fun setState(inCartCount: Int) {
        this.inCartCount = inCartCount
        isDataLoaded = true
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        widthPx = w.toFloat()
        heightPx = h.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isDataLoaded) {
            if (inCartCount > 0) {
                drawCounter(canvas, inCartCount)
            } else {
                drawAddToCartButton(canvas)
            }
        }
    }

    private fun drawAddToCartButton(canvas: Canvas) {
        paint.color = Color.BLUE
        buttonRect.set(0f, 0f, widthPx, heightPx)
        canvas.drawRoundRect(buttonRect, CORNER_RADIUS, CORNER_RADIUS, paint)

        paint.color = Color.WHITE
        canvas.drawText(
            context.getString(R.string.to_cart),
            widthPx / 2f,
            heightPx / 2f + 10f,
            paint
        )
    }

    private fun drawCounter(canvas: Canvas, count: Int) {
        paint.color = Color.GRAY
        buttonRect.set(0f, 0f, widthPx, heightPx)
        canvas.drawRoundRect(buttonRect, CORNER_RADIUS, CORNER_RADIUS, paint)

        paint.color = Color.WHITE
        canvas.drawText(count.toString(), widthPx / 2f, heightPx / 2f + 15f, paint)

        paint.textSize = TEXT_SIZE_LARGE
        canvas.drawText(
            context.getString(R.string.minus),
            widthPx * DECREMENT_THRESHOLD,
            heightPx / 2f + 20f,
            paint
        )
        canvas.drawText(
            context.getString(R.string.plus),
            widthPx * INCREMENT_THRESHOLD,
            heightPx / 2f + 20f,
            paint
        )
        paint.textSize = TEXT_SIZE_NORMAL
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (inCartCount > 0) {
                    when {
                        event.x < widthPx * DECREMENT_PRESS_THRESHOLD -> {
                            decrementCount()
                            isDecrementing = true
                            startLongCounterClick()
                        }

                        event.x > widthPx * INCREMENT_PRESS_THRESHOLD -> {
                            incrementCount()
                            isIncrementing = true
                            startLongCounterClick()
                        }

                        else -> return true // Не обрабатываем нажатие вне кнопок "+" и "-"
                    }
                } else {
                    incrementCount() // Добавляем товар в корзину
                }
                return true
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                stopLongCounterClick()
                updateProductInCartCount?.invoke(inCartCount)
                performClick()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    private fun startLongCounterClick() {
        repeatInterval = INITIAL_INTERVAL
        handler.postDelayed(counterChangeRunnable, INITIAL_DELAY)
    }

    private fun stopLongCounterClick() {
        isIncrementing = false
        isDecrementing = false
        handler.removeCallbacks(counterChangeRunnable)
    }

    private fun incrementCount() {
        inCartCount = (inCartCount + COUNT_ONE).coerceAtMost(MAX_COUNT)
        invalidate()
    }

    private fun decrementCount() {
        inCartCount = (inCartCount - COUNT_ONE).coerceAtLeast(MIN_COUNT)
        invalidate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopLongCounterClick()
    }

    companion object {
        private const val MAX_COUNT = 999
        private const val MIN_COUNT = 0
        private const val DECREMENT_PRESS_THRESHOLD = 0.4f
        private const val INCREMENT_PRESS_THRESHOLD = 0.6f
        private const val DECREMENT_THRESHOLD = 0.2f
        private const val INCREMENT_THRESHOLD = 0.8f
        private const val CORNER_RADIUS = 20f
        private const val INITIAL_DELAY = 500L
        private const val INITIAL_INTERVAL = 100L
        private const val MIN_INTERVAL = 20L
        private const val INTERVAL_DECREMENT = 10L
        private const val TEXT_SIZE_NORMAL = 40f
        private const val TEXT_SIZE_LARGE = 60f
        private const val COUNT_ONE = 1
    }
}
