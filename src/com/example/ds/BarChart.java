package com.example.ds;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BarChart extends View {

    private static final int MARGIN_FROM_SIDE_OF_BOX_TO_SIDE_OF_GRAPH = 5;
    private static final int MARGIN_FROM_SIDE_OF_TEXT_BOX_TO_SIDE_OF_OVERLAY = 20;
    private static final int MARGIN_FROM_TOP_OF_TEXT_BOX_TO_TOP_OF_OVERLAY = 9;
    private static final int MARGIN_FROM_BOTTOM_OF_TEXT_BOX_TO_BOTTOM_OF_OVERLAY = 22;
    private static final String TAG = "ZoneBarChart";

    private View mCurrentOverlay;
    private View mCurrentOverlayTextBox;
   // private Zones.Zone.DistributionBucket mCurrentOverlayBucket;

   // protected Zones.Zone mZone;
    protected long mMovingTime;
    //protected List<LabelInformation> rectAndBuckets;

    private final Resources mResources;
    private final Context mContext;
    private final LayoutInflater mLayoutInflator;
    private final Paint mRectHighlightPaint;
    private final List<Rect> mHighlightRects;
    //private IntArgumentCallback mZoneSelectedCallback;

    public BarChart(Context context) {
        super(context);
        mLayoutInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        mResources = context.getResources();
       // rectAndBuckets = new ArrayList();
        mRectHighlightPaint = new Paint();
        mRectHighlightPaint.setStrokeWidth(3);
        mRectHighlightPaint.setColor(Color.WHITE);
        mRectHighlightPaint.setStyle(Paint.Style.STROKE);
        mHighlightRects = new ArrayList();
    }
    
    public BarChart(Context context, AttributeSet set) {
        super(context, set);
        mLayoutInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        mResources = context.getResources();
       // rectAndBuckets = new ArrayList();
        mRectHighlightPaint = new Paint();
        mRectHighlightPaint.setStrokeWidth(3);
        mRectHighlightPaint.setColor(Color.WHITE);
        mRectHighlightPaint.setStyle(Paint.Style.STROKE);
        mHighlightRects = new ArrayList();
    }
/*
    public void setInformation(Zones.Zone zone, long movingTime) {
        if (zone == null) {
            Log.e(TAG, "got a null zone in setInformation(). Later story will talk about the no-data-yet for zones - that's the issue.");
            return;
        }
        Preconditions.checkNotNull(zone, "zone should not be null");
        Preconditions.checkArgument(movingTime >= 0, "ride should not be null");
        this.mZone = zone;
        this.mMovingTime = movingTime;
        this.invalidate();
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Rect rect : mHighlightRects) {
            canvas.drawRect(rect, mRectHighlightPaint);
        }
    }

   // protected abstract RelativeLayout getOverlayLayout(LayoutInflater mLayoutInflator, ViewGroup parent);

    //protected abstract void layoutTextInBox(View view, Zones.Zone.DistributionBucket bucket, int bucketNum);

    /*
    protected void drawOverlayBubble(FrameLayout parent, Pair<Integer, Integer> overlayPointTarget, Zones.Zone.DistributionBucket bucket, int bucketNum) {
        int viewWidth = getWidth();

        RelativeLayout overlayTextBox = getOverlayLayout(mLayoutInflator, parent); 
        layoutTextInBox(overlayTextBox, bucket, bucketNum);
        overlayTextBox.measure(getWidth(), getHeight());
        int textBoxWidth = overlayTextBox.getMeasuredWidth();
        int textBoxHeight = overlayTextBox.getMeasuredHeight();
        // Need to lay it out to measure it, but want it on top later in the method.
        parent.removeView(overlayTextBox);

        AsymmetricallyExpandingBox box = new AsymmetricallyExpandingBox(
                mResources.getDrawable(R.drawable.graph_bubble_left),
                mResources.getDrawable(R.drawable.graph_bubble_right));

        int backgroundWidth = textBoxWidth + 2 * MARGIN_FROM_SIDE_OF_TEXT_BOX_TO_SIDE_OF_OVERLAY;
        int backgroundHeight = textBoxHeight + MARGIN_FROM_TOP_OF_TEXT_BOX_TO_TOP_OF_OVERLAY + MARGIN_FROM_BOTTOM_OF_TEXT_BOX_TO_BOTTOM_OF_OVERLAY;

        box.setBounds(0, 0, backgroundWidth, backgroundHeight);
        box.setHeight(backgroundHeight);
        box.setWidth(backgroundWidth);
        FrameLayout.LayoutParams backgroundLayoutParams = new FrameLayout.LayoutParams(backgroundWidth, backgroundHeight);
        FrameLayout.LayoutParams textLayoutParams = new FrameLayout.LayoutParams(textBoxWidth, textBoxHeight);

        backgroundLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        textLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        backgroundLayoutParams.topMargin = overlayPointTarget.second - backgroundHeight;
        textLayoutParams.topMargin = backgroundLayoutParams.topMargin + MARGIN_FROM_TOP_OF_TEXT_BOX_TO_TOP_OF_OVERLAY;

        int barCenter = overlayPointTarget.first;

        // TODO(matt): Figure out how to deal with the overlay wanting to be as wide as the screen. Currently assuming
        // it's touching either the left or the right.
        if (barCenter - backgroundWidth / 2 - MARGIN_FROM_SIDE_OF_BOX_TO_SIDE_OF_GRAPH < 0) {
            // Bar is too far left to get the whole bar in - shrink to the left!
            backgroundLayoutParams.leftMargin = MARGIN_FROM_SIDE_OF_BOX_TO_SIDE_OF_GRAPH;
            box.setLeftWidth(barCenter - MARGIN_FROM_SIDE_OF_BOX_TO_SIDE_OF_GRAPH);
        } else if (barCenter + backgroundWidth / 2 + MARGIN_FROM_SIDE_OF_BOX_TO_SIDE_OF_GRAPH > viewWidth) {
            // Bar is too far right to get the whole box in - shrink to the right!
            backgroundLayoutParams.leftMargin = viewWidth - MARGIN_FROM_SIDE_OF_BOX_TO_SIDE_OF_GRAPH - backgroundWidth;
            box.setRightWidth(viewWidth - barCenter - MARGIN_FROM_SIDE_OF_BOX_TO_SIDE_OF_GRAPH);
        } else {
            backgroundLayoutParams.leftMargin = barCenter - backgroundWidth / 2;
        }
        textLayoutParams.leftMargin = backgroundLayoutParams.leftMargin + MARGIN_FROM_SIDE_OF_TEXT_BOX_TO_SIDE_OF_OVERLAY;

        ImageView overlay = new ImageView(mContext);

        overlay.setBackgroundDrawable(box);
        mCurrentOverlay = overlay;
        mCurrentOverlayTextBox = overlayTextBox;

        parent.addView(mCurrentOverlay, backgroundLayoutParams);
        parent.addView(mCurrentOverlayTextBox, textLayoutParams);
    }*/

    protected Set<Integer> distributeExtraPixels(int numExtraPixels, int numBuckets) {
        if (numExtraPixels == 0) {
            return Collections.emptySet();
        }
        if (numExtraPixels >= numBuckets) {
            Log.d(TAG, "distributeExtraPixels was called with more extra pixels than numBuckets!");
        }
        Set<Integer> bucketsWithExtraPixels = new HashSet<Integer>(numExtraPixels);

        for (int i = 0; i < numExtraPixels; i++) {
            bucketsWithExtraPixels.add(numBuckets * i / numExtraPixels);
        }

        return bucketsWithExtraPixels;
    }
    /*
    @Override
    public synchronized boolean onTouchEvent(MotionEvent event) {

        if (mZone == null) {
            return false;
        }

        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }
        float touchX = event.getX();

        int rectSelectedIndex = -1;
        for (int i = 0, rectAndBucketsSize = rectAndBuckets.size(); i < rectAndBucketsSize; i++) {
            LabelInformation labelInformation = rectAndBuckets.get(i);
            Rect r = labelInformation.mTouchTarget;

            // Touch goes all the way to the top.
            if (r.left < touchX && r.right >= touchX) {
                rectSelectedIndex = i;
                break;
            }
        }
        if (rectSelectedIndex >= 0) {
            highlightRect(rectSelectedIndex);

            if (mZoneSelectedCallback != null) {
                mZoneSelectedCallback.run(rectSelectedIndex);
            }
        }

        return true;
    }
 	*/
    public void highlightRect(int rect) {
        mHighlightRects.clear();

        if (mCurrentOverlay != null) {
            mCurrentOverlay.setVisibility(View.GONE);
            ((ViewGroup) mCurrentOverlay.getParent()).removeView(mCurrentOverlay);
            mCurrentOverlay = null;
        }
        if (mCurrentOverlayTextBox != null) {
            mCurrentOverlayTextBox.setVisibility(View.GONE);
            ((ViewGroup) mCurrentOverlayTextBox.getParent()).removeView(mCurrentOverlayTextBox);
            mCurrentOverlayTextBox = null;
        }

        //LabelInformation labelInformation = rectAndBuckets.get(rect);
        //Zones.Zone.DistributionBucket bucket = labelInformation.mDistributionBucket;

        //drawOverlayBubble((FrameLayout) getParent(), labelInformation.mLabelTarget, bucket, rect);
        //Rect highlightRect = generateHighlightRectFromRect(labelInformation.mTouchTarget);
        //mHighlightRects.add(highlightRect);

        //mCurrentOverlayBucket = labelInformation.mDistributionBucket;
        ((FrameLayout) getParent()).invalidate();
    }

    //protected abstract String getRangeForBucket(Zones.Zone.DistributionBucket bucket);

    // TODO(matt): doc
    /*
    protected static int maxTime(Zones.Zone.DistributionBucket[] buckets) {
        int maxTime = 1;
        for (Zones.Zone.DistributionBucket bucket : buckets) {
            if (bucket.getTime() > maxTime) {
                maxTime = bucket.getTime();
            }
        }
        return maxTime;
    }

    protected static class LabelInformation {
        public final Rect mTouchTarget;
        public final Pair<Integer, Integer> mLabelTarget;
        public final Zones.Zone.DistributionBucket mDistributionBucket;

        public LabelInformation(Rect mTouchTarget, Pair<Integer, Integer> mLabelTarget, Zones.Zone.DistributionBucket mDistributionBucket) {
            this.mTouchTarget = mTouchTarget;
            this.mLabelTarget = mLabelTarget;
            this.mDistributionBucket = mDistributionBucket;
        }
    }*/

    private Rect generateHighlightRectFromRect(Rect rectangleToHighlight) {
        Rect highlightRect = new Rect(rectangleToHighlight);
        float halfStrokeWidth = mRectHighlightPaint.getStrokeWidth() / 2;
        highlightRect.left = (int) (highlightRect.left + halfStrokeWidth);
        highlightRect.right = (int) (highlightRect.right - halfStrokeWidth);
        if (highlightRect.top - highlightRect.bottom < 4 * halfStrokeWidth) {
            highlightRect.top = (int) (highlightRect.top + halfStrokeWidth);
        } else {
            highlightRect.top = (int) (highlightRect.bottom - 3 * halfStrokeWidth);
        }
        highlightRect.bottom = (int) (highlightRect.bottom - halfStrokeWidth);
        return highlightRect;
    }

}