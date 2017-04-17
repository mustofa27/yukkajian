package com.almuflihun.yukkajian;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import adapter.MyKajianAdapter;
import helper.FontManager;
import model.KajianModel;
import network.KajianNetwork;

public class KelolaKajian extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    KajianModel kajianModel;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_kajian);
        kajianModel = new KajianModel(this);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KelolaKajian.this,CreateKajian.class));
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new MyKajianAdapter(this,kajianModel.GetAllKajian()));
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
