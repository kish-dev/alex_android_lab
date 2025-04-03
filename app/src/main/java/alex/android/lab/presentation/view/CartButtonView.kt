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

    private var inCartCount = 0
    private var isInCart = false
    private var isDataLoaded = false

    var updateProductInCartCount: ((Int) -> Unit)? = null

    private var cartState: CartState = CartState.ADD_TO_CART
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        textSize = 40f
        textAlign = Paint.Align.CENTER
    }
    private val buttonRect = RectF()

    init {
        setOnClickListener {
            when (cartState) {
                CartState.ADD_TO_CART -> {
                    cartState = CartState.COUNTER
                    incrementCount()
                }

                CartState.COUNTER -> {
                }
            }
            invalidate()
        }
    }

    val setProductInCartData: ((Int, Boolean) -> Unit) = { inCartCount, isInCart ->
        this.inCartCount = inCartCount
        this.isInCart = isInCart
        if (isInCart) {
            cartState = CartState.COUNTER
        }
        isDataLoaded = true
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isDataLoaded) {
            when (cartState) {
                CartState.ADD_TO_CART -> drawAddToCartButton(canvas)
                CartState.COUNTER -> drawCounter(canvas)
            }
        }
    }

    private fun drawAddToCartButton(canvas: Canvas) {
        paint.color = Color.BLUE
        buttonRect.set(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(buttonRect, 20f, 20f, paint)

        paint.color = Color.WHITE
        paint.textSize = 40f
        canvas.drawText(context.getString(R.string.to_cart), width / 2f, height / 2f + 10f, paint)
    }

    private fun drawCounter(canvas: Canvas) {
        paint.color = Color.GRAY
        buttonRect.set(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(buttonRect, 20f, 20f, paint)

        paint.color = Color.WHITE
        paint.textSize = 40f
        canvas.drawText(inCartCount.toString(), width / 2f, height / 2f + 15f, paint)

        paint.textSize = 60f
        canvas.drawText(context.getString(R.string.minus), width * 0.2f, height / 2f + 20f, paint)
        canvas.drawText(context.getString(R.string.plus), width * 0.8f, height / 2f + 20f, paint)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (cartState == CartState.COUNTER && event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x
            if (x < width * 0.5f) {
                decrementCount()
                return true
            } else if (x > width * 0.5f) {
                incrementCount()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun incrementCount() {
        inCartCount++
        updateProductInCartCount?.invoke(inCartCount)
        invalidate()
    }

    private fun decrementCount() {
        if (inCartCount > 1) {
            inCartCount--
        } else {
            inCartCount = 0
            isInCart = false
            cartState = CartState.ADD_TO_CART
        }
        updateProductInCartCount?.invoke(inCartCount)
        invalidate()
    }

    enum class CartState {
        ADD_TO_CART,
        COUNTER,
    }
}
