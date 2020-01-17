package vn.funtap.funtapsdklite.rest;

public class RestfulApi {

    public static final String BASE_URL = "https://id.funtap.vn";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
