package tn.com.cellcom.cellcomevertek.network;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;
import tn.com.cellcom.cellcomevertek.entities.Form;
import tn.com.cellcom.cellcomevertek.entities.Response;


/**
 * Created by farouk on 07/04/2017.
 */

public interface RetrofitFormInterface {
    @POST("form")
    Observable<Response> addForm(@Body Form form);
    @GET("form/{id}")
    Observable<Form> getFormById(@Path("id") String id);
    @GET("form")
    Observable<List<Form>> getAllForm();
    @PUT("form/{id}")
    Observable<Response> updateForm(@Path("id") String id, @Body Form form);
    @DELETE("form/{id}")
    Observable<Response> deleteForm(@Path("id") String id);
}
