package tn.com.cellcom.cellcomevertek.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;
import tn.com.cellcom.cellcomevertek.utils.Constants;

/**
 * Created by farouk on 10/04/2017.
 */

public class FileRetrofit {
    public static RetrofitFormInterface getFileRetrofit(){

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitFormInterface.class);

    }
}
