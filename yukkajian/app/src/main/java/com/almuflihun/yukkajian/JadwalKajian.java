package com.almuflihun.yukkajian;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.KajianAdapter;
import entity.Kajian;
import model.KajianModel;
import network.ConnectionHandler;
import network.JsonCallback;
import network.KajianNetwork;

public class JadwalKajian extends AppCompatActivity {

    ListView listView;
    TextView tanggal;
    Calendar calendar;
    KajianNetwork kajianNetwork;
    KajianModel kajianModel;
    String tanggalKajian,tanggalKajian1;
    ProgressBar progressBar;
    List<Kajian> kajianList;
    int width;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_kajian);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        width = size.x;
        kajianList = new ArrayList<>();
        kajianModel = new KajianModel(this);
        kajianNetwork = new KajianNetwork(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        calendar = Calendar.getInstance();
        tanggal = (TextView) findViewById(R.id.tanggal);
        tanggal.setText(String.valueOf(calendar.get(calendar.DAY_OF_MONTH)) + " " +
                new DateFormatSymbols().getMonths()[calendar.get(calendar.MONTH)] + " " + String.valueOf(calendar.get(calendar.YEAR)));
        tanggalKajian = String.valueOf(calendar.get(Calendar.YEAR)) + "-" +
                String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + " 00:00:00";
        tanggalKajian1 = String.valueOf(calendar.get(Calendar.YEAR)) + "-" +
                String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + 1) + " 00:00:00";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("from",String.valueOf(tanggalKajian));
            jsonObject.put("to",String.valueOf(tanggalKajian1));
            kajianNetwork.GetKajian(jsonObject, new JsonCallback() {
                @Override
                public void Done(JSONObject jsonObject, String message) {
                    progressBar.setVisibility(View.GONE);
                    if(message.equals(ConnectionHandler.response_message_success) && jsonObject != null){
                        try {
                            kajianList.clear();
                            JSONArray data = new JSONArray(jsonObject.getString("data"));
                            for(int i = 0; i < data.length(); i++){
                                JSONObject current = data.getJSONObject(i);
                                kajianList.add(new Kajian(0,current.getInt("id"),current.getInt("isRutin"),current.getString("waktu").split(" ")[0],
                                        current.getString("waktu").split(" ")[1],current.getString("tema"),current.getString("pemateri"),
                                        current.getString("alamat"),current.getString("latitude"),current.getString("longitude"),
                                        current.getString("deskripsi"),current.getString("jenis_peserta"),current.getString("gambar")));
                            }
                            listView.setAdapter(new KajianAdapter(JadwalKajian.this,kajianList,width));
                        } catch (JSONException e) {
                            Toast.makeText(JadwalKajian.this,"Terjadi kesalahan, silahkan coba lagi.",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (JSONException e) {
            Toast.makeText(JadwalKajian.this,"Terjadi kesalahan, silahkan coba lagi.",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            e.printStackTrace();
        }
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog mDialog = new DatePickerDialog(JadwalKajian.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        tanggal.setText(String.valueOf(dayOfMonth) + " " +
                                new DateFormatSymbols().getMonths()[monthOfYear] + " " + String.valueOf(year));
                        tanggalKajian = String.valueOf(year) + "-" +
                                String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth) + " 00:00:00";
                        tanggalKajian1 = String.valueOf(year) + "-" +
                                String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth+1) + " 00:00:00";
                        progressBar.setVisibility(View.VISIBLE);
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("from",String.valueOf(tanggalKajian));
                            jsonObject.put("to",String.valueOf(tanggalKajian1));
                            kajianNetwork.GetKajian(jsonObject, new JsonCallback() {
                                @Override
                                public void Done(JSONObject jsonObject, String message) {
                                    progressBar.setVisibility(View.GONE);
                                    if(message.equals(ConnectionHandler.response_message_success) && jsonObject != null){
                                        try {
                                            kajianList.clear();
                                            JSONArray data = new JSONArray(jsonObject.getString("data"));
                                            for(int i = 0; i < data.length(); i++){
                                                JSONObject current = data.getJSONObject(i);
                                                kajianList.add(new Kajian(0,current.getInt("id"),current.getInt("isRutin"),current.getString("waktu").split(" ")[0],
                                                        current.getString("waktu").split(" ")[1],current.getString("tema"),current.getString("pemateri"),
                                                        current.getString("alamat"),current.getString("latitude"),current.getString("longitude"),
                                                        current.getString("deskripsi"),current.getString("jenis_peserta"),current.getString("gambar")));
                                            }
                                            listView.setAdapter(new KajianAdapter(JadwalKajian.this,kajianList,width));
                                        } catch (JSONException e) {
                                            Toast.makeText(JadwalKajian.this,"Terjadi kesalahan, silahkan coba lagi.",Toast.LENGTH_LONG).show();
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            Toast.makeText(JadwalKajian.this,"Terjadi kesalahan, silahkan coba lagi.",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                mDialog.show();
            }
        });
        listView = (ListView) findViewById(R.id.list_kajian);
        //KajianAdapter adapter = new KajianAdapter(this, integers);
        //listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,MainActivity.class));
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
