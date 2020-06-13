package eu.oncreate.bingie.fragment.list

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import eu.oncreate.bingie.R

@Suppress("MagicNumber", "TooManyFunctions")
class DonutProgress : View {

    private var finishedPaint: Paint? = null
    private var unfinishedPaint: Paint? = null
    private var innerCirclePaint: Paint? = null

    private var textPaint: Paint? = null
    private var innerBottomTextPaint: Paint? = null

    private val finishedOuterRect = RectF()
    private val unfinishedOuterRect = RectF()

    var attributeResourceId = 0
    var showText = false
    var textSize = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var textColor = 0
        set(value) {
            field = value
            invalidate()
        }
    var innerBottomTextColor = 0
        set(value) {
            field = value
            invalidate()
        }
    var progress = 0f
        set(value) {
            field = if (value > max) {
                value.rem(max.toFloat())
            } else {
                value
            }
            invalidate()
        }
    var max = 0
        set(value) {
            if (value > 0) {
                field = value
                invalidate()
            }
        }
    var finishedStrokeColor = 0
        set(value) {
            field = value
            invalidate()
        }
    var unfinishedStrokeColor = 0
        set(value) {
            field = value
            invalidate()
        }
    var startingDegree = 0
        set(value) {
            field = value
            invalidate()
        }
    var finishedStrokeWidth = 0f
        set(value) {
            field = value
            invalidate()
        }
    var unfinishedStrokeWidth = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var strokePadding = 0f
    var innerBackgroundColor = 0
        set(value) {
            field = value
            invalidate()
        }
    var prefixText = ""
        set(value) {
            field = value
            invalidate()
        }
    var suffixText = "%"
        set(value) {
            field = value
            invalidate()
        }
    var text: String? = null
        set(value) {
            field = value
            invalidate()
        }
    var innerBottomTextSize = 0f
        set(value) {
            field = value
            invalidate()
        }
    var innerBottomText: String? = null
        set(value) {
            field = value
            invalidate()
        }
    private var innerBottomTextHeight = 0f

    private var defaultStrokeWidth = 0f
    private val defaultFinishedColor = Color.rgb(66, 145, 241)
    private val defaultUnfinishedColor = Color.rgb(204, 204, 204)
    private val defaultTextColor = Color.rgb(66, 145, 241)
    private val defaultInnerBottomTextColor = Color.rgb(66, 145, 241)
    private val defaultInnerBackgroundColor = Color.TRANSPARENT
    private val defaultMax = 100
    private val defaultStartingdegree = 0
    private var defaultTextSize = 0f
    private var defaultInnerBottomTextSize = 0f
    private var minSize = 0

    companion object {
        private const val INSTANCE_STATE = "saved_instance"
        private const val INSTANCE_TEXT_COLOR = "text_color"
        private const val INSTANCE_TEXT_SIZE = "text_size"
        private const val INSTANCE_TEXT = "text"
        private const val INSTANCE_INNER_BOTTOM_TEXT_SIZE = "inner_bottom_text_size"
        private const val INSTANCE_INNER_BOTTOM_TEXT = "inner_bottom_text"
        private const val INSTANCE_INNER_BOTTOM_TEXT_COLOR = "inner_bottom_text_color"
        private const val INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color"
        private const val INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color"
        private const val INSTANCE_MAX = "max"
        private const val INSTANCE_PROGRESS = "progress"
        private const val INSTANCE_SUFFIX = "suffix"
        private const val INSTANCE_PREFIX = "prefix"
        private const val INSTANCE_FINISHED_STROKE_WIDTH = "finished_stroke_width"
        private const val INSTANCE_UNFINISHED_STROKE_WIDTH = "unfinished_stroke_width"
        private const val INSTANCE__STROKE_PADDING = "donut_stroke_padding"
        private const val INSTANCE_BACKGROUND_COLOR = "inner_background_color"
        private const val INSTANCE_STARTING_DEGREE = "starting_degree"
        private const val INSTANCE_INNER_DRAWABLE = "inner_drawable"
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        defaultTextSize = sp2px(resources, 18f)
        minSize = dp2px(resources, 100f).toInt()
        defaultStrokeWidth = dp2px(resources, 10f)
        defaultInnerBottomTextSize = sp2px(resources, 18f)

        val attributes = context.theme
            .obtainStyledAttributes(attrs, R.styleable.DonutProgress, defStyleAttr, 0)
        initByAttributes(attributes)
        attributes.recycle()

        initPainters()
    }

