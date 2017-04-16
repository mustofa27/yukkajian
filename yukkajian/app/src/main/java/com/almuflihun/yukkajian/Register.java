package com.almuflihun.yukkajian;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import entity.User;
import helper.StringHelper;
import model.UserModel;
import network.ConnectionHandler;
import network.JsonCallback;
import network.UserNetwork;

public class Register extends AppCompatActivity {

    EditText nama, tempatLahir, username, email, password, confirmPass, asal, domisili, pekerjaan, noHP;
    RadioGroup jk;
    TextView tanggalLahir;
    String jenisKelamin = "", birthday = "";
    UserNetwork userNetwork;
    UserModel userModel;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userNetwork = new UserNetwork(this);
        userModel = new UserModel(this);
        nama = (EditText) findViewById(R.id.nama);
        tempatLahir = (EditText) findViewById(R.id.tempatLahir);
        tanggalLahir = (TextView) findViewById(R.id.tanggalLahir);
        tanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                Dialog mDialog = new DatePickerDialog(Register.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        tanggalLahir.setTextColor(Color.BLACK);
                        tanggalLahir.setText(String.valueOf(dayOfMonth) + " " +
                                new DateFormatSymbols().getMonths()[monthOfYear] + " " + String.valueOf(year));
                        birthday = String.valueOf(year) + "-" +
                                String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);
                    }
                }, 1990, 0, 1);
                mDialog.show();
            }
        });
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPass = (EditText) findViewById(R.id.confirmPass);
        asal = (EditText) findViewById(R.id.alamat_asal);
        domisili = (EditText) findViewById(R.id.alamat_domisili);
        pekerjaan = (EditText) findViewById(R.id.pekerjaan);
        noHP = (EditText) findViewById(R.id.nomerHp);
        jk = (RadioGroup) findViewById(R.id.jk);
        jk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.pria) {
                    jenisKelamin = "Pria";
                } else if (i == R.id.wanita) {
                    jenisKelamin = "Wanita";
                }
            }
        });
    }

    public void submit(View v) {
        if (StringHelper.isNotEmpty(nama.getText().toString()) && StringHelper.isNotEmpty(tempatLahir.getText().toString()) &&
                StringHelper.isNotEmpty(birthday) && StringHelper.isNotEmpty(username.getText().toString()) &&
                StringHelper.isNotEmpty(email.getText().toString()) && StringHelper.isNotEmpty(password.getText().toString()) &&
                StringHelper.isNotEmpty(confirmPass.getText().toString()) && StringHelper.isNotEmpty(asal.getText().toString()) &&
                StringHelper.isNotEmpty(domisili.getText().toString()) && StringHelper.isNotEmpty(pekerjaan.getText().toString()) &&
                StringHelper.isNotEmpty(noHP.getText().toString()) && StringHelper.isNotEmpty(jenisKelamin)) {
            if(password.getText().toString().equals(confirmPass.getText().toString())){
                //uploading
                user = new User(0,0,username.getText().toString(),password.getText().toString(),email.getText().toString(),
                        nama.getText().toString(),asal.getText().toString(),jenisKelamin,domisili.getText().toString(),
                        noHP.getText().toString(),pekerjaan.getText().toString(),tempatLahir.getText().toString(),birthday);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("nama",nama.getText().toString());
                    jsonObject.put("tempat_lahir",tempatLahir.getText().toString());
                    jsonObject.put("tanggal_lahir",birthday);
                    jsonObject.put("username",username.getText().toString());
                    jsonObject.put("email",email.getText().toString());
                    jsonObject.put("password",password.getText().toString());
                    jsonObject.put("alamat_asal",asal.getText().toString());
                    jsonObject.put("alamat_domisili",domisili.getText().toString());
                    jsonObject.put("pekerjaan",pekerjaan.getText().toString());
                    jsonObject.put("nomor_hp",noHP.getText().toString());
                    jsonObject.put("jenis_kelamin",jenisKelamin);
                    userNetwork.Register(jsonObject, new JsonCallback() {
                        @Override
                        public void Done(JSONObject jsonObject, String message) {
                            if(message.equals(ConnectionHandler.response_message_success) && jsonObject != null){
                                try {
                                    JSONObject data = new JSONObject(jsonObject.getString("data"));
                                    user.setSid(data.getInt("id"));
                                    userModel.InsertUser(user);
                                    finish();
                                    startActivity(new Intent(Register.this,SplashScreen.class));
                                } catch (JSONException e) {
                                    Toast.makeText(Register.this,"Terjadi kesalahan, silahkan coba lagi.",Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                            else
                                Toast.makeText(Register.this,message,Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this,"Terjadi kesalahan, silahkan coba lagi.",Toast.LENGTH_LONG).show();
                }
            }
            else
                Toast.makeText(this,"Pastikan pasangan password anda sudah sesuai",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"Pastikan semua isian sudah terisi",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,Login.class));
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
