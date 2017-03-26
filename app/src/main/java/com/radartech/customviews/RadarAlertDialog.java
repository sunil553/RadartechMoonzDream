package com.radartech.customviews;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.radartech.callbacks.AlertDialogCallback;
import com.radartech.sw.R;


/**
 * Created on 24 Aug 2016 4:03 PM.
 *
 */
public class RadarAlertDialog extends Dialog implements View.OnClickListener {
    private final Context context;
    private RadarButton mFirstButtonTextView, mSecondButtonTextView;
    //All final attributes
    private final String title; // optional
    private final String content; // optional
    private final String button1text; // required
    private final String button2text; // required
    private final AlertDialogCallback mAlertDialogCallback;
    ; // required

    /**
     * @param builder
     */
    public RadarAlertDialog(MagellanAlertDialogBuilder builder) {
        super(builder.context);
        this.context = builder.context;
        this.title = builder.title;
        this.content = builder.content;
        this.button1text = builder.button1Text;
        this.button2text = builder.button2Text;
        this.mAlertDialogCallback = builder.callback;
    }

    public void showDialog() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.magellan_dialog, null);
        RadarTextView titleTv = ((RadarTextView) contentView.findViewById(R.id.dialog_title_textView));
        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
        } else {
            titleTv.setVisibility(View.GONE);
        }
        RadarTextView contentTv = ((RadarTextView) contentView.findViewById(R.id.dialog_content_textView));
        if (!TextUtils.isEmpty(content)) {
            contentTv.setVisibility(View.VISIBLE);
            contentTv.setText(content);
        } else {
            contentTv.setVisibility(View.GONE);
        }
        mFirstButtonTextView = (RadarButton) contentView.findViewById(R.id.dialog_first_button);
        if (!TextUtils.isEmpty(button1text)) {
            mFirstButtonTextView.setText(button1text);
        }
        mFirstButtonTextView.setOnClickListener(this);
        mSecondButtonTextView = (RadarButton) contentView.findViewById(R.id.dialog_second_button);
        if (!TextUtils.isEmpty(button2text)) {
            mSecondButtonTextView.setText(button2text);
        }
        mSecondButtonTextView.setOnClickListener(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(contentView);
        setCancelable(false);
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_shadow);
        show();
    }

    @Override
    public void onClick(View view) {
        if (mAlertDialogCallback == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.dialog_first_button:
                mAlertDialogCallback.alertDialogCallback(AlertDialogCallback.OK);
                break;
            case R.id.dialog_second_button:
                mAlertDialogCallback.alertDialogCallback(AlertDialogCallback.CANCEL);
                break;
            default:
                break;
        }
    }

    public static class MagellanAlertDialogBuilder {

        private final Context context;
        private final AlertDialogCallback callback;
        private String title;
        private String content;
        private String button1Text;
        private String button2Text;

        public MagellanAlertDialogBuilder(Context context, AlertDialogCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        public MagellanAlertDialogBuilder title(String title) {
            this.title = title;
            return this;
        }

        public MagellanAlertDialogBuilder content(String content) {
            this.content = content;
            return this;
        }

        public MagellanAlertDialogBuilder button1Text(String button1Text) {
            this.button1Text = button1Text;
            return this;
        }

        public MagellanAlertDialogBuilder button2Text(String button2Text) {
            this.button2Text = button2Text;
            return this;
        }

        //Return the finally constructed User object
        public RadarAlertDialog build() {
            RadarAlertDialog dialog = new RadarAlertDialog(this);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            return dialog;
        }
    }
}
