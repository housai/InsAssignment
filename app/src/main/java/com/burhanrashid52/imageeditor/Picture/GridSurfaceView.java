package com.burhanrashid52.imageeditor.Picture;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GridSurfaceView extends SurfaceView {
    /**
     * 竖向线条数,可通过布局属性设置列数
     */
    private int linesX = 2;
    /**
     * 竖向线条数，可通过布局属性设置行数
     */
    private int linesY = 2;

    private int width;
    private int height;
    private Paint mPaint = null;
    public GridSurfaceView(Context context) {
        this(context, null);
    }

    public GridSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.gridSurfaceView);
        linesX = a.getInteger(R.styleable.gridSurfaceView_linesX, linesX);
        linesY = a.getInteger(R.styleable.gridSurfaceView_linesY, linesY);
        a.recycle();
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth() - this.getResources().getDimensionPixelOffset(R.dimen.classes_dp_40);
        height = wm.getDefaultDisplay().getHeight();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = width/(linesX + 1);
        int y = height/(linesY + 1);
        for(int i = 1 ; i <= linesX ; i++){
            canvas.drawLine(x * i, 0, x * i, height, mPaint);
        }

        for (int i = 1; i <= linesY; i++) {
            canvas.drawLine(0, y * i, width, y * i, mPaint);
        }
    }

}
