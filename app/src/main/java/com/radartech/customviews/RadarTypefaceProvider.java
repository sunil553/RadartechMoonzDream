package com.radartech.customviews;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created on 18 Aug 2016 11:23 AM.
 *
 */
public class RadarTypefaceProvider {
    public final static int UBUNTU = 100;
    public final static int UBUNTU_BOLD = 101;
    public final static int UBUNTU_MEDIUM = 102;
    public final static int UBUNTU_REGULAR = 103;
    private static RadarTypefaceProvider sTypeFace;
    private Typeface mUbuntuBold;
    private Typeface mUbuntuMedium;
    private Typeface mUbuntuRegular;
    private Typeface mUbuntuLight;

    private RadarTypefaceProvider() {
    }

    public static RadarTypefaceProvider getInstance() {
        if (sTypeFace == null) {
            sTypeFace = new RadarTypefaceProvider();
        }
        return sTypeFace;
    }

    public Typeface getTypeface(Context context, int typefaceValue)
            throws IllegalArgumentException {
        switch (typefaceValue) {
            case UBUNTU_BOLD:
                if (mUbuntuBold == null) {
                    mUbuntuBold = Typeface.createFromAsset(context.getAssets(),
                            "fonts/ubuntu_bold.ttf");
                }
                return mUbuntuBold;
            case UBUNTU_MEDIUM:
                if (mUbuntuMedium == null) {
                    mUbuntuMedium = Typeface.createFromAsset(context.getAssets(),
                            "fonts/ubuntu_medium.ttf");
                }
                return mUbuntuMedium;
            case UBUNTU_REGULAR:
                if (mUbuntuRegular == null) {
                    mUbuntuRegular = Typeface.createFromAsset(context.getAssets(),
                            "fonts/ubuntu_regular.ttf");
                }
                return mUbuntuRegular;
            case UBUNTU:
            default:
                if (mUbuntuLight == null) {
                    mUbuntuLight = Typeface.createFromAsset(context.getAssets(),
                            "fonts/ubuntu_light.ttf");
                }
                return mUbuntuLight;
        }
    }
}
