package vn.funtap.funtapsdklite.rest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.funtap.funtapsdklite.model.Data;

public interface APIService {
    @POST("/ApiIdV5/login")
    @FormUrlEncoded
    Call<Data> loginPost(
            @Field("appkey") String appkey,
            @Field("username") String username,
            @Field("password") String password,
            @Field("signature") String signature
    );
}
