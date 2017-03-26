package com.radartech.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created on 17 Nov 2016 10:24 AM.
 *
 */

public class ProgressCurveView extends ImageView {

    private Paint strokePaint;
    private Path mPath;
    private static final int RADIUS = 10;
    private ArrayList<PointF> curvedPoints;

    public ProgressCurveView(Context context) {
        super(context);
        init();
    }

    public ProgressCurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        curvedPoints = new ArrayList<>();

        mPath = new Path();

        strokePaint = new Paint();
        strokePaint.setColor(Color.CYAN);
        strokePaint.setStrokeWidth(2);
        strokePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int maxX = getWidth();
        int maxY = getHeight();
        // Calculate the middle point on imageView
        int halfX = (int) maxX / 2;
        int halfY = (int) maxY / 2;

        // Set the line color and draw a horizontal axis
        //  canvas.drawLine(0, halfY, maxX, halfY, strokePaint);
        // Draw the sine wave
        strokePaint.setColor(Color.RED);
        for (int i = 0; i < maxX; i++) {
            PointF point = new PointF();
            point.set(i, getNormalizedSine(i, halfY, maxX));
            canvas.drawPoint(i, getNormalizedSine(i, halfY, maxX), strokePaint);
        }

        strokePaint.setColor(Color.BLACK);
        for (int i = 0; i < maxX; i++) {
            canvas.drawLine(i, maxY, i, getNormalizedSine(i, halfY, maxX), strokePaint);
        }
    }

    /**
     * Calculates the sine value on sine curve
     *
     * @param x     the value along the x-axis
     * @param halfY the value of the y-axis
     * @param maxX  the width of the x-axis
     * @return int, y axis point on sine curve
     */
    int getNormalizedSine(int x, int halfY, int maxX) {
        double piDouble = 2 * Math.PI;
        double factor = piDouble / maxX;
        return (int) (int) (Math.sin(x * factor) * (RADIUS) + halfY);
    }

}
