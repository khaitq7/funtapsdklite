package vn.funtap.funtapsdklite;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import vn.funtap.funtapsdklite.model.Data;
import vn.funtap.funtapsdklite.ui.SignInActivity;
import vn.funtap.funtapsdklite.utils.Preference;

public class FuntapSDK {
    private static final String TAG = FuntapSDK.class.getSimpleName();
    public static String SHARED_PREF_APPKEY = "shared_pref_appkey";
    public static int REQUESTCODE_CALLBACK = 9090;

    private FuntapListener mListener;
    private static FuntapSDK INSTANCE;
    private Activity mActivity;
    public static FuntapSDK getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FuntapSDK();
        }
        return INSTANCE;
    }

    public void init(Activity activity, String appkey) {
        this.mActivity = activity;
        try {
            Preference.save(activity, SHARED_PREF_APPKEY, appkey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mListener == null) {
            setMobGameListener(new FuntapListener() {
                @Override
                public void onLoginSuccessful(String access_token, String ft_id) {

                }

                @Override
                public void onError(int errorCode, String message) {

                }

                @Override
                public void onCloseForm() {

                }
            });
        }
    }

    public void setMobGameListener(FuntapListener listener) {
        final FuntapListener fl = listener;

        FuntapListener onLoginListener = new FuntapListener() {
            @Override
            public void onLoginSuccessful(String access_token, String ft_id) {
                fl.onLoginSuccessful(access_token , ft_id);
            }

            @Override
            public void onError(int errorCode, String message) {
                fl.onError(errorCode , message);
            }

            @Override
            public void onCloseForm() {

            }
        };
        mListener = onLoginListener;
    }

    public void login() {
        //TODO confirm have autoLogin ?

        //TODO call form login
        Intent intent = new Intent(mActivity , SignInActivity.class);
        mActivity.startActivityForResult(intent , REQUESTCODE_CALLBACK);
    }

    public void logout() {

    }

    public void onActivityResult(int requestCode , int resultCode, @Nullable Intent data ){
        if (requestCode == REQUESTCODE_CALLBACK) {
            if(data != null){
                if(data.getStringExtra("data") != null){
                    String status = data.getStringExtra("data");
                    Log.d(TAG , "data from activityResult : " + data);
                    Gson gson = new GsonBuilder().create();
                    Data.UserInfo response = gson.fromJson(status , Data.UserInfo.class);
                    mListener.onLoginSuccessful(response.getAccessToken() , response.getFtId());
                    mListener.onCloseForm();
                }
                if(data.getStringExtra("fails") != null){
                    String error = data.getStringExtra("fails");
                    mListener.onError(-1 , error);
                }
            }else{
                mListener.onCloseForm();
            }
        }
    }
}
