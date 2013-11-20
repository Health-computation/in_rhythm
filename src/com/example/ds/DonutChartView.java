package com.example.ds;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.util.Log;

public class DonutChartView  extends SleepDonutBaseView implements OnTouchListener {
    private static final String TAG = "DonutChartView";
    private final int DAYS = 7;
    private final Typeface mTypeface;

    private final int[] ZONE_COLORS;
    private final String[] DAY_ABBR = {"Su", "M", "T", "W", "Th", "F", "Sa"};

    private enum Rotate {
        OFF, CW, CCW, EITHER,
    }
    
    private class ArcInfo {
        float start;
        float width;
        float bisector;

        @Override
        public String toString() {
            return "start=" + start + ", bisector=" + bisector + ", width=" + width;
        }
    }
    
    private Rotate mRotate = Rotate.OFF;//initial rotation
    //Counter of how much the donut chart is currently rotated
    private float mCurrentAngle = 0;

    //Index of a single zone that takes 100% of all data (if applicable)
    private int mOneZoneIndex = -1;
    private int mZonesTimeSum = 7;
    private int mSelectedIndex = 0;
    private int mDesiredIndex = 1;

    //Maps zones to their start angle (in degrees) and the length of the arc as a % of 360degrees
    private final TreeMap<Integer, ArcInfo> mArcInfos = new TreeMap<Integer, ArcInfo>();
    private int[] mBuckets = {0};
    
    private ArrayList<RectF> DonutSpace=new ArrayList<RectF>();
    
    public DonutChartView(Context context) {
        super(context);
        setOnTouchListener(this);
        mTypeface = Typeface.createFromAsset(context.getAssets(),
	            "proximanova-bold-webfont.ttf");
        ZONE_COLORS = new int[DAYS];

        Resources res = context.getResources();
        TypedArray colorArray = res.obtainTypedArray(R.array.donut_chart_colors);
        for (int i = 0; i < DAYS; i++) {
        	ZONE_COLORS[i] = colorArray.getColor(i, Color.RED);
        }
    }
    
    public DonutChartView(Context context, AttributeSet set) {
        super(context, set);
        setOnTouchListener(this);
        mTypeface = Typeface.createFromAsset(context.getAssets(),
	            "proximanova-bold-webfont.ttf");

        ZONE_COLORS = new int[DAYS];
        Resources res = context.getResources();
        TypedArray colorArray = res.obtainTypedArray(R.array.donut_chart_colors);
        for (int i = 0; i < DAYS; i++) {
            ZONE_COLORS[i] = colorArray.getColor(i, Color.RED);
        }
    }
    
    @SuppressLint("DrawAllocation")
	@Override
    public void onDraw(Canvas canvas) {
        float size;
        float pivotStartPoint = -90;
        Calendar calendar = Calendar.getInstance();
        mBuckets = new int[calendar.get(calendar.DAY_OF_WEEK)];
        mDesiredIndex = calendar.get(calendar.DAY_OF_WEEK) - 1; 
        mMult = 1f;
        size = 75 * mMult;
        RectF donutRect = new RectF(xTop + size, yTop + size, xBottom - size, yBottom - size);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        if (mOneZoneIndex != -1) {
            mPaint.setColor(ZONE_COLORS[mOneZoneIndex]);
            mPaint.setStrokeWidth(100 * mMult);
            canvas.drawArc(donutRect, 0, 360, false, mPaint);
            return;
        }

        for (int workingIndex = 0; workingIndex < mBuckets.length; workingIndex++) {
            float arcLength = 0;
            if (true) {
                float ratio = 1 / 7f;
                arcLength = ratio * 360;

                mPaint.setColor(ZONE_COLORS[workingIndex]);
                pivotStartPoint = pivotStartPoint % 360;
                Path textPath = new Path();
                mPaint.setStyle(Paint.Style.STROKE);

                if (workingIndex == mDesiredIndex) {
                    // This offsets selected arc so than inner (colored arc) radius
                    // lines up with other arcs' inner radii. 12.5 is half the
                    // colored stroke width (125) minus the other stroke width (100).
                    float outerRadiusOffset = 12.5f * mMult;
                    RectF outerDonutRect = new RectF(xTop + size - outerRadiusOffset, yTop + size - outerRadiusOffset,
                            xBottom - size + outerRadiusOffset, yBottom - size + outerRadiusOffset);
                    System.out.println("The top is"+outerDonutRect.top);
                    System.out.println("The bottom is "+outerDonutRect.bottom);
                    System.out.println("The left is "+outerDonutRect.left);
                    System.out.println("The right is "+outerDonutRect.right);
                    DonutSpace.add(outerDonutRect);
                    
                    textPath.addArc(outerDonutRect, pivotStartPoint, arcLength);
                    // Draw an arc in normal size in highlighted color
                    
                    mPaint.setStrokeWidth(135 * mMult);
                    mPaint.setColor(Color.WHITE);
                    canvas.drawArc(outerDonutRect, pivotStartPoint, arcLength, false, mPaint);
                   

                    //Draw a slightly smaller overlaid arc in normal color for zone
                    
                    mPaint.setColor(ZONE_COLORS[workingIndex]);
                    mPaint.setStrokeWidth(125 * mMult);

                    canvas.drawArc(outerDonutRect, pivotStartPoint + 1, arcLength - 2, false, mPaint);
                    
                    

                    
                } else {
                    mPaint.setStrokeWidth(100 * mMult);
                    // Overdraw very slightly to avoid off-by-one issues.
                    textPath.addArc(donutRect, pivotStartPoint, arcLength);
                    canvas.drawArc(donutRect, pivotStartPoint, arcLength + 0.25f, false, mPaint);
                }
                mPaint.setColor(getResources().getColor(R.color.donut));
                
                //canvas.drawText("M", (float)(centerX -  + (radius * Math.cos(medianAngle))), (float)(centerY + (radius * Math.sin(medianAngle))), mPaint);

                mPaint.setStrokeWidth(20);
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setTextSize(80);
                mPaint.setTypeface(mTypeface);
                canvas.drawTextOnPath(DAY_ABBR[workingIndex], textPath, 50, 30, mPaint);
                //canvas.drawText("M", xTop, yTop, mPaint);
            }

            if (!mArcInfos.containsKey(workingIndex)) {
                ArcInfo angle = new ArcInfo();
                angle.start = pivotStartPoint;
                angle.width = arcLength;
                angle.bisector = -pivotStartPoint + 90 - (arcLength / 2);
                if (angle.bisector < 0) {
                    angle.bisector = getDistanceFromZero(angle.bisector);
                }
                mArcInfos.put(workingIndex, angle);
            }
            pivotStartPoint += arcLength;
        }

        if (mRotate != Rotate.OFF) {
            ArcInfo angles = mArcInfos.get(mDesiredIndex);
            if (angles == null) {
                return;
            }

            float desiredAngle = angles.bisector;
            float currentAnchorAngle = mCurrentAngle;
            mCurrentAngle = desiredAngle;

            if (Math.abs(currentAnchorAngle-desiredAngle) > 180) {
                Log.d(TAG, "OVERROTATE");
            }

            RotateAnimation rotateAnimation = new RotateAnimation(currentAnchorAngle, desiredAngle,
                    Animation.RELATIVE_TO_SELF, 0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);
            //rotateAnimation.setInterpolator(new OvershootInterpolator(.8f));//default is 2.0f
            rotateAnimation.setDuration(750);
            rotateAnimation.setFillAfter(true);

            startAnimation(rotateAnimation);

            mRotate = Rotate.OFF;
            mSelectedIndex = mDesiredIndex;
        }
    }
    
