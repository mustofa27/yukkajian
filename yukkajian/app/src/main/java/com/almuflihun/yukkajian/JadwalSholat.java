package com.almuflihun.yukkajian;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import entity.Jadwal;
import model.JadwalModel;
import utility.GetLocationCallback;
import utility.LocationGetter;
import utility.PrayTimeCounter;

public class JadwalSholat extends AppCompatActivity {
    Location location;
    JadwalModel jadwalSQL;
    Jadwal jadwal;
    TextView subuh,terbit,dzuhur,asr,maghrib,isya;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_sholat);
        jadwalSQL = new JadwalModel(this);
        jadwal = jadwalSQL.GetJadwal();
        subuh = (TextView) findViewById(R.id.wktFajr);
        terbit = (TextView) findViewById(R.id.wktShuruq);
        dzuhur = (TextView) findViewById(R.id.wktDuhr);
        asr = (TextView) findViewById(R.id.wktAsr);
        maghrib = (TextView) findViewById(R.id.wktMaghrib);
        isya = (TextView) findViewById(R.id.wktIsha);
        final Calendar cal = Calendar.getInstance();
        if(jadwal == null || (Double.parseDouble(jadwal.getTanggal()) - cal.getTimeInMillis())>= 24*60*60*1000) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            final PrayTimeCounter prayers = new PrayTimeCounter();
            prayers.setTimeFormat(prayers.getTime24());
            prayers.setCalcMethod(prayers.getEgypt());
            prayers.setAsrJuristic(prayers.getShafii());
            prayers.setAdjustHighLats(prayers.getAngleBased());
            int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
            prayers.tune(offsets);
            TimeZone tz = TimeZone.getDefault();
            String gmt = TimeZone.getTimeZone(tz.getID()).getDisplayName(false,
                    TimeZone.SHORT);
            String z1 = "";
            String z = "";
            if(gmt.contains("+")) {
                z1 = gmt.split("\\+")[1];
                z = z1.replaceAll(":", ".");
            }
            else if(gmt.contains("-")){
                z1 = gmt.split("-")[1];
                z = z1.replaceAll(":", ".");
                z = "-"+z;
            }
            else
                z = "07.00";
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"Location getter not permitted",Toast.LENGTH_LONG);
            }
            location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria,true));
            if(location == null){
                final String finalZ = z;
                LocationGetter.requestSingleUpdate1(this, new GetLocationCallback() {
                    @Override
                    public void Done(Location location1) {
                        location = location1;
                        ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
                                location.getLatitude(), location.getLongitude(), Double.parseDouble(finalZ));
                        jadwal = new Jadwal(0, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()),
                                prayerTimes.get(0), prayerTimes.get(1), prayerTimes.get(2), prayerTimes.get(3), prayerTimes.get(4),
                                prayerTimes.get(prayerTimes.size() - 1), String.valueOf(cal.getTimeInMillis()));
                        JadwalModel jadwalSQL = new JadwalModel(JadwalSholat.this);
                        jadwalSQL.InsertJadwal(jadwal);
                        instanceView();
                    }
                });
            }
            else{
                ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
                        location.getLatitude(), location.getLongitude(), Double.parseDouble(z));
                jadwal = new Jadwal(0, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()),
                        prayerTimes.get(0), prayerTimes.get(1), prayerTimes.get(2), prayerTimes.get(3), prayerTimes.get(4),
                        prayerTimes.get(prayerTimes.size() - 1), String.valueOf(cal.getTimeInMillis()));
                JadwalModel jadwalSQL = new JadwalModel(this);
                jadwalSQL.InsertJadwal(jadwal);
                instanceView();
            }
        }
        else {
            instanceView();
        }
    }
    private void instanceView(){
        subuh.setText(jadwal.getSubuh());
        terbit.setText(jadwal.getTerbit());
        dzuhur.setText(jadwal.getDzuhur());
        asr.setText(jadwal.getAsr());
        maghrib.setText(jadwal.getMaghrib());
        isya.setText(jadwal.getIsya());
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
