package com.tim3a.eangkotdriver;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
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

public class GetFareActivity extends AppCompatActivity {

    private ListView dataListView;
    private String driverId;
    public String alreadyPayStr;
    public boolean alreadyGetPrice = false;
    private static int STATIC_PRICE;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Payment");
    private DatabaseReference dbRef2 = database.getReference("PaymentTable");

    ArrayList<String> listItems = new ArrayList<>();
    ArrayAdapter<String> adapter;

    ArrayList<Integer> listIdGetFare = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_fare);

        dataListView = (ListView) findViewById(R.id.listV_getFare_dynamic);
        driverId = getJson();

        dbRef.orderByChild("id_driver").equalTo(driverId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // reset the ArrayList
                listItems.clear();
                listIdGetFare.clear();

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    final String id_customer = dataSnapshot1.child("id_customer").getValue().toString();
                    final int id = Integer.valueOf(dataSnapshot1.child("id").getValue().toString());
                    final int distance = Integer.valueOf(dataSnapshot1.child("distance").getValue().toString());
                    final boolean alreadyPay = Boolean.valueOf(dataSnapshot1.child("alreadyPay").getValue().toString());

                    dbRef2.orderByChild("distance").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()){
                                int getDistance = Integer.valueOf(dataSnapshot2.child("distance").getValue().toString());
                                if (getDistance > distance && !alreadyGetPrice){
                                    STATIC_PRICE = Integer.valueOf(dataSnapshot2.child("price").getValue().toString());
                                    alreadyGetPrice = true;

                                    if (!alreadyPay) {
                                        alreadyPayStr = "Belum";
                                    } else {
                                        alreadyPayStr = "Sudah";
                                    }

                                    String s =    "ID pembayaran: " + id
                                            + "\nID pelanggan: " + id_customer
                                            + "\nJarak tempuh: " + distance
                                            + "\nTarif: " + STATIC_PRICE
                                            + "\nSudah bayar: " + alreadyPayStr;

                                    // set data into ArrayList
                                    // only if the customer not pay yet
                                    if (!alreadyPay){
                                        listItems.add(s);
                                        listIdGetFare.add(id);
                                    }
                                }
                            }

                            alreadyGetPrice = false;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter = new ArrayAdapter<String>(this,
                R.layout.layout_listview_getfare,
                listItems);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dataListView.setAdapter(adapter);
                                dataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        // set id of payment into tempInt
                                        final int tempInt = listIdGetFare.get(position);
                                        System.out.println("Ini id dari payment: " + tempInt);

                                        // create AlertDialog
                                        AlertDialog.Builder myBuilder = new AlertDialog.Builder(GetFareActivity.this);
                                        myBuilder.setTitle("Anda akan menyatakan bahwa penumpang akan membayar.");
                                        myBuilder.setMessage("Tekan tombol OK jika penumpang sudah membayar, TIDAK untuk membatalkan.");

                                        // condition if the driver OK
                                        myBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                //////////////////////////////////////////
                                                // do something if driver already do OK //
                                                //////////////////////////////////////////

                                                // set alreadyPay into true
                                                String s = String.valueOf(tempInt);
                                                DatabaseReference dbRef2 = database.getReference().child("Payment").child(s).child("alreadyPay");
                                                dbRef2.setValue(true);

                                                Toast.makeText(GetFareActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                                finish();
                                                startActivity(getIntent());
                                            }
                                        });

                                        // condition if the driver TIDAK
                                        myBuilder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                        myBuilder.show();
                                    }
                                });
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
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
