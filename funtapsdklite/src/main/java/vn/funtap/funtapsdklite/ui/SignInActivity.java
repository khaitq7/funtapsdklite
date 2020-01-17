package vn.funtap.funtapsdklite.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.funtap.funtapsdklite.FuntapListener;
import vn.funtap.funtapsdklite.FuntapSDK;
import vn.funtap.funtapsdklite.R;
import vn.funtap.funtapsdklite.model.Data;
import vn.funtap.funtapsdklite.rest.APIService;
import vn.funtap.funtapsdklite.rest.RestfulApi;
import vn.funtap.funtapsdklite.utils.Preference;
import vn.funtap.funtapsdklite.utils.Utils;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = SignInActivity.class.getSimpleName();
    private Button btnLogin;
    private ImageButton btnClose;
    private AppCompatEditText edUsername;
    private TextInputEditText edPassword;

    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);
        btnLogin = findViewById(R.id.btn_login);
        btnClose = findViewById(R.id.btn_closeform);
        edUsername = findViewById(R.id.edit_username);
        edPassword = findViewById(R.id.edit_password);

        mAPIService = RestfulApi.getAPIService();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String username , password;
                    if(validateInputForm(edUsername)){
                        username = edUsername.getText().toString();
                    }else{
                        return;
                    }

                    if(validateInputForm(edPassword)){
                        password = edPassword.getText().toString();
                    }else{
                        return;
                    }
                    String appkey = Preference.getString(getApplicationContext() , FuntapSDK.SHARED_PREF_APPKEY);
                    String input = getSig() + appkey +  username + password;
                    String signature = Hashing.sha256()
                            .hashString(input, StandardCharsets.UTF_8)
                            .toString();
                    Log.d(TAG , "Signature and something: " + signature + " , appkey " + appkey + ", username : " + username + " , password : " + password);
                    loginPostRequest(appkey , username , password , signature);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void loginPostRequest(String appkey , String username , String password , String signature){
        mAPIService.loginPost(appkey , username , password , signature).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                try {
                    if(response.isSuccessful()){
                        Data.UserInfo userInfo = response.body().getUserInfo();
                        Gson gson = new GsonBuilder().create();
                        String json = gson.toJson(userInfo);
                        Intent intent = getIntent();
                        intent.putExtra("data" , json);
                        setResult(FuntapSDK.REQUESTCODE_CALLBACK , intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                edUsername.setError("Email/Tên đăng nhập/SĐT/ Mật khẩu không đúng");
                Intent intent = getIntent();
                intent.putExtra("fails" , t.getMessage().toString());
                setResult(FuntapSDK.REQUESTCODE_CALLBACK , intent);
            }
        });
    }

    private String getSig(){
        String str = "ZKbkPEor+kDxxu2G9svRbXvxlJd7h0DeeB8fVwOTWg0=";
        byte[] decode = Base64.decode(str, Base64.DEFAULT);
//        return "15f50330cccb84340e43bbb753c49782";

        return Utils.decryptionDataECB(decode);
    }
    private boolean validateInputForm (EditText editText){
        if(editText.getText().toString().trim() != null || !editText.getText().toString().trim().equals("")){
            return true;
        }else{
            return false;
        }
    }

}
