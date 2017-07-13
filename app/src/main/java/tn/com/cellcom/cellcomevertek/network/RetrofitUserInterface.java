package tn.com.cellcom.cellcomevertek.network;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;
import tn.com.cellcom.cellcomevertek.entities.Response;
import tn.com.cellcom.cellcomevertek.entities.User;

/**
 * Created by farouk on 07/04/2017.
 */

public interface RetrofitUserInterface {
    @POST("user/register")
    Observable<Response> register(@Body User user);
    @POST("user/login")
    Observable<Response> login();
    @GET("user/{email}")
    Observable<User> getProfile(@Path("email") String email);
    @PUT("user/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body User user);
    @POST("user/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);
    @POST("user/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);
}
