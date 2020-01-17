package vn.funtap.demosdkstrike;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import vn.funtap.funtapsdklite.FuntapListener;
import vn.funtap.funtapsdklite.FuntapSDK;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnSignIn ;
    private TextView txtToken , txtFtId;
    FuntapSDK funtapSDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (Button) findViewById(R.id.btn_call_form_sdk);
        txtToken = findViewById(R.id.txt_token);
        txtFtId = findViewById(R.id.txt_ftid);

        btnSignIn.setOnClickListener(this);

        initSDK();
    }

    private void initSDK() {
        funtapSDK = FuntapSDK.getInstance();
        funtapSDK.init(this, "d650e39f327e4d0fd59a2cc32d8250ca");
        funtapSDK.setMobGameListener(mOnLoginListener);
    }

    FuntapListener mOnLoginListener = new FuntapListener() {
        @Override
        public void onLoginSuccessful(String access_token, String ft_id) {
            Log.d("TAG" , "Info user : " + access_token + ", " + ft_id);
            txtToken.setText("token :" + access_token);
            txtFtId.setText("ftid :" + ft_id);
        }

        @Override
        public void onError(int errorCode, String message) {
            Log.d("TAG" , "Error " + errorCode + ", " + message);
        }

        @Override
        public void onCloseForm() {
            Log.d("TAG" , "close Form Login");
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_call_form_sdk:
                funtapSDK.login();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        funtapSDK.onActivityResult(requestCode , resultCode , data);
    }
}
