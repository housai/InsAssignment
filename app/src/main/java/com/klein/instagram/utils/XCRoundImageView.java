package com.klein.instagram.utils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.klein.instagram.R;

/**
 * Custom Round ImageView，can be used as a part of layout
 * @author caizhiming
 *
 */
public class XCRoundImageView extends android.support.v7.widget.AppCompatImageView{

    private Paint mPaint;//set paint
    private Bitmap mBitmap;//get image bitmap
    private int width, height;//get width height of layout
    private int mOuterRing = 0;//set outer ring size
    private int outerRingAlpha = 30;//set outer ring transparency
    private int color = Color.BLUE;//set outer ring colour

    public XCRoundImageView(Context context) {
        this(context, null);
    }

    public XCRoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XCRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public int getmOuterRing() {
        return mOuterRing;
    }

    public void setmOuterRing(int mOuterRing) {
        this.mOuterRing = mOuterRing;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getOuterRingAlpha() {
        return outerRingAlpha;
    }

    public void setOuterRingAlpha(int outerRingAlpha) {
        this.outerRingAlpha = outerRingAlpha;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Get width height of space
        width = View.getDefaultSize(getMeasuredWidth(), widthMeasureSpec);
        height = View.getDefaultSize(getMeasuredHeight(), heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        Bitmap bitmap;
        if (drawable != null) {
            if (mBitmap != null) {
                bitmap = mBitmap;
            } else {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            }
            Matrix matrix = new Matrix();
            //Set image zoom ratio
            if (mOuterRing>0){
                if (width > height) {
                    matrix.setScale((float) (height) / bitmap.getHeight(), (float) (height) / bitmap.getHeight());
                } else {
                    matrix.setScale((float) (width) / bitmap.getWidth(), (float) (width) / bitmap.getWidth());
                }
            }else {
                if (width > height) {
                    matrix.setScale((float) (width) / bitmap.getWidth(), (float) (width) / bitmap.getWidth());
                } else {
                    matrix.setScale((float) (height) / bitmap.getHeight(), (float) (height) / bitmap.getHeight());
                }
            }

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            //Set circular image
            bitmap = toRoundBitmap(bitmap);

            //When outer ring width is >0, must check that ring width + image width is not larger than layout
            if (mOuterRing+bitmap.getWidth()>width){
                mOuterRing = (width-bitmap.getWidth())/2;
            }

            //Draw outer ring
            Paint cPaint = new Paint();
            cPaint.setStrokeWidth(2);
            cPaint.setColor(color);
            cPaint.setAlpha(outerRingAlpha);
            // cPaint.setFilterBitmap(true);
            cPaint.setAntiAlias(true);
            canvas.drawCircle(bitmap.getWidth()/2+mOuterRing,bitmap.getHeight()/2+mOuterRing,bitmap.getWidth()/2+mOuterRing,cPaint);

            //Draw image
            canvas.drawBitmap(bitmap, mOuterRing, mOuterRing, mPaint);

        } else {
            super.onDraw(canvas);
        }
    }

    /**
     * Convert image to round bitmap
     *
     * @param bitmap
     * @return
     */
    public Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst_left, dst_top, dst_right, dst_bottom);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

}