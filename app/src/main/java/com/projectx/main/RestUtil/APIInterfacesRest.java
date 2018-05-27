package com.projectx.main.RestUtil;

/**
 * Created by user on 1/10/2018.
 */




import com.projectx.main.modelservice.mapsvendor.MapsVendor;
import com.projectx.main.modelservice.vendor.Merchandise;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by anupamchugh on 09/01/17.
 */

 public interface APIInterfacesRest {

 /*@FormUrlEncoded
 @POST("api/user/login")
 Call<Login> getLogin(@Field("username") String username, @Field("password") String password);
*/

 @GET("fetchMerchandise")
 Call<List<Merchandise>> getListMerchant1(@Query("vendorId") String vendorId, @Query("categoryId") String categoryId, @Query("hasChild") String hasChild, @Query("currentDate") String currentDate, @Query("lat") String lat, @Query("long") String lon,@Query("customerPhone") String customerPhone);


 @GET("fetchVendorsLocation")
 Call<List<MapsVendor>> getMapVendorLocation(@Query("vendorId") String vendorId, @Query("categoryId") String categoryId, @Query("hasChild") String hasChild, @Query("lat") String lat, @Query("long") String lon);


 /*@Multipart
 @POST("api/tbl_pegawai/add")
 Call<Pegawai> addData(
         @Part("nama") RequestBody nama,
         @Part("no_pegawai") RequestBody no_pegawai,
         @Part("foto\"; filename=\"image.jpeg\"") RequestBody foto

 );*/



/*
 @GET("api/tbl_tukar_point/all")
 Call<TukarPoint> getTukarPoint(@Query("filter") String filter, @Query("field") String field, @Query("start") String start, @Query("limit") String limit);

 @GET("api/tbl_motor_bekas/all")
 Call<MotorBekas> getMotorBekas(@Query("filter") String filter, @Query("field") String field, @Query("start") String start, @Query("limit") String limit);

 @GET("api/tbl_elektronik/all")
 Call<Elektronik> getElektronik(@Query("filter") String filter, @Query("field") String field, @Query("start") String start, @Query("limit") String limit);

 @GET("api/tbl_info_kamm/all")
 Call<InfoKamm> getInfoKamm(@Query("filter") String filter, @Query("field") String field, @Query("start") String start, @Query("limit") String limit);
*/



}

