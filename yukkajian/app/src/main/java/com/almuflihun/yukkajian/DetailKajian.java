package com.almuflihun.yukkajian;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import entity.Kajian;
import helper.FontManager;
import network.ConnectionHandler;
import utility.GetLocationCallback;
import utility.LocationGetter;

public class DetailKajian extends AppCompatActivity {
    ImageView thumbnail;
    TextView tanggal,jam,alamat,pemateri,judul,desc;
    Kajian current;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kajian);
        FontManager.markAsIconContainer(findViewById(R.id.container),FontManager.getTypeface(this,FontManager.font_awesome));
        current = (Kajian) getIntent().getSerializableExtra("kajian");
        calendar = Calendar.getInstance();
        String[] date = current.getTanggal().split("-");
        String[] time = current.getJam().split(":");
        calendar.set(Integer.valueOf(date[0]),Integer.valueOf(date[1])-1,Integer.valueOf(date[2]));
        thumbnail = (ImageView) findViewById(R.id.thumbnail);
        tanggal = (TextView) findViewById(R.id.tanggal);
        jam = (TextView) findViewById(R.id.jam);
        alamat = (TextView) findViewById(R.id.alamat);
        pemateri = (TextView) findViewById(R.id.pemateri);
        judul = (TextView) findViewById(R.id.title);
        desc = (TextView) findViewById(R.id.desc);
        Picasso.with(this).load(ConnectionHandler.IMAGE_URL+current.getThumbnail()).into(thumbnail);
        tanggal.setText(String.valueOf(calendar.get(calendar.DAY_OF_MONTH)) + " " +
                new DateFormatSymbols().getMonths()[calendar.get(calendar.MONTH)] + " " + String.valueOf(calendar.get(calendar.YEAR)));
        jam.setText(time[0] + ":" + time[1]);
        alamat.setText(current.getAlamat());
        pemateri.setText(current.getPemateri());
        judul.setText(current.getTema());
        desc.setText(current.getDeskripsi());
        findViewById(R.id.checkin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationGetter.requestSingleUpdateDialog(DetailKajian.this, new GetLocationCallback() {
                    @Override
                    public void Done(Location location) {
                        Double distance = calculateDistance(Double.valueOf(current.getLat()),location.getLatitude()
                                ,Double.valueOf(current.getLongi()),location.getLongitude());
                        if(distance > 1000){
                            Toast.makeText(DetailKajian.this,"Lokasi anda terlalu jauh dari lokasi kajian, silahkan datangi lokasi kajian sesuai alamat yang tertera.",Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(DetailKajian.this,PostPertanyaan.class);
                            intent.putExtra("kajian",current);
                            finish();
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
    private double calculateDistance(double latitude1,double latitude2,double longitude1,double longitude2){
        double r = 6371;
        double latRadian = getRadian(latitude2-latitude1);
        double longRadian = getRadian(longitude2-longitude1);
        double a = Math.pow(Math.sin(latRadian/2),2) + Math.cos(getRadian(latitude1)) * Math.cos(getRadian(latitude2)) * Math.pow(Math.sin(longRadian/2),2);
        return r * 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a))*1000;
    }
    private double getRadian(double degree){
        return degree*(Math.PI/180);
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
