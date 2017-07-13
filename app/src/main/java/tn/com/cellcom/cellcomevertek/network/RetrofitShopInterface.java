package tn.com.cellcom.cellcomevertek.network;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;
import tn.com.cellcom.cellcomevertek.entities.Response;
import tn.com.cellcom.cellcomevertek.entities.Shop;

/**
 * Created by farouk on 05/07/2017.
 */

public interface RetrofitShopInterface {
    @POST("shop")
    Observable<Response> addShop(@Body Shop shop);
    @GET("shop/{id}")
    Observable<Shop> getShopById(@Path("id") String id);
    @GET("shop")
    Observable<List<Shop>> getAllShop();
    @PUT("shop/{id}")
    Observable<Shop> updateShop(@Path("id") String id, @Body Shop shop);
    @DELETE("shop/{id}")
    Observable<Response> deleteShop(@Path("id") String id);
}
