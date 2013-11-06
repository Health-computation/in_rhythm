package com.example.ds;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Set;
import com.androidplot.xy.XYGraphWidget;

/**
 * The power zone bar chart.
 *
 * Please note I am not proud of this class. It has terrible functionality leak (it references its enveloping container).
 * AsymmetricallyExpandingBox probably should handle height and width through use of bounds.
 * @author matt@strava.com (Matt Laroche)
 */
public class SleepBarChart extends BarChart {

    private static final String TAG = "SleepBarChart";
    private final int mRulerHeight;
    private final float mScaleFactor;
    private static final int RULER_TICK_WIDTH = 1;
    private static final int PADDING_BETWEEN_BUCKETS = 2;

    private final Resources mResources;

    public SleepBarChart(Context context) {
        super(context);
        //mZone = null;
        mResources = context.getResources();
        mScaleFactor = context.getResources().getDisplayMetrics().density;
        mRulerHeight = (int) (0 * mScaleFactor);
    }
    
    public SleepBarChart(Context context, AttributeSet set) {
        super(context, set);
        //mZone = null;
        mResources = context.getResources();
        mScaleFactor = context.getResources().getDisplayMetrics().density;
        mRulerHeight = (int) (0 * mScaleFactor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int[] buckets = {6, 7, 9, 5, 8, 7, 9};
        int chartHeight = getHeight();
        int chartWidth = getWidth();
        int barChartBaseline = chartHeight - mRulerHeight - PADDING_BETWEEN_BUCKETS;

        int numBuckets =  buckets.length;
        int approximateWidthPerBucket = (chartWidth - (numBuckets - 1) * PADDING_BETWEEN_BUCKETS) / numBuckets;

        int numExtraPixels = chartWidth - approximateWidthPerBucket * numBuckets - (numBuckets - 1) * PADDING_BETWEEN_BUCKETS;
        Set<Integer> bucketsWithExtraPixel = distributeExtraPixels(numExtraPixels, numBuckets);
        int maxTime = 9;
        // Assuming contiguousness. Jeff said is a fine assumption.
        int leftOffset = 0;
        //rectAndBuckets.clear();
        TypedArray colorArray = mResources.obtainTypedArray(R.array.demo_sleep_chart_colors);
        for (int i = 0, bucketsLength = buckets.length; i < bucketsLength; i++) {
            boolean hasExtraPixel = bucketsWithExtraPixel.contains(i);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(colorArray.getColor(i, Color.RED));

            int bucket = buckets[i];
            // We want the tallest bar to be 2/3 of the total space allocated to the chart.
            // TODO(matt): during design review, clear 2/3. Currently eyeballed.
            int heightOfThisBar = (bucket * barChartBaseline * 2) / (maxTime * 3);
            int rightEdge;
            if (i == buckets.length - 1) {
                rightEdge = chartWidth;
            } else {
                rightEdge = leftOffset + approximateWidthPerBucket + (hasExtraPixel ? 1 : 0);
            }
            Rect rect = new Rect(leftOffset, barChartBaseline - heightOfThisBar, rightEdge, barChartBaseline);
            // Should be a no-op, but a sanity check anyway.
            rect.sort();
            Pair<Integer, Integer> overlayCenter = Pair.create((rect.left + rect.right) / 2, rect.top);

            //rectAndBuckets.add(new LabelInformation(rect, overlayCenter, bucket));
            canvas.drawRect(rect, paint);

            leftOffset += approximateWidthPerBucket + PADDING_BETWEEN_BUCKETS + (hasExtraPixel ? 1 : 0);
        }
        drawRuler(canvas, buckets, bucketsWithExtraPixel, approximateWidthPerBucket, chartHeight - mRulerHeight, chartHeight);
        super.onDraw(canvas);
    }
/*
    @Override
    protected RelativeLayout getOverlayLayout(LayoutInflater inflator, ViewGroup parent) {
        inflator.inflate(R.layout.power_zones_overlay, parent);
        return (RelativeLayout) parent.findViewById(R.id.power_zones_overlay_root);
    }*/

    /*
    @Override
    protected void layoutTextInBox(View view, Zones.Zone.DistributionBucket bucket, int bucketNum) {
        int numSeconds = bucket.getTime();
        int percent = mMovingTime == 0 ? 0 : Math.round((float) 100 * bucket.getTime() / mMovingTime);
        String range = getRangeForBucket(bucket);

        ((TextView) view.findViewById(R.id.power_zones_overlay_power_range)).setText(range);
        ((TextView) view.findViewById(R.id.power_zones_overlay_time_text)).setText(FormatUtils.formatTime(numSeconds));
        ((TextView) view.findViewById(R.id.power_zones_overlay_percent_text)).setText(mResources.getString(R.string.stat_percent, percent));
    }*/

    /*
    @Override
    protected String getRangeForBucket(int bucket) {
        if (bucket == rectAndBuckets.get(0).mDistributionBucket) {
            return mResources.getString(R.string.activity_pace_power_zones_fragment_watt_label,
                    FormatUtils.formatDecimal(bucket.getMin(), 0));
        } else if (bucket == rectAndBuckets.get(rectAndBuckets.size() - 1).mDistributionBucket) {
            return mResources.getString(R.string.activity_pace_power_zones_fragment_power_bottom,
                    FormatUtils.formatDecimal(bucket.getMin(), 0));
        } else {
            return mResources.getString(R.string.activity_pace_power_zones_fragment_power_range,
                    FormatUtils.formatDecimal(bucket.getMin() + 1, 0),
                    FormatUtils.formatDecimal(bucket.getMax(), 0));
        }
    }*/

    private void drawRuler(Canvas canvas,int[] buckets, Set<Integer> bucketsWithExtraPixel,
                           int approximateWidthPerBucket, int rulerTop, int rulerBottom) {
        int chartWidth = canvas.getWidth();

        Paint whitePaint = new Paint();
        whitePaint.setAntiAlias(true);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(new Rect(0, rulerTop + PADDING_BETWEEN_BUCKETS, chartWidth, rulerTop), whitePaint);

        Paint rulerBackgroundPaint = new Paint();
        rulerBackgroundPaint.setAntiAlias(true);
        rulerBackgroundPaint.setColor(mResources.getColor(R.color.graph_ruler_background_color));
        rulerBackgroundPaint.setStyle(Paint.Style.FILL);

        canvas.drawRect(new Rect(0, rulerBottom, chartWidth, rulerTop), rulerBackgroundPaint);

        int leftOffset = 0;

        Paint rulerLabelPaint = new Paint();
        rulerLabelPaint.setAntiAlias(true);
        rulerLabelPaint.setColor(mResources.getColor(R.color.dark_text));
        rulerLabelPaint.setStyle(Paint.Style.FILL);
        rulerLabelPaint.setTextSize(rulerLabelPaint.getTextSize() * mScaleFactor);
        rulerLabelPaint.setTextAlign(Paint.Align.CENTER);

        // TODO(matt) Add a pixel above each tick.
        int topOfTicks = (int) (rulerTop + 3 * mScaleFactor);
        float heightOfLargeTick = 8 * mScaleFactor;
        float bottomOfLargeTick = topOfTicks + heightOfLargeTick;
        float heightOfSmallTick = (float) (0.65 * heightOfLargeTick);
        float bottomOfSmallTick = topOfTicks + heightOfSmallTick;

        float labelHeightOffset = topOfTicks + mRulerHeight / 2 + 12 / 2;

        for (int i = 0, bucketsLength = buckets.length; i < bucketsLength; i++) {
            boolean hasExtraPixel = bucketsWithExtraPixel.contains(i);

            int bucket = buckets[i];

            if (i != 0) {
                float bottomOfThisOne;
                // Draw a big tick at the start of every either bar - at the start of 0-50, 100-150, etc.
                if (i % 100 == 0) {
                    bottomOfThisOne = bottomOfLargeTick;

                    String labelText = Integer.toString(i);
                    canvas.drawText(labelText, leftOffset - (float) RULER_TICK_WIDTH / 2, labelHeightOffset, rulerLabelPaint);
                } else {
                    bottomOfThisOne = bottomOfSmallTick;
                }
                RectF majorTickMark = new RectF(leftOffset - RULER_TICK_WIDTH, topOfTicks, leftOffset, bottomOfThisOne);
                canvas.drawRect(majorTickMark, rulerLabelPaint);
            }
            float minorTickLeft = leftOffset + approximateWidthPerBucket / 2;
            canvas.drawRect(new RectF(minorTickLeft, topOfTicks, minorTickLeft + RULER_TICK_WIDTH, bottomOfSmallTick), rulerLabelPaint);
            leftOffset += approximateWidthPerBucket + PADDING_BETWEEN_BUCKETS + (hasExtraPixel ? 1 : 0);
        }
    }


}