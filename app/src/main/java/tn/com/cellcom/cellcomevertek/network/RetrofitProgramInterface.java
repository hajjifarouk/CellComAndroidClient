package tn.com.cellcom.cellcomevertek.network;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;
import tn.com.cellcom.cellcomevertek.entities.Program;
import tn.com.cellcom.cellcomevertek.entities.Response;

/**
 * Created by farouk on 08/07/2017.
 */

public interface RetrofitProgramInterface {
    @POST("program")
    Observable<Response> addProgram(@Body Program program);
    @GET("program/{id}")
    Observable<Program> getProgramById(@Path("id") String id);
    @GET("program/user/{userId}")
    Observable<Program> getProgramByUserId(@Path("userId") String id);
    @GET("program/date/{date}")
    Observable<Program> getProgramByDate(@Path("date") String id);
    @GET("program/userDate/{user}/{date}")
    Observable<Program> getProgramByUserDate(@Path("date") String date,@Path("user") String user);
    @GET("program")
    Observable<List<Program>> getAllProgram();
    @PUT("program/{id}")
    Observable<Program> updateProgram(@Path("id") String id, @Body Program program);
    @DELETE("program/{id}")
    Observable<Response> deleteProgram(@Path("id") String id);
}