    @Override
    public boolean onTouch(View view, MotionEvent event) {
    	
    	int index=-1;
    	if (event.getAction() == MotionEvent.ACTION_DOWN)  {
    		
    		float x=event.getX();
        	float y=event.getY();
        	System.out.println("X= "+x+","+"Y= "+y);
        	
    	  for(int i=0; i<DonutSpace.size();i++){
    		
    		if(DonutSpace.get(i).contains(x,y)){
    			
    			index=i;
    			System.out.println("Day="+index);
    			System.out.println("The top coordinate is"+DonutSpace.get(i).top);
    			System.out.println("The bottom coordinate is "+DonutSpace.get(i).bottom);
    			System.out.println("The Left coordinate is "+DonutSpace.get(i).left);
    			System.out.println("The right coordinate is "+DonutSpace.get(i).right);
    			
    			
    		 }
    	 }
    	  
    	  invalidate();  
    	
    	}
    	
    	
    	
    	Intent dataPage = new Intent(SleepProbeApplication2.getAppContext(), DayDataActivity.class);
    	dataPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	dataPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	if(index!=-1){
    	SleepProbeApplication2.getAppContext().startActivity(dataPage);
    	}
    	return true;
    }
    
    public int getDay(MotionEvent event){
    	
    	int index=-1;
    		
    	
    	
    	if (event.getAction() == MotionEvent.ACTION_DOWN)  {
    		
    		float x=event.getX();
        	float y=event.getY();
        	System.out.println("X= "+x+","+"Y= "+y);
        	
    	  for(int i=0; i<DonutSpace.size();i++){
    		
    		if(DonutSpace.get(i).contains(x,y)){
    			
    			System.out.println("i="+i);
    			return i;
    			
    		 }
    	 }
    	  
    	  invalidate();  
    	
    	}
    	
    	return index;
    	
    }
    
   
    //negative
    private float getDistanceFromZero(float f) {
        if (f < 0) {
            return 360 + f;
        } else if (f > 360) {
            return f % 360;
        } else {
            return f;
        }
    }
    
    
    public static double angleForPoint(PointF point, PointF center, int height) {
        center.y = height - center.y;
        point.y = height - point.y;

        point.x -= center.x;
        point.y -= center.y;

        double hyp = FloatMath.sqrt((point.x * point.x) + (point.y * point.y));
        float adj = point.x;

        double angle = Math.acos(adj / hyp);

        double pi2 = Math.PI / 2;
        if (point.y > 0) {
            angle = pi2 - angle;
        } else {
            angle = pi2 + angle;
        }

        while (angle < 0) {
            angle += Math.PI * 2;
        }
        return angle;
    }


}
