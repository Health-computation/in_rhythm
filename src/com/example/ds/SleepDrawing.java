package com.example.ds;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SleepDrawing extends View {
    private final LayoutInflater mLayoutInflator;
    private final Context mContext;
    public SleepDrawing(Context context) {
        super(context);
        mLayoutInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }
    
    public SleepDrawing(Context context, AttributeSet set) {
        super(context, set);
        mLayoutInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int chartHeight = getHeight();
        int chartWidth = getWidth();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.good));
        Rect rect = new Rect(0, 0, chartWidth, chartHeight);
        canvas.drawRect(rect, paint);	
        paint.setColor(Color.WHITE);
        for(int i = 0; i < 30; ++i){
        	//Log.d("FUN", Integer.toString( chartWidth);
        	int position = (int) (Math.random() * chartWidth * 0.5);
            rect = new Rect(position, 0, position + 5, chartHeight);
            canvas.drawRect(rect, paint);	
        }
        
        for(int i = 0; i < 30; ++i){
        	//Log.d("FUN", Integer.toString( chartWidth);
        	int position = (int) ((Math.random() * chartWidth * 0.1) + (chartWidth * 0.8));
            rect = new Rect(position, 0, position + 5, chartHeight);
            canvas.drawRect(rect, paint);	
        }


        /*
        for(int i = 0; i < 100; ++i){
        	int position = (int) (Math.random() *60) * chartWidth;
            rect = new Rect(position, 0, position + 10, chartHeight);
            canvas.drawRect(rect, paint);	
        }*/

    }

}
