package levkaantonov.com.study.doomfire

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.graphics.createBitmap
import java.util.*

class DoomFire @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private lateinit var temp: IntArray
    private lateinit var bitmapPixels: IntArray
    private lateinit var bitmap: Bitmap
    private var sh = 0
    private var sw = 0

    private val scale = 5
    private val rand = Random()
    private val paint = Paint()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        sh = h / scale
        sw = w / scale
        bitmap = createBitmap(sw, sh)
        temp = IntArray(sw * sh)
        for (x in sw * sh - sw until sw * sh)
            temp[x] = palette.size - 1
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawFire(canvas)
    }

    private fun drawFire(canvas: Canvas) {
        bitmapPixels = IntArray(sw * sh)
        val paletteSize = palette.size - 1
        var t: Int
        for (y in 0 until sh - 1) {
            for (x in 0 until sw) {
                spreadFire(x, y)
                t = Math.min(paletteSize, Math.max(0, temp[y * sw + x]))
                bitmapPixels[sw * y + x] = palette[t]
            }
        }
        bitmap.setPixels(bitmapPixels, 0, sw, 0, 0, sw, sh)
        canvas.scale(scale.toFloat(), scale.toFloat())
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        invalidate()
    }


    private fun spreadFire(x: Int, y: Int) {
        val rx = rand.nextInt(3)
        val ry = rand.nextInt(6)
        val dx = Math.min(sw - 1, Math.max(0, x + rx - 1))
        val dy = Math.min(sh - 1, y + ry)
        val dt = -(rx and 1)
        temp[y * sw + x] = Math.max(0, temp[dy * sw + dx] + dt)
    }

    companion object {
        private val palette = arrayOf(
            0xff070707.toInt(),
            0xff1F0707.toInt(),
            0xff2F0F07.toInt(),
            0xff470F07.toInt(),
            0xff571707.toInt(),
            0xff671F07.toInt(),
            0xff771F07.toInt(),
            0xff8F2707.toInt(),
            0xff9F2F07.toInt(),
            0xffAF3F07.toInt(),
            0xffBF4707.toInt(),
            0xffC74707.toInt(),
            0xffDF4F07.toInt(),
            0xffDF5707.toInt(),
            0xffDF5707.toInt(),
            0xffD75F07.toInt(),
            0xffD75F07.toInt(),
            0xffD7670F.toInt(),
            0xffCF6F0F.toInt(),
            0xffCF770F.toInt(),
            0xffCF7F0F.toInt(),
            0xffCF8717.toInt(),
            0xffC78717.toInt(),
            0xffC78F17.toInt(),
            0xffC7971F.toInt(),
            0xffBF9F1F.toInt(),
            0xffBF9F1F.toInt(),
            0xffBFA727.toInt(),
            0xffBFA727.toInt(),
            0xffBFAF2F.toInt(),
            0xffB7AF2F.toInt(),
            0xffB7B72F.toInt(),
            0xffB7B737.toInt(),
            0xffCFCF6F.toInt(),
            0xffDFDF9F.toInt(),
            0xffEFEFC7.toInt(),
            0xffFFFFFF.toInt()
        )
    }
}