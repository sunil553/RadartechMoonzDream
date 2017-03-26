package com.radartech.network;

import android.support.annotation.Nullable;

import com.radartech.model.APIBaseResponse;
import com.radartech.model.PlayBackResponse;
import com.radartech.model.alarm.BaseAlarmResponse;
import com.radartech.model.device.settings.BaseDeviceItem;
import com.radartech.model.devices.list.BaseDevicesList;
import com.radartech.model.geofence.BaseGeoFence;
import com.radartech.model.jpushalarm.AlarmIdItem;
import com.radartech.model.track.BaseTrackResponse;
import com.radartech.model.version.VersionNumberResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created on 24 Oct 2016 6:13 PM.
 *
 * @author PurpleTalk India Pvt. Ltd.
 */

public interface APIService {

    /*@GET(APIConstants.API_LANGUAGES)
    Observable<LanguageBaseResponse> getLanguagesAPI();

    @FormUrlEncoded
    @POST(APIConstants.API_REGISTER)
    Observable<APIBaseResponse> registerAPI(@Field("Language") String language, @Field("FirstName") String
            firstName, @Field("LastName") String lastName, @Field("DOB")
                                                    String dob, @Field("EmailAddress") String email, @Field
                                                    ("UserName")
                                                    String userName, @Field("Password") String password,
                                            @Field("ConfirmPassword") String confirmPassword,
                                            @Field("ActivationCode") String
                                                    activationCode);*/

    @FormUrlEncoded
    @POST(APIConstants.API_APP_VERSION)
    Observable<VersionNumberResponse> getVersionNumer(@Field("appname") String
                                                 userName);

    @FormUrlEncoded
    @POST(APIConstants.API_LOGIN)
    Observable<APIBaseResponse> loginAPI(@Field("username") String
                                                 userName, @Field("passwd") String password);

    @POST(APIConstants.API_MY_DEVICES_LIST)
    Observable<BaseDevicesList> myDeviceListAPI(@Header("cookie") String authToken);

    @FormUrlEncoded
    @POST(APIConstants.API_MY_DEVICES_LIST)
    Observable<BaseDevicesList> customerDeviceAndGpsoneAPI(@Header("cookie") String authToken, @Field("maptype") String mapType);

    @POST(APIConstants.API_UNREAD_ALARM_NUMBER)
    Observable<Object> unreadAlarmNumberAPI(@Header("cookie") String authToken);

    @POST(APIConstants.API_UNREAD_ALARM_LIST)
    Observable<BaseAlarmResponse> unreadAlarmListAPI(@Header("cookie") String authToken);

    @FormUrlEncoded
    @POST(APIConstants.API_ALARM_LIST)
    Observable<BaseAlarmResponse> alarmListAPI(@Header("cookie") String authToken, @Field("fromtime") String
                                                       fromTime, @Field("count") String count, @Field("maptype") String mapType);

    @FormUrlEncoded
    @POST(APIConstants.API_HANDLE_ALARM)
    Observable<Object> handleAlarmAPI(@Header("cookie") String authToken, @Field("alarmid") String alarmId);

    @FormUrlEncoded
    @POST(APIConstants.API_PUSH_ALARMID_API)
    Observable<AlarmIdItem> handlePushAlarmAPI(@Header("cookie") String authToken, @Field("alarmid") String alarmId , @Field("maptype") String maptype);
    @FormUrlEncoded
    @POST(APIConstants.API_TRACK)
    Observable<BaseTrackResponse> trackAPI(@Header("cookie") String authToken, @Field("deviceid") String
                                        deviceId, @Field("maptype") String mapType);

    @FormUrlEncoded
    @POST(APIConstants.API_PLAYBACK)
    Observable<PlayBackResponse> playbackAPI(@Header("cookie") String authToken,
                                             @Field("deviceid") String deviceId,
                                             @Field("begintime") String beginTime,
                                             @Field("endtime") String endTime,
                                             @Field("maptype") String mapType);


    @FormUrlEncoded
    @POST(APIConstants.API_DEVICEINFO)
    Observable<BaseDeviceItem> deviceInfoAPI(@Header("cookie") String authToken, @Field("deviceid") String
            deviceId, @Field("maptype") String mapType);



    @FormUrlEncoded
    @POST(APIConstants.API_DEVICEOVERSPEED)
    Observable<Object> overSpeedAPI(@Field("deviceid") String deviceId , @Field("overspeed")String overSpeed);



    @FormUrlEncoded
    @POST(APIConstants.API_EDIT_DEVICE)
    Observable<Object> deviceEditAPI(@Header("cookie") String authToken, @Field("deviceid") String deviceId, @Field("devicename") String deviceName,
                                     @Field("carnumber") String carNumber);

    @POST(APIConstants.API_CLEAR_ALL_ALARM)
    Observable<Object> clearAllAlarmAPI(@Header("cookie") String authToken);

    @FormUrlEncoded
    @POST(APIConstants.API_EFENCE_LIST)
    Observable<BaseGeoFence> efenceListAPI(@Header("cookie") String authToken, @Field("deviceid") String
            deviceId, @Field("maptype") String mapType);


    @FormUrlEncoded
    @POST(APIConstants.API_EFENCE_ON_OFF_API)
    Observable<Object> effenceOnOffAPI(@Header("cookie") String authToken, @Field("efenceid") String
            efenceId);



    @FormUrlEncoded
    @POST(APIConstants.API_EFENCE_DELETE_API)
    Observable<Object> effenceDeleteAPI(@Header("cookie") String authToken, @Field("efenceid") String
            efenceId);

    @FormUrlEncoded
    @POST(APIConstants.API_GEOFENCE_NEW_API)
    Observable<Object> effenceNewGeoFence(@Header("cookie") String authToken, @Field("deviceid") String
            efenceId,@Field("maptype") String  mapType,@Field("efencename") String  efenceName,@Field("shapetype") String  shapeType,@Field("shapeparam") String  shapeParam,
                                          @Field("alarmtype") String  alarmType,@Field("isopen") String  Isopen,@Field("phonenumber") String  phoneNumber);

    @FormUrlEncoded
    @POST(APIConstants.API_GEOFENCE_EDIT_API)
    Observable<Object> effenceEditGeoFenceAPI(@Header("cookie") String authToken,
                                              @Field("efenceid") String efenceId,
                                              @Field("maptype") String  mapType,
                                              @Field("efencename") String  efenceName,
                                              @Field("shapetype") String  shapeType,
                                              @Field("shapeparam") String  shapeParam,
                                              @Field("alarmtype") String  alarmType,
                                              @Field("isopen") String  Isopen,
                                              @Nullable @Field("phonenumber") String  phoneNumber);



    @FormUrlEncoded
    @POST(APIConstants.API_PASSWORD_CHANGE)
    Observable<Object> changePasswordAPI(@Header("cookie") String authToken,
                                              @Field("oldpwd") String oldPwd,
                                              @Field("newpwd") String  newPwd);

    /*@FormUrlEncoded
    @POST(APIConstants.API_FORGOTPASSWORD)
    Observable<APIBaseResponse> forgotPasswordAPI(@Field("EmailAddress") String email);

    @FormUrlEncoded
    @POST(APIConstants.API_UPDATE_PROFILE)
    Observable<APIBaseResponse> updateProfile(@Header("AuthToken") String authToken, @Field("FirstName") String
            firstName, @Field("LastName") String lastName);

    @GET(APIConstants.API_VIEW_PROFILE)
    Observable<APIBaseResponse> viewProfileAPI(@Header("AuthToken") String authToken);*/
}
