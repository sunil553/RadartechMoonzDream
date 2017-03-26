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

package com.radartech.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.radartech.activity.GeofenceItemActivity;
import com.radartech.activity.UpdateDeviceInfoActivty;
import com.radartech.adapter.InfoListAdapter;
import com.radartech.model.SettingSpinnerItemData;
import com.radartech.model.device.settings.BaseDeviceItem;
import com.radartech.model.device.settings.DeviceItemResponse;
import com.radartech.network.RadarTechService;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.SharedPreferenceUtils;
import com.radartech.util.UiUtils;
import com.radartech.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.radartech.util.AppConstants.FragmentConstants.CAPTURE_CAMERA_ACTIVITY_REQUEST_CODE;
import static com.radartech.util.AppConstants.FragmentConstants.CAPTURE_GALLERY_ACTIVITY_REQUEST_CODE;


/**
 * Created by office on 9/22/2016.
 */
public class SettingsFragment extends BaseFragment implements AdapterView.OnItemClickListener {


    private static final String APP_TAG = "RADARTECH";
    @BindView(R.id.car_info_name_plate)
    TextView mCarNoPlate;
    @BindView(R.id.title)
    TextView tvTitle;
    @BindView(R.id.back_button)
    ImageView img_setting;
    @BindView(R.id.car_info_update)
    ImageView imupdate;
    @BindView(R.id.car_info_icon)
    ImageView imcaricon;
    @BindView(R.id.car_info_list)
    ListView listView;

    private int aDeviceId;
    private DeviceItemResponse devicesItemResponses = null;

    private int mOverSpeed;
    private String aDeviceName;
    private int mParkTime;
    private ArrayList<SettingSpinnerItemData> settingsInfoList = new ArrayList();
    private InfoListAdapter infoListAdapter;
    private String currentCapturedImageFilePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initButterKnife(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        callDeviceDetailsAPI();
    }