    private fun initPainters() {
        if (showText) {
            textPaint = TextPaint()
            textPaint?.color = textColor
            textPaint?.textSize = textSize
            textPaint?.isAntiAlias = true
            innerBottomTextPaint = TextPaint()
            innerBottomTextPaint?.color = innerBottomTextColor
            innerBottomTextPaint?.textSize = innerBottomTextSize
            innerBottomTextPaint?.isAntiAlias = true
        }
        finishedPaint = Paint()
        finishedPaint?.color = finishedStrokeColor
        finishedPaint?.style = Paint.Style.STROKE
        finishedPaint?.isAntiAlias = true
        finishedPaint?.strokeWidth = finishedStrokeWidth
        unfinishedPaint = Paint()
        unfinishedPaint?.color = unfinishedStrokeColor
        unfinishedPaint?.style = Paint.Style.STROKE
        unfinishedPaint?.isAntiAlias = true
        unfinishedPaint?.strokeWidth = unfinishedStrokeWidth
        innerCirclePaint = Paint()
        innerCirclePaint?.color = innerBackgroundColor
        innerCirclePaint?.isAntiAlias = true
    }

    private fun initByAttributes(attributes: TypedArray) {
        finishedStrokeColor = attributes.getColor(
            R.styleable.DonutProgress_donut_finished_color,
            defaultFinishedColor
        )
        unfinishedStrokeColor = attributes.getColor(
            R.styleable.DonutProgress_donut_unfinished_color,
            defaultUnfinishedColor
        )
        showText = attributes.getBoolean(R.styleable.DonutProgress_donut_show_text, true)
        attributeResourceId =
            attributes.getResourceId(R.styleable.DonutProgress_donut_inner_drawable, 0)
        max = (attributes.getInt(R.styleable.DonutProgress_donut_max, defaultMax))
        progress = attributes.getFloat(R.styleable.DonutProgress_donut_progress, 0f)
        finishedStrokeWidth = attributes.getDimension(
            R.styleable.DonutProgress_donut_finished_stroke_width,
            defaultStrokeWidth
        )
        unfinishedStrokeWidth = attributes.getDimension(
            R.styleable.DonutProgress_donut_unfinished_stroke_width,
            defaultStrokeWidth
        )
        strokePadding = attributes.getDimension(
            R.styleable.DonutProgress_donut_stroke_padding,
            0f
        )
        if (showText) {
            initTextAttributes(attributes)
        }
        innerBottomTextSize = attributes.getDimension(
            R.styleable.DonutProgress_donut_inner_bottom_text_size,
            defaultInnerBottomTextSize
        )
        innerBottomTextColor = attributes.getColor(
            R.styleable.DonutProgress_donut_inner_bottom_text_color,
            defaultInnerBottomTextColor
        )
        innerBottomText = attributes.getString(R.styleable.DonutProgress_donut_inner_bottom_text)
        startingDegree = attributes.getInt(
            R.styleable.DonutProgress_donut_circle_starting_degree,
            defaultStartingdegree
        )
        innerBackgroundColor = attributes.getColor(
            R.styleable.DonutProgress_donut_background_color,
            defaultInnerBackgroundColor
        )
    }

