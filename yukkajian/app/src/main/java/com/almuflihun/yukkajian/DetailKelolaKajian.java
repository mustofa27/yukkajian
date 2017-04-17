package com.almuflihun.yukkajian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import entity.Kajian;
import helper.FontManager;
import network.ConnectionHandler;

public class DetailKelolaKajian extends AppCompatActivity {
    ImageView thumbnail;
    TextView tanggal,jam,alamat,pemateri,judul,desc;
    Kajian current;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kelola_kajian);
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
        findViewById(R.id.list_pertanyaan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailKelolaKajian.this,PertanyaanKajian.class);
                intent.putExtra("kajian",current);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,KelolaKajian.class));
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