    private void initViews() {
        Bundle bundle = this.getArguments();
        aDeviceId = bundle.getInt(IntentConstants.DEVICE_ID);
        aDeviceName = bundle.getString(IntentConstants.DEVICE_NAME);

        mOverSpeed = SharedPreferenceUtils.readInteger(aDeviceId + PreferenceConstants.DEVICE_OVER_SPEED);
        tvTitle.setText(aDeviceName);

        infoListAdapter = new InfoListAdapter(getActivity(), settingsInfoList);
        listView.setAdapter(infoListAdapter);
        listView.setOnItemClickListener(this);
        try {
            createImageToPath(SharedPreferenceUtils.readString(aDeviceId + APP_TAG));

        }catch (NullPointerException e){

        }
        imcaricon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogForImage();
            }
        });

        imupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(IntentConstants.CAR_NAME, devicesItemResponses.getDevicename());
                bundle.putString(IntentConstants.CAR_NUMB, devicesItemResponses.getCarnum());
                bundle.putString(IntentConstants.CAR_ID, String.valueOf(devicesItemResponses.getDeviceid()));

                UiUtils.launchActivity(getActivity(), UpdateDeviceInfoActivty.class, bundle, false);
            }
        });

    }

    private void openDialogForImage() {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                getActivity());
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        launchGallery();
                    }
                });

        myAlertDialog.setNegativeButton("",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        launchCamera();
                    }
                });
        myAlertDialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_CAMERA_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                createImageToPath(currentCapturedImageFilePath);
            }
        } else if (requestCode == CAPTURE_GALLERY_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String sourcePath = cursor.getString(columnIndex);
                try {
                    copyFile(sourcePath, currentCapturedImageFilePath);
                    createImageToPath(currentCapturedImageFilePath);
                    SharedPreferenceUtils.writeString(aDeviceId + APP_TAG, currentCapturedImageFilePath);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                cursor.close();
            }
        }
    }
    /**
     * copies content from source file to destination file
     *
     * @param sourceFile
     * @param destFile
     * @throws IOException
     */
    private void copyFile(String sourceFile, String destFile) throws IOException {
        File sourceFile1 =  new File(sourceFile);
        File destFile1 =  new File(destFile);
        if (!sourceFile1.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile1).getChannel();
        destination = new FileOutputStream(destFile1).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }

    }

    private void createImageToPath(final String absolutePath) {
        File isImageExistes = new File(absolutePath);
        if (!isImageExistes.exists()) {
            return;
        }
        Glide.with(imcaricon.getContext())
                .load(absolutePath) // or URI/path
                .error(R.drawable.car_icon)
                .into(imcaricon); //imageview to set thumbnail to

    }

    public void copyFile1(String selectedImagePath, String targetPath) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {


            in = new FileInputStream(selectedImagePath);
            out = new FileOutputStream(targetPath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        }  catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    //opens default camera app
    public void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri()); // set the image file name
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_CAMERA_ACTIVITY_REQUEST_CODE);
        }
    }

    public void launchGallery() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri()); // set the image file name
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_GALLERY_ACTIVITY_REQUEST_CODE);
        }
    }
    //creates temporary file to save captured image in the directory
    public Uri getPhotoFileUri() {
        File mediaFile = new File(getActivity().getCacheDir() + File.separator + aDeviceId + APP_TAG + ".jpg");
        currentCapturedImageFilePath = mediaFile.toString();
        return Uri.fromFile(mediaFile);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 6) {
            dialogOverSpeed();
        } else if (position == 7) {
            Bundle bundle = new Bundle();
            bundle.putInt(IntentConstants.DEVICE_ID, aDeviceId);
            bundle.putString(IntentConstants.DEVICE_NAME, aDeviceName);
            UiUtils.launchActivity(getActivity(), GeofenceItemActivity.class, bundle, false);
        } else if (position == 8) {
            dialogParkTime();

        }
    }

    @OnClick(R.id.back_button)
    public void onClickAlarmBack() {
        getActivity().finish();
    }

    private void dialogOverSpeed() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dailog_overspeed, null);
        final EditText etUsername = (EditText) alertLayout.findViewById(R.id.et_username);
        String stringOverSpeed = String.valueOf(mOverSpeed);
        etUsername.setText(stringOverSpeed);
        etUsername.setSelection(stringOverSpeed.length());
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Please Input the overspeed threshold");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (etUsername.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please give valid Speed", Toast.LENGTH_LONG);
                    return;
                } else {
                    mOverSpeed = Integer.parseInt(etUsername.getText().toString());
                    callHandledDeviceOverSpeedAPI();
                }


            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void dialogParkTime() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dailog_overspeed, null);
        final EditText etUsername = (EditText) alertLayout.findViewById(R.id.et_username);
        mParkTime = SharedPreferenceUtils.readInteger(aDeviceId + PreferenceConstants.PARK_TIME);
        etUsername.setText(String.valueOf(mParkTime));
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Please Input the Parking time threshold in Minutes");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (etUsername.getText().toString().equals("") || etUsername.getText().toString().equals("0")) {
                    Toast.makeText(getContext(), "Please give valid Park Time", Toast.LENGTH_LONG);
                    return;
                } else {
                    mParkTime = Integer.parseInt(etUsername.getText().toString());
                    SharedPreferenceUtils.writeInteger(aDeviceId + PreferenceConstants.PARK_TIME, mParkTime);
                    addItemsTolist();//referesh list after changing park time
                }


            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }


    private void addItemsTolist() {
        if (devicesItemResponses == null) {
            return;
        }
        settingsInfoList.clear();
        int overspeed = devicesItemResponses.getOverspeed();
        settingsInfoList.add(new SettingSpinnerItemData("SimCard : " + devicesItemResponses.getSimcard(), InfoConstants.ARROW_INVISIBLE));
        settingsInfoList.add(new SettingSpinnerItemData("IMEI : " + devicesItemResponses.getImei(), InfoConstants.ARROW_INVISIBLE));
        settingsInfoList.add(new SettingSpinnerItemData("Type : " + devicesItemResponses.getDevicetype(), InfoConstants.ARROW_INVISIBLE));
        settingsInfoList.add(new SettingSpinnerItemData("Activation Time : " + Utility.getDateFromLong(devicesItemResponses.getFactorytime(), AppConstants.YYYY_MM_DD), InfoConstants.ARROW_INVISIBLE));
        settingsInfoList.add(new SettingSpinnerItemData("Expiry Date : " + Utility.getDateFromLong(devicesItemResponses.getPlattime(), AppConstants.YYYY_MM_DD), InfoConstants.ARROW_INVISIBLE));
        settingsInfoList.add(new SettingSpinnerItemData("Enable Time :" + Utility.getDateFromLong(devicesItemResponses.getOnlinetime(), AppConstants.YYYY_MM_DD), InfoConstants.ARROW_INVISIBLE));
        settingsInfoList.add(new SettingSpinnerItemData("OverSpeed Threshold: " + overspeed + "km/h", InfoConstants.ARROW_VISIBLE));
        settingsInfoList.add(new SettingSpinnerItemData("GEO-fence: ", InfoConstants.ARROW_VISIBLE));
        settingsInfoList.add(new SettingSpinnerItemData("Park Time: " + SharedPreferenceUtils.readInteger(aDeviceId + PreferenceConstants.PARK_TIME), InfoConstants.ARROW_VISIBLE));
        infoListAdapter.notifyDataSetChanged();
        mCarNoPlate.setText(devicesItemResponses.getCarnum() + "\n" + devicesItemResponses.getDevicename());

    }

    /*TODO */
    private void callDeviceDetailsAPI() {
        Observable<BaseDeviceItem> loginObservable = RadarTechService.getInstance().getAPIService()
                .deviceInfoAPI(SharedPreferenceUtils.readString(PreferenceConstants.PREF_COOKIES), String.valueOf(aDeviceId), AppConstants.MAP_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscription subscribe = loginObservable.subscribe(new Observer<BaseDeviceItem>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
                UiUtils.showErrorMessage(getActivity(), e);
            }

            @Override
            public void onNext(BaseDeviceItem response) {
                if (response.getErrorcode() == 0) {
                    devicesItemResponses = response.getRecord();
                    addItemsTolist();

//                       mAdapter.setDevicesList(devicesListResponses);
                } else {
                    UiUtils.showToast(getActivity(), response.getErrorcode().toString());
                }
                UiUtils.dismissProgress();
            }
        });

        addSubscription(subscribe);
    }


    private void callHandledDeviceOverSpeedAPI() {

        Observable<Object> loginObservable = RadarTechService.getInstance().getAPIService()
                .overSpeedAPI(String.valueOf(aDeviceId), String.valueOf(mOverSpeed))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscription subscribe = loginObservable.subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
                callDeviceDetailsAPI();
            }

            @Override
            public void onError(Throwable e) {
                mOverSpeed = SharedPreferenceUtils.readInteger(aDeviceId + PreferenceConstants.DEVICE_OVER_SPEED);
                UiUtils.dismissProgress();
                UiUtils.showErrorMessage(getActivity(), e);
            }

            @Override
            public void onNext(Object response) {
                try {
                    JSONObject jsono = new JSONObject(response.toString());
                    int errorcode = jsono.getInt("errorcode");
                    if (errorcode == 0) {
                        SharedPreferenceUtils.writeInteger(aDeviceId + AppConstants.PreferenceConstants.DEVICE_OVER_SPEED, mOverSpeed);
                        UiUtils.showToast(getActivity(), getString(R.string.api_overspeed_message));
                        Utility.showLogMessage("data", "datadata" + "SUCCESSFULLY READ");
                    } else {
                        UiUtils.logMessage("unsuccessfully read");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        addSubscription(subscribe);
    }


}