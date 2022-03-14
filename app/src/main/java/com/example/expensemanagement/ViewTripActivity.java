package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTripActivity extends AppCompatActivity {

    private DatabaseHelper db;

    private TextView name;
    private TextView destination;
    private TextView date;
    private TextView risk;
    private TextView description;
    private TextView duration;
    private TextView mode;


    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewtrip);

        db=new DatabaseHelper(this);
        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String trip_id=(shared.getString("selectedtrip",""));

        name=findViewById(R.id.tripName);
        destination=findViewById(R.id.tripDestination);
        risk=findViewById(R.id.tripRisk);
        description=findViewById(R.id.tripDescription);
        duration=findViewById(R.id.tripDuration);
        mode=findViewById(R.id.tripMode);
        date=findViewById(R.id.tripDate);

        Cursor cc=db.getTripDetails(trip_id);

        while (cc.moveToNext())
        {
            int index;

            index = cc.getColumnIndexOrThrow("trip_name");
            String tripName = cc.getString(index);

            index = cc.getColumnIndexOrThrow("trip_destination");
            String tripDestination = cc.getString(index);


            index = cc.getColumnIndexOrThrow("trip_date");
            String tripDatte = cc.getString(index);

            index = cc.getColumnIndexOrThrow("trip_risk");
            String tripRisk = cc.getString(index);

            index = cc.getColumnIndexOrThrow("trip_description");
            String tripDescription = cc.getString(index);

            index = cc.getColumnIndexOrThrow("trip_duration");
            String tripDuration = cc.getString(index);

            index = cc.getColumnIndexOrThrow("trip_mode");
            String tripMode = cc.getString(index);

            name.setText(tripName);
            destination.setText(tripDestination);
            date.setText(tripDatte);
            risk.setText(tripRisk);
            description.setText(tripDescription);
            duration.setText(tripDuration);
            mode.setText(tripMode);


        }




    }
}