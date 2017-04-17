package com.almuflihun.yukkajian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.PertanyaanAdapter;
import entity.Kajian;
import entity.Pertanyaan;
import network.ConnectionHandler;
import network.JsonCallback;
import network.PertanyaanNetwork;

public class PertanyaanKajian extends AppCompatActivity {
    PertanyaanNetwork pertanyaanNetwork;
    Kajian current;
    List<Pertanyaan> pertanyaanList;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pertanyaan_kajian);
        current = (Kajian)getIntent().getSerializableExtra("kajian");
        pertanyaanNetwork = new PertanyaanNetwork(this);
        pertanyaanList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);
        instanceView();
    }
    private void instanceView(){
        pertanyaanNetwork.GetPertanyaan(current.getSid(), new JsonCallback() {
            @Override
            public void Done(JSONObject jsonObject, String message) {
                if(message.equals(ConnectionHandler.response_message_success) && jsonObject != null){
                    try {
                        pertanyaanList.clear();
                        JSONArray data = new JSONArray(jsonObject.getString("data"));
                        for(int i = 0; i < data.length(); i++){
                            JSONObject curr = data.getJSONObject(i);
                            pertanyaanList.add(new Pertanyaan(0,curr.getInt("id"),curr.getInt("id_kajian"),curr.getInt("id_user"),
                                    curr.getString("pertanyaan")));
                        }
                        listView.setAdapter(new PertanyaanAdapter(PertanyaanKajian.this,pertanyaanList));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.refresh:
                instanceView();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
