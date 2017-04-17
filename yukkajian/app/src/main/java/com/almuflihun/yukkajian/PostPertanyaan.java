package com.almuflihun.yukkajian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import entity.Kajian;
import helper.StringHelper;
import model.UserModel;
import network.ConnectionHandler;
import network.JsonCallback;
import network.PertanyaanNetwork;

public class PostPertanyaan extends AppCompatActivity {
    EditText pertanyaan;
    Kajian current;
    UserModel userModel;
    PertanyaanNetwork pertanyaanNetwork;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_pertanyaan);
        current = (Kajian) getIntent().getSerializableExtra("kajian");
        userModel = new UserModel(this);
        pertanyaanNetwork = new PertanyaanNetwork(this);
        pertanyaan = (EditText) findViewById(R.id.pertanyaan);
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringHelper.isNotEmpty(pertanyaan.getText().toString())) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id_user", userModel.GetUser().getSid());
                        jsonObject.put("id_kajian", current.getSid());
                        jsonObject.put("pertanyaan", pertanyaan.getText().toString());
                        pertanyaanNetwork.CreatePertanyaan(jsonObject, new JsonCallback() {
                            @Override
                            public void Done(JSONObject jsonObject, String message) {
                                if(message.equals(ConnectionHandler.response_message_success)){
                                    onBackPressed();
                                }else {
                                    Toast.makeText(PostPertanyaan.this,message,Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        Toast.makeText(PostPertanyaan.this,"Terjadi kesalahan, silahkan coba lagi.",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,JadwalKajian.class));
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
