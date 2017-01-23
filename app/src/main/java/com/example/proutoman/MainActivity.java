package com.example.proutoman;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.LocaleList;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Provider;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.location.LocationManager.GPS_PROVIDER;

public class MainActivity extends AppCompatActivity {
    private Button  getDate, submit;
    private EditText longitude, latitude;
    private TextView setSeek, date;
    private SeekBar proutometre;
    private int resultProut;
    private String resultDate;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDate = (Button) findViewById(R.id.getDate);
        submit = (Button) findViewById(R.id.submitResult);
        longitude = (EditText) findViewById(R.id.Longitude);
        latitude = (EditText) findViewById(R.id.latidue);
        proutometre = (SeekBar) findViewById(R.id.prout);
        setSeek = (TextView) findViewById(R.id.setSeek);
        date = (TextView) findViewById(R.id.Date);
        proutometre.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setSeek.setText("Prout : " + proutometre.getProgress() + "/" + proutometre.getMax());
                resultProut = seekBar.getProgress();
            }
        });
        getDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultDate = (new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                date.setText(resultDate);

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Float lon, lat;
                lon = Float.parseFloat(String.valueOf(longitude.getText()));
                lat = Float.parseFloat(String.valueOf(latitude.getText()));
                new Background_get().execute("lon="+lon + "lat="+lat +"date="+resultDate+"strength="+resultProut);
                ClassModel a = new ClassModel(lat, lon, resultProut, resultDate);
                database.setValue(a);
            }
        });
    }
    public class Background_get extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                /* Change the IP to the IP you set in the arduino sketch */
                URL url = new URL("http://192.168.0.117/prout-spotter/api/new.php?" + params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    result.append(inputLine).append("\n");

                in.close();
                connection.disconnect();
                return result.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
