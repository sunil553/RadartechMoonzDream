/*
 *
 *                  Moonzdream Confidential and Proprietary
 *
 *    This work contains valuable confidential and proprietary information.
 *    Disclosure, use or reproduction outside of Moonzdream, Inc. is prohibited
 *    except as authorized in writing. This unpublished work is protected by
 *    the laws of the United States and other countries. If publication occurs,
 *    following notice shall apply:
 *
 *                        Copyright 2016, Moonzdream Inc.
 *                            All rights reserved.
 *                   Freedom of Information Act(5 USC 522) and
 *            Disclosure of Confidential Information Generaly(18 USC 1905)
 *
 *    This material is being furnished in confidence by Moonzdream, Inc. The
 *    information disclosed here falls within Exemption (b)(4) of 5 USC 522
 *    and the prohibitions of 18 USC 1905
 */

package com.radartech.util;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

public class CustomProgressDialog {

	private Dialog mDialog;
	private TextView mTxtMessage;

	public CustomProgressDialog(Context context) {/*
		mDialog = new Dialog(context, R.style.NewDialog);
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mDialog.setContentView(R.layout.custom_progress_dialog);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.setCancelable(false);

		mTxtMessage = (TextView) mDialog.findViewById(R.id.txt_message);
	}

	public void showProgress(String message) {
		if(Utility.isValueNullOrEmpty(message))	{
			mTxtMessage.setVisibility(View.GONE);
		}
		else {
			mTxtMessage.setText("" + message);
		}

		if(mDialog != null) {
			mDialog.show();
		}
	}

	public void dismissProgress() {
		if(mDialog != null || mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}*/
	}
}