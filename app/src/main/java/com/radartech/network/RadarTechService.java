package com.radartech.network;


import com.radartech.sw.BuildConfig;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 24 Oct 2016 6:15 PM.
 *
 * @author PurpleTalk India Pvt. Ltd.
 */

public class RadarTechService {

    private static RadarTechService instance = null;
    private APIService mApiService;

    private RadarTechService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(new OkHttpClient.Builder()
                        // this line is the important one for getting cookies:
                        .cookieJar(new JavaNetCookieJar(new MyCookieManager()))
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .build();
        mApiService = retrofit.create(APIService.class);
    }

    public static RadarTechService getInstance() {
        if (instance == null) {
            instance = new RadarTechService();
        }
        return instance;
    }

    public APIService getAPIService() {
        return mApiService;
    }
}
