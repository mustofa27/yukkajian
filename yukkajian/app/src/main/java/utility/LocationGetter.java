package utility;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import entity.Jadwal;
import model.JadwalModel;


/**
 * Created by LENOVO on 2/3/2017.
 */

public class LocationGetter {
    public static void requestSingleUpdate(final Context context) {
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled) {
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Calendar cal = Calendar.getInstance();
                    PrayTimeCounter prayers = new PrayTimeCounter();
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
                    ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
                            location.getLatitude(), location.getLongitude(), Double.parseDouble(z));
                    Jadwal jadwal = new Jadwal(0, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()),
                            prayerTimes.get(0), prayerTimes.get(1), prayerTimes.get(2), prayerTimes.get(3), prayerTimes.get(4),
                            prayerTimes.get(prayerTimes.size() - 1), String.valueOf(cal.getTimeInMillis()));
                    JadwalModel jadwalSQL = new JadwalModel(context);
                    jadwalSQL.InsertJadwal(jadwal);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context,"Location getter not permitted", Toast.LENGTH_LONG);
                return;
            }
            locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria,true),1000*60*60*1,45*1000,locationListener, null);
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Your GPS is disabled. Enable your GPS and WIFI and try again!")
                    .setCancelable(false)
                    .setTitle("Gps Status")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
    public static void requestSingleUpdate1(final Context context, final GetLocationCallback callback) {
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled) {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Processing");
            progressDialog.setMessage("Getting Location, please wait!");
            progressDialog.setCancelable(false);
            progressDialog.show();
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    float akurasi = location.getAccuracy();
                    progressDialog.dismiss();
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(context,"Location getter not permitted", Toast.LENGTH_LONG);
                        return;
                    }
                    callback.Done(location);
                    locationManager.removeUpdates(this);
                    Toast.makeText(context,"Akurasi : " + akurasi, Toast.LENGTH_LONG);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                progressDialog.dismiss();
                Toast.makeText(context,"Location getter not permitted", Toast.LENGTH_LONG);
                return;
            }
            locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria,true),1000*1,0,locationListener, null);
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Your GPS is disabled. Enable your GPS and WIFI and try again!")
                    .setCancelable(false)
                    .setTitle("Gps Status")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
