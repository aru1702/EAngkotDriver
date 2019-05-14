package com.tim3a.eangkotdriver;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button openMap, getFare;
    private TextView personName;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refPool = database.getReference("Driver/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openMap = (Button) findViewById(R.id.button_main_openMap);
        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OpenMap.class));
            }
        });

        getFare = (Button) findViewById(R.id.button_main_getFare);
        getFare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GetFareActivity.class));
            }
        });

        personName = (TextView) findViewById(R.id.textV_main_driverName);

        String driverId = getJson();
        refPool.orderByChild("id").equalTo(driverId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String driverName = dataSnapshot1.child("name").getValue().toString();
                    personName.setText(driverName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String getJson() {
        String json;

        try {
            InputStream inputStream = getAssets().open("driver.json");
            int size = inputStream.available();
            byte [] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);

            return jsonObject.getString("id");

        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }

        return "";
    }
}
