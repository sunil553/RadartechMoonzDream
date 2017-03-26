package com.radartech.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.radartech.activity.BaseActivity;
import com.radartech.activity.LoginActivity;
import com.radartech.model.version.VersionNumberResponse;
import com.radartech.network.RetrofitException;
import com.radartech.sw.R;

import java.io.IOException;

/**
 * Created on 25 Oct 2016 11:39 AM.
 *
 * @author PurpleTalk India Pvt. Ltd.
 */

public class UiUtils {

    private static final String UNAUTHORIZED_MESSAGE = "Authorization has been denied for this request.";
    private static ProgressDialog mPprogressDialog;

    public static void displayProgress(Activity activity, String message) {
        dismissProgress();
        mPprogressDialog = new ProgressDialog(activity);
        mPprogressDialog.setMessage(message);
        mPprogressDialog.setCancelable(false);
        mPprogressDialog.show();
    }

    public static void dismissProgress() {
        if (mPprogressDialog != null) {
            mPprogressDialog.cancel();
        }
    }

    /**
     * This method is used to launch another activity from current activity
     */
    public static void launchActivity(Activity activityContext, Class targetClassName, Bundle bundle, boolean
            isFinishPrevious) {
        Intent intent = new Intent(activityContext, targetClassName);

        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activityContext.startActivity(intent);
        if (isFinishPrevious) {
            activityContext.finish();
        }
    }

    public static void resetAllActivities(Context activityContext, Class targetClassName) {
        Intent intent = new Intent(activityContext, targetClassName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activityContext.startActivity(intent);
    }

    public static void logMessage(String message) {
        Log.e("TAG", message);
    }

    /**
     * Method to push the mFragment
     *
     * @param containerId
     * @param fragment
     * @param addToBackStack
     * @param animStyle
     */
    public static void pushFragment(Activity activityContext, int containerId, Fragment fragment, Bundle bundle, boolean
            addToBackStack, int animStyle) {
        FragmentManager supportFragmentManager = ((BaseActivity) activityContext).getSupportFragmentManager();
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        /*switch (animStyle) {
            case AppConstants.ANIMATION_FADE:
            case AppConstants.ANIMATION_LEFT_TO_RIGHT:
            case AppConstants.ANIMATION_BOTTOM_TO_TOP:
                ft.setCustomAnimations(R.anim.frag_enter, R.anim.frag_exit,
                        R.anim.frag_pop_enter, R.anim.frag_pop_exit);
                break;

            case AppConstants.ANIMATION_NONE:
                break;
            default:
                break;
        }*/
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        ft.replace(containerId, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack) {
            ft.addToBackStack(fragment.getClass().getSimpleName());
        }
        ft.commit();
    }

    /**
     * Method hides the soft keyboard
     *
     * @param context
     * @param v
     */
    public static void hideSoftKeyboard(Context context, View v) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void showToast(Context context, String message) {
        if (message != null || !message.isEmpty()) {
            Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Displays retrofit error message
     * @param context
     * @param e
     */
    public static void showErrorMessage(Context context, Throwable e) {
        RetrofitException error = (RetrofitException) e;
        int errorCode = 0;
        try {
            errorCode = Integer.parseInt(error.getMessage());
        }catch (Exception exception){

        }

        if (errorCode == 401) {
            showToast(context, UNAUTHORIZED_MESSAGE);
        } else {
            if (error.getKind() == RetrofitException.Kind.NETWORK){
                showToast(context, context.getString(R.string.no_internet_connection));
            }else {
                try {
                    VersionNumberResponse response = error.getErrorBodyAs(VersionNumberResponse.class);
                    if (response == null) {
                        showToast(context, context.getString(R.string.no_internet_connection));
                    } else {
                        int errorcode = response.getErrorcode();
                        showToast(context, ("shiva : " + response.getErrorcode() != null ? errorcode + "" : ""));
                        if (errorcode == 1003) {// means auth token expire have to login again
                            SharedPreferenceUtils.clear();
                            UiUtils.resetAllActivities(context, LoginActivity.class);
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                    logMessage("IO Exception");
                }
            }
        }
    }
}
