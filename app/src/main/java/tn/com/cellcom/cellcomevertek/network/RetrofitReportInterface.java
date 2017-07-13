package tn.com.cellcom.cellcomevertek.network;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;
import tn.com.cellcom.cellcomevertek.entities.Report;
import tn.com.cellcom.cellcomevertek.entities.Response;
import tn.com.cellcom.cellcomevertek.entities.Shop;

/**
 * Created by farouk on 11/07/2017.
 */

public interface RetrofitReportInterface {
    @POST("report")
    Observable<Report> addReport(@Body Report report);
    @GET("report/{id}")
    Observable<Report> getReportById(@Path("id") String id);
    @GET("report")
    Observable<List<Report>> getAllReport();
    @PUT("report/{id}")
    Observable<Report> updateReport(@Path("id") String id, @Body Report report);
    @DELETE("report/{id}")
    Observable<Response> deleteReport(@Path("id") String id);
}