    private fun initTextAttributes(attributes: TypedArray) {
        if (attributes.getString(R.styleable.DonutProgress_donut_prefix_text) != null) {
            prefixText = attributes.getString(R.styleable.DonutProgress_donut_prefix_text)!!
        }
        if (attributes.getString(R.styleable.DonutProgress_donut_suffix_text) != null) {
            suffixText = attributes.getString(R.styleable.DonutProgress_donut_suffix_text)!!
        }
        if (attributes.getString(R.styleable.DonutProgress_donut_text) != null) {
            text = attributes.getString(R.styleable.DonutProgress_donut_text)
        }
        textColor =
            attributes.getColor(R.styleable.DonutProgress_donut_text_color, defaultTextColor)
        textSize = attributes.getDimension(
            R.styleable.DonutProgress_donut_text_size,
            defaultTextSize
        )
        innerBottomTextSize = attributes.getDimension(
            R.styleable.DonutProgress_donut_inner_bottom_text_size,
            defaultInnerBottomTextSize
        )
        innerBottomTextColor = attributes.getColor(
            R.styleable.DonutProgress_donut_inner_bottom_text_color,
            defaultInnerBottomTextColor
        )
        innerBottomText =
            attributes.getString(R.styleable.DonutProgress_donut_inner_bottom_text)
    }

    override fun invalidate() {
        initPainters()
        super.invalidate()
    }

