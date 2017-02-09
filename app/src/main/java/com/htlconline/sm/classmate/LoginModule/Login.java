package com.htlconline.sm.classmate.LoginModule;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.htlconline.sm.classmate.MainActivity;
import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.handler.UserActivityHandler;
import com.htlconline.sm.classmate.interfaces.DataHandlerCallBack;

import java.util.HashMap;

/**
 * Created by Shikhar Garg on 19-12-2016.
 */
public class Login extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener,DataHandlerCallBack {
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;

    private SignInButton mSignInButton;
    private LoginButton mLoginButton;
    private Button mNormalSignInButton;
    private ImageView fbbutton,googlebutton;
    private FloatingActionButton fb_button,g_button;
    private final static int RC_SIGN_IN=004;
    private final static String TAG="Login.class";
    private String user,pass;
    private EditText mUser,mPass;
    private DataHandlerCallBack dataHandlerCallBack;

    private static final String url="http://www.htlconline.com/api/users/login/";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login_activity_screen);

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        mSignInButton=(SignInButton) findViewById(R.id.gsignin);
        mSignInButton.setSize(SignInButton.SIZE_STANDARD);
        mSignInButton.setOnClickListener(this);

        mNormalSignInButton=(Button) findViewById(R.id.normal_sign_in);
        mNormalSignInButton.setOnClickListener(this);


        mCallbackManager=CallbackManager.Factory.create();
        mLoginButton=(LoginButton) findViewById(R.id.f_signin);
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(TAG,"Fb Login id:"+loginResult.getAccessToken().getUserId());
                Log.i(TAG,"Fb user name:"+loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Log.i(TAG,"cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i(TAG,"Error");
            }
        });
        fbbutton=(ImageView) findViewById(R.id.fb);
        googlebutton=(ImageView) findViewById(R.id.gplus);
        //fb_button=(FloatingActionButton) findViewById(R.id.fb);
        //g_button=(FloatingActionButton) findViewById(R.id.gplus);

        mUser=(EditText) findViewById(R.id.username_login);
        mPass=(EditText) findViewById(R.id.password_login);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.gsignin:
                signin();
                break;
            case R.id.normal_sign_in:
                user=mUser.getText().toString();
                pass=mPass.getText().toString();
                login(user,pass);
                break;
            case R.id.fb:
                mLoginButton.performClick();
                break;
            case R.id.gplus:
                signin();
                break;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void signin(){
        Intent sign_in_intent=Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(sign_in_intent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleresult(result);
        }
    }

    public void handleresult(GoogleSignInResult result){
        Log.i(TAG, "result status:" + result.isSuccess());
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            Log.i(TAG,"Email:"+account.getEmail());
            startActivity(new Intent(Login.this,MainActivity.class));
        }
        else{

        }
    }

    public void login(String user,String password){
        HashMap<String,String> map=new HashMap<String,String>();
        map.put("username",user);
        map.put("password",pass);
        Log.i("Login_checking", user + " " + pass);
        UserActivityHandler userActivityHandler=new UserActivityHandler(Login.this,dataHandlerCallBack);
        userActivityHandler.postUserLogin(url, map);
    }

    @Override
    public void onSuccess(HashMap<String, Object> map) {
        Log.i("Login_checking","check1");
        LoginResponse res=(LoginResponse)map.get("LoginResponse");
        Log.i("Login_checking","check2");
        if(res.getSuccess().equalsIgnoreCase("true")){
            Log.i("Login_checking","check3");
            startActivity(new Intent(Login.this,MainActivity.class));
        }
        else{
            Log.i("Login_checking","check4");
        }

    }
    @Override
    public void onFailure(HashMap<String, Object> map) {
        Log.i("Login_checking","check5");
    }
}
