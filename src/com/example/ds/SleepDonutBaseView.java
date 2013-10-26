package com.example.ds;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

abstract class SleepDonutBaseView extends View {
	
    //Scaling multiplier for arc lengths of donut chart
    protected float mMult = .75f;
    //Padding around donut chart
    protected int mPadding = 20;
    
    //Defines box w & h that contains the Donut Chart
    protected int mDim;
    //Upper left coordinates of box containing Donut Chart
    protected int xTop;
    protected int yTop;
    //Lower right coordinates of box containing Donut Chart
    protected int xBottom;
    protected int yBottom;
    protected int xCenter;
    protected int mDimDiff;
    
    protected Paint mPaint;
    
    public SleepDonutBaseView(Context context, AttributeSet set) {
        super(context, set);
        
        mPaint = new Paint();
    }
    
    public SleepDonutBaseView(Context context) {
        super(context);
        
        mPaint = new Paint();
    }
    
    /**
     * Depending on the density, adjust donut settings for padding and arc width
     * 
     * @param density
     * @see DisplayMetrics
     */
    public void setDensity(int density) {
        if (density <= DisplayMetrics.DENSITY_LOW) {
            mMult = .15f;
        } else if (density <= DisplayMetrics.DENSITY_MEDIUM) {
            mMult = this instanceof DonutChartView ? .3f : .4f;//DonutChartView uses .3f
        } else if (density <= DisplayMetrics.DENSITY_HIGH) {
            mMult = .55f;
        } else {
            mMult = .8f;
        }
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasure = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthMeasure, widthMeasure);
    }
    
    
    /*
     * Save dimension values for donut chart box  
     */
    @Override     
    public void onSizeChanged(int w, int h, int oldw, int oldh) { 
        super.onSizeChanged(w, h, oldw, oldh);
        mDim = Math.min(w, h) - mPadding;
        xTop = (w - mDim)/2;
        yTop = (h - mDim)/2;
        xBottom = xTop + mDim;
        yBottom = yTop + mDim;
        xCenter = w / 2;
        mDimDiff = Math.abs(w-h);
    }
    
}

