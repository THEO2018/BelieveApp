package com.netset.believeapp.retrofitManager;


import android.content.Context;

import com.netset.believeapp.Utils.GeneralValues;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Neeraj Narwal on 5/5/17.
 */
public class ApiClient {
    private  Retrofit retrofit = null;
    private Context context;
 //   public static String BASE_URL="http://104.236.127.72:3000";

    //Local SERVER
//    192.168.2.95
    public static String BASE_URL="http://198.211.110.165:5009";
//    public static String BASE_URL="http://192.168.2.95:5009";
//    public static String BASE_URL="http://192.168.2.61:5009";
//    public static String BASE_URL="https://calm-springs-62159.herokuapp.com";

    public ApiClient(Context context) {
        this.context = context;
    }

    public Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = getClient();
        }
        return retrofit;
    }

    public Retrofit getClient() {

        /** Creates client for retrofit, here you can configure different settings of retrofit manager
         * like Logging, Cache size, Decoding factories, Convertor factories etc.
         */
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .connectTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {      // add header in api
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("app_version", GeneralValues.get_appVersion(context))
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}