    private fun getProgressAngle() = progress / max.toFloat() * 360f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec))

        // TODO calculate inner circle height and then position bottom text at the bottom (3/4)
        innerBottomTextHeight = height - height * 3 / 4.toFloat()
    }

    private fun measure(measureSpec: Int): Int {
        var result: Int
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        if (mode == MeasureSpec.EXACTLY) {
            result = size
        } else {
            result = minSize
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size)
            }
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val delta = Math.max(finishedStrokeWidth, unfinishedStrokeWidth)
        finishedOuterRect[delta + strokePadding, delta + strokePadding, width - strokePadding - delta] =
            height - delta - strokePadding
        unfinishedOuterRect[delta + strokePadding, delta + strokePadding, width - strokePadding - delta] =
            height - delta - strokePadding
        val innerCircleRadius = (width - Math.min(
            finishedStrokeWidth,
            unfinishedStrokeWidth
        ) + Math.abs(finishedStrokeWidth - unfinishedStrokeWidth)) / 2f
        canvas.drawCircle(
            width / 2.0f,
            height / 2.0f,
            innerCircleRadius,
            innerCirclePaint!!
        )
        canvas.drawArc(
            finishedOuterRect,
            startingDegree.toFloat(),
            getProgressAngle(),
            false,
            finishedPaint!!
        )
        canvas.drawArc(
            unfinishedOuterRect,
            startingDegree + getProgressAngle(),
            360 - getProgressAngle(),
            false,
            unfinishedPaint!!
        )
        if (showText) {
            val text =
                if (text != null) text else prefixText + progress + suffixText
            if (!TextUtils.isEmpty(text)) {
                val textHeight = textPaint!!.descent() + textPaint!!.ascent()
                canvas.drawText(
                    text!!,
                    (width - textPaint!!.measureText(text)) / 2.0f,
                    (width - textHeight) / 2.0f,
                    textPaint!!
                )
            }
            if (!TextUtils.isEmpty(innerBottomText)) {
                innerBottomTextPaint!!.textSize = innerBottomTextSize
                val bottomTextBaseline =
                    height - innerBottomTextHeight - (textPaint!!.descent() + textPaint!!.ascent()) / 2
                canvas.drawText(
                    innerBottomText!!,
                    (width - innerBottomTextPaint!!.measureText(innerBottomText)) / 2.0f,
                    bottomTextBaseline,
                    innerBottomTextPaint!!
                )
            }
        }
        if (attributeResourceId != 0) {
            val bitmap = BitmapFactory.decodeResource(resources, attributeResourceId)
            canvas.drawBitmap(
                bitmap,
                (width - bitmap.width) / 2.0f,
                (height - bitmap.height) / 2.0f,
                null
            )
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState())
        bundle.putInt(INSTANCE_TEXT_COLOR, textColor)
        bundle.putFloat(INSTANCE_TEXT_SIZE, textSize)
        bundle.putFloat(INSTANCE_INNER_BOTTOM_TEXT_SIZE, innerBottomTextSize)
        bundle.putFloat(
            INSTANCE_INNER_BOTTOM_TEXT_COLOR,
            innerBottomTextColor.toFloat()
        )
        bundle.putString(INSTANCE_INNER_BOTTOM_TEXT, innerBottomText)
        bundle.putInt(INSTANCE_INNER_BOTTOM_TEXT_COLOR, innerBottomTextColor)
        bundle.putInt(INSTANCE_FINISHED_STROKE_COLOR, finishedStrokeColor)
        bundle.putInt(INSTANCE_UNFINISHED_STROKE_COLOR, unfinishedStrokeColor)
        bundle.putInt(INSTANCE_MAX, max)
        bundle.putInt(INSTANCE_STARTING_DEGREE, startingDegree)
        bundle.putFloat(INSTANCE_PROGRESS, progress)
        bundle.putString(INSTANCE_SUFFIX, suffixText)
        bundle.putString(INSTANCE_PREFIX, prefixText)
        bundle.putString(INSTANCE_TEXT, text)
        bundle.putFloat(INSTANCE_FINISHED_STROKE_WIDTH, finishedStrokeWidth)
        bundle.putFloat(INSTANCE_UNFINISHED_STROKE_WIDTH, unfinishedStrokeWidth)
        bundle.putInt(INSTANCE_BACKGROUND_COLOR, innerBackgroundColor)
        bundle.putFloat(INSTANCE__STROKE_PADDING, strokePadding)
        bundle.putInt(INSTANCE_INNER_DRAWABLE, attributeResourceId)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            val bundle = state
            textColor = bundle.getInt(INSTANCE_TEXT_COLOR)
            textSize = bundle.getFloat(INSTANCE_TEXT_SIZE)
            innerBottomTextSize = bundle.getFloat(INSTANCE_INNER_BOTTOM_TEXT_SIZE)
            innerBottomText = bundle.getString(INSTANCE_INNER_BOTTOM_TEXT)
            innerBottomTextColor = bundle.getInt(INSTANCE_INNER_BOTTOM_TEXT_COLOR)
            finishedStrokeColor = bundle.getInt(INSTANCE_FINISHED_STROKE_COLOR)
            unfinishedStrokeColor = bundle.getInt(INSTANCE_UNFINISHED_STROKE_COLOR)
            finishedStrokeWidth = bundle.getFloat(INSTANCE_FINISHED_STROKE_WIDTH)
            unfinishedStrokeWidth = bundle.getFloat(INSTANCE_UNFINISHED_STROKE_WIDTH)
            innerBackgroundColor = bundle.getInt(INSTANCE_BACKGROUND_COLOR)
            strokePadding = bundle.getFloat(INSTANCE__STROKE_PADDING)
            attributeResourceId = bundle.getInt(INSTANCE_INNER_DRAWABLE)
            initPainters()
            max = (bundle.getInt(INSTANCE_MAX))
            startingDegree = bundle.getInt(INSTANCE_STARTING_DEGREE)
            progress = bundle.getFloat(INSTANCE_PROGRESS)
            prefixText = bundle.getString(INSTANCE_PREFIX)!!
            suffixText = bundle.getString(INSTANCE_SUFFIX)!!
            text = bundle.getString(INSTANCE_TEXT)
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE))
            return
        }
        super.onRestoreInstanceState(state)
    }

    fun setDonutProgress(percent: String) {
        if (!TextUtils.isEmpty(percent)) {
            progress = (percent.toInt().toFloat())
        }
    }

    fun dp2px(resources: Resources, dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale + 0.5f
    }

    fun sp2px(resources: Resources, sp: Float): Float {
        val scale = resources.displayMetrics.scaledDensity
        return sp * scale
    }
}
