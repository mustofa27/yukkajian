package com.almuflihun.yukkajian;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Calendar;

import entity.Kajian;
import helper.ImageHelper;
import helper.StringHelper;
import model.KajianModel;
import model.UserModel;
import network.ConnectionHandler;
import network.JsonCallback;
import network.KajianNetwork;
import utility.GetLocationCallback;
import utility.LocationGetter;

public class CreateKajian extends AppCompatActivity {
    TextView pickLocation,tanggal,waktu,thumbnail;
    String latitude = "",longitude = "",tanggalKajian = "",waktuKajian = "",jenisPeserta = "";
    KajianNetwork kajianNetwork;
    KajianModel kajianModel;
    EditText tema,pemateri,keterangan,masjid,alamat;
    Switch umum,rutin;
    int isRutin = 0;
    Uri selectedImage;
    ImageHelper imageHelper;
    Bitmap bitmap;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_kajian);
        kajianNetwork = new KajianNetwork(this);
        kajianModel = new KajianModel(this);
        userModel = new UserModel(this);
        imageHelper = new ImageHelper(this);
        tema = (EditText) findViewById(R.id.tema);
        pemateri = (EditText) findViewById(R.id.pemateri);
        keterangan = (EditText) findViewById(R.id.keterangan);
        masjid = (EditText) findViewById(R.id.masjid);
        alamat = (EditText) findViewById(R.id.alamat);
        ((Button)findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringHelper.isNotEmpty(tema.getText().toString()) && StringHelper.isNotEmpty(pemateri.getText().toString()) &&
                        StringHelper.isNotEmpty(keterangan.getText().toString()) && StringHelper.isNotEmpty(masjid.getText().toString()) &&
                        StringHelper.isNotEmpty(alamat.getText().toString()) && StringHelper.isNotEmpty(latitude) && StringHelper.isNotEmpty(longitude) &&
                        StringHelper.isNotEmpty(tanggalKajian) && StringHelper.isNotEmpty(waktuKajian) && StringHelper.isNotEmpty(jenisPeserta) &&
                        selectedImage != null) {
                    JSONObject jsonObject = new JSONObject();
                    final Kajian kajian = new Kajian(0,0,isRutin,tanggalKajian,waktuKajian,tema.getText().toString(),
                            pemateri.getText().toString(),masjid.getText().toString() + ", " + alamat.getText().toString(),
                            latitude,longitude,keterangan.getText().toString(),jenisPeserta,selectedImage.toString());
                    try {
                        jsonObject.put("tema",tema.getText().toString());
                        jsonObject.put("pemateri",pemateri.getText().toString());
                        jsonObject.put("alamat",masjid.getText().toString() + ", " + alamat.getText().toString());
                        jsonObject.put("deskripsi",keterangan.getText().toString());
                        jsonObject.put("jenis_peserta",jenisPeserta);
                        jsonObject.put("latitude",latitude);
                        jsonObject.put("longitude",longitude);
                        jsonObject.put("isRutin",isRutin);
                        jsonObject.put("latitude",latitude);
                        jsonObject.put("waktu",tanggalKajian + " " + waktuKajian);
                        jsonObject.put("thumbnail", imageHelper.getStringImage(bitmap));
                        jsonObject.put("id_user",userModel.GetUser().getSid());
                        kajianNetwork.CreateKajian(jsonObject, new JsonCallback() {
                            @Override
                            public void Done(JSONObject jsonObject, String message) {
                                if(message.equals(ConnectionHandler.response_message_success) && jsonObject != null){
                                    try {
                                        JSONObject data = new JSONObject(jsonObject.getString("data"));
                                        kajian.setSid(data.getInt("id"));
                                        kajian.setThumbnail(data.getString("gambar"));
                                        kajianModel.InsertKajian(kajian);
                                        onBackPressed();
                                    } catch (JSONException e) {
                                        Toast.makeText(CreateKajian.this,"Terjadi kesalahan, silahkan coba lagi.",Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }
                                }
                                else
                                    Toast.makeText(CreateKajian.this,message,Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (JSONException e) {
                        Toast.makeText(CreateKajian.this,"Terjadi kesalahan, silahkan coba lagi.",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
        });
        pickLocation = (TextView) findViewById(R.id.pickLocation);
        tanggal = (TextView) findViewById(R.id.tanggal);
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                Dialog mDialog = new DatePickerDialog(CreateKajian.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        tanggal.setTextColor(Color.BLACK);
                        tanggal.setText(String.valueOf(dayOfMonth) + " " +
                                new DateFormatSymbols().getMonths()[monthOfYear] + " " + String.valueOf(year));
                        tanggalKajian = String.valueOf(year) + "-" +
                                String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(calendar.DAY_OF_MONTH));
                mDialog.show();
            }
        });
        waktu = (TextView) findViewById(R.id.jam);
        waktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateKajian.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        waktuKajian = selectedHour + ":" + selectedMinute + ":00";
                        waktu.setText(waktuKajian);
                    }
                }, hour, minute, false);
                mTimePicker.show();

            }
        });
        pickLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(CreateKajian.this,MapsActivity.class),1);
            }
        });
        thumbnail = (TextView) findViewById(R.id.thumbnail);
        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 2);
            }
        });
        umum = (Switch) findViewById(R.id.isUmum);
        umum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(umum.isChecked()){
                    jenisPeserta = "Umum";
                } else {
                    jenisPeserta = "Laki-laki";
                }
            }
        });
        rutin = (Switch) findViewById(R.id.isRutin);
        rutin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(rutin.isChecked()){
                    isRutin = 1;
                } else {
                    isRutin = 0;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == 1) {
                latitude = data.getStringExtra("latitude");
                longitude = data.getStringExtra("longitude");
                pickLocation.setText(latitude + ", " + longitude);
            }
            else if (requestCode == 2){
                selectedImage = data.getData();
                thumbnail.setText(selectedImage.toString());
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,KelolaKajian.class));
    }
}
