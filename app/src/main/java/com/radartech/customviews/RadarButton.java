package com.radartech.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

import com.radartech.sw.R;


/**
 * Created on 18 Aug 2016 11:48 AM.
 *
 */
public class RadarButton extends Button {
    public RadarButton(Context context) {
        super(context);
    }

    public RadarButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs);
    }

    public RadarButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseAttributes(context, attrs);
    }

    private void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
        int typefaceValue = values.getInt(R.styleable.CustomView_typeface, 0);
        values.recycle();
        try {

        setTypeface(RadarTypefaceProvider.getInstance().getTypeface(context, typefaceValue));
        }catch (Exception e){
            //DO nothing
        }
    }
}
