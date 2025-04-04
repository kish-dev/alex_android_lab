package alex.android.lab.presentation.view

import alex.android.lab.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
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
        textSize = 40f
        textAlign = Paint.Align.CENTER
    }
    private val buttonRect: RectF by lazy { RectF() }

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
        if(isDataLoaded) {
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
        canvas.drawRoundRect(buttonRect, 20f, 20f, paint)

        paint.color = Color.WHITE
        canvas.drawText(
            context.getString(R.string.to_cart),
            widthPx / 2f,
            heightPx / 2f + 10f,
            paint
        )
        invalidate()
    }

    private fun drawCounter(canvas: Canvas, count: Int) {
        paint.color = Color.GRAY
        buttonRect.set(0f, 0f, widthPx, heightPx)
        canvas.drawRoundRect(buttonRect, 20f, 20f, paint)

        paint.color = Color.WHITE
        canvas.drawText(count.toString(), widthPx / 2f, heightPx / 2f + 15f, paint)

        paint.textSize = 60f
        canvas.drawText(
            context.getString(R.string.minus),
            widthPx * 0.2f,
            heightPx / 2f + 20f,
            paint
        )
        canvas.drawText(
            context.getString(R.string.plus),
            widthPx * 0.8f,
            heightPx / 2f + 20f,
            paint
        )
        paint.textSize = 40f
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (inCartCount > 0) {
                when {
                    event.x < widthPx * 0.4f -> decrementCount()
                    event.x > widthPx * 0.6f -> incrementCount()
                }
            } else {
                incrementCount()
            }
            performClick()
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    private fun incrementCount() {
        inCartCount = (inCartCount + 1).coerceAtMost(999)
        updateProductInCartCount?.invoke(inCartCount)
        invalidate()
    }

    private fun decrementCount() {
        inCartCount = (inCartCount - 1).coerceAtLeast(0)
        updateProductInCartCount?.invoke(inCartCount)
        invalidate()
    }
}
