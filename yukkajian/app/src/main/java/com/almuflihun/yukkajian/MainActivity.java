package com.almuflihun.yukkajian;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import fragment.Home;
import helper.FontManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    int aktif = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Toast.makeText(this, "This application need permission to use GPS to access your location", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            }
        }
        FontManager.markAsIconContainer(findViewById(R.id.menu_utama),FontManager.getTypeface(this,FontManager.font_awesome));
        ((LinearLayout)findViewById(R.id.menu_home)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.menu_setting)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.menu_help)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.menu_account)).setOnClickListener(this);
        onClick(findViewById(R.id.menu_home));
    }
    private void setAktif(View view){
        if(aktif != 0)
            findViewById(aktif).setActivated(false);
        view.setActivated(true);
        aktif = view.getId();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu_home:
                Home home = new Home();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_utama, home).commit();
                setAktif(findViewById(R.id.icon_home));
                break;
            case R.id.menu_setting:
                setAktif(findViewById(R.id.icon_setting));
                break;
            case R.id.menu_help:
                setAktif(findViewById(R.id.icon_help));
                break;
            case R.id.menu_account:
                setAktif(findViewById(R.id.icon_account));
                break;
        }
    }
}
