package com.almuflihun.yukkajian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import entity.User;
import helper.StringHelper;
import model.UserModel;
import network.ConnectionHandler;
import network.JsonCallback;
import network.UserNetwork;

public class Login extends AppCompatActivity {

    EditText username,password;
    Button signin;
    UserNetwork userNetwork;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNetwork = new UserNetwork(this);
        userModel = new UserModel(this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringHelper.isNotEmpty(username.getText().toString()) && StringHelper.isNotEmpty(password.getText().toString())){
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("username",username.getText().toString());
                        jsonObject.put("password",password.getText().toString());
                        userNetwork.Login(jsonObject, new JsonCallback() {
                            @Override
                            public void Done(JSONObject jsonObject, String message) {
                                if(message.equals(ConnectionHandler.response_message_success) && jsonObject != null){
                                    try {
                                        JSONObject data = new JSONObject(jsonObject.getString("data"));
                                        User user = new User(0,data.getInt("id"),data.getString("username"),password.getText().toString(),
                                                data.getString("email"),data.getString("nama"),data.getString("alamat_asal"),
                                                data.getString("jenis_kelamin"),data.getString("alamat_domisili"),data.getString("nomor_hp"),
                                                data.getString("pekerjaan"),data.getString("tempat_lahir"),data.getString("tanggal_lahir"));
                                        userModel.InsertUser(user);
                                        finish();
                                        startActivity(new Intent(Login.this,SplashScreen.class));
                                    } catch (JSONException e) {
                                        Toast.makeText(Login.this,"Terjadi kesalahan, silahkan coba lagi.",Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }
                                }
                                else
                                    Toast.makeText(Login.this,message,Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (JSONException e) {
                        Toast.makeText(Login.this,"Terjadi kesalahan, silahkan coba lagi.",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
        });
        findViewById(R.id.toRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(Login.this,Register.class));
            }
        });
    }
}
