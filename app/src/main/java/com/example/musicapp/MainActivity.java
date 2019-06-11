package com.example.musicapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView listViewMusic;
    private MediaPlayer mediaPlayer;
    private ArrayAdapter adapter;
    private int[] resID = {R.raw.beh_chala,R.raw.chala,R.raw.got,R.raw.jigra};
    private String[] list = {"Beh_chala","Challa mein lad jana","Game of Thrones","Jigra"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewMusic = findViewById(R.id.list_view_music);
        adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item);
        listViewMusic.setAdapter(adapter);
        for (int i=0;i<list.length;i++) {
            adapter.add(list[i]);
        }

        listViewMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
                Intent intent = new Intent(MainActivity.this,PlayMusic.class);
                intent.putExtra("name",name);
                intent.putExtra("res",resID[position]);
                startActivity(intent);




            }
        });

    }
}
