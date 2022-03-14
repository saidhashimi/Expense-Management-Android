package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

public class UpdateTripActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private DatabaseHelper db;

    private EditText tripName;
    private EditText tripDestination;
    private TextView tripDate;
    private EditText tripDescription;
    private Spinner tripDuration;
    private Spinner tripMode;

    private Button updateButton;
    private String trip_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trip);

        db=new DatabaseHelper(this);
        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        trip_id=(shared.getString("selectedtrip",""));

        tripName=findViewById(R.id.updateName);
        tripDestination=findViewById(R.id.updateDestination);
        tripDate=findViewById(R.id.updateDate);
        tripDescription=findViewById(R.id.updateDescription);
        tripDuration=findViewById(R.id.updateDuration);
        tripMode=findViewById(R.id.updateMode);

        Cursor cc=db.getTripDetails(trip_id);

        while (cc.moveToNext())
        {
            int index;

            index = cc.getColumnIndexOrThrow("trip_name");
            String name = cc.getString(index);

            index = cc.getColumnIndexOrThrow("trip_destination");
            String destination = cc.getString(index);


            index = cc.getColumnIndexOrThrow("trip_date");
            String date = cc.getString(index);


            index = cc.getColumnIndexOrThrow("trip_description");
            String des = cc.getString(index);

            index = cc.getColumnIndexOrThrow("trip_duration");
            String duration = cc.getString(index);

            index = cc.getColumnIndexOrThrow("trip_mode");
            String mode = cc.getString(index);

            tripName.setText(name);
            tripDestination.setText(destination);
            tripDate.setText(date);

            tripDescription.setText(des);

            if (duration.equals("Select Here")){
                tripDuration.setSelection(0);
            } if (duration.equals("1 Day")){
            tripDuration.setSelection(1);
        }if (duration.equals("2 Day")){
            tripDuration.setSelection(2);
        }if (duration.equals("3 Day")){
            tripDuration.setSelection(3);
        }if (duration.equals("Weekly")){
            tripDuration.setSelection(4);
        }if (duration.equals("Monthly")){
            tripDuration.setSelection(5);
        }if (mode.equals("Select Here")){
            tripMode.setSelection(0);
        }if (mode.equals("On Airlane")){
            tripMode.setSelection(1);
        }if (mode.equals("On Car")){
            tripMode.setSelection(2);
        }if (mode.equals("On Bike")){
            tripMode.setSelection(3);
        }



        }

        //End While


        updateButton=findViewById(R.id.updateTripButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOpen();
            }
        });




    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new anotherDate();
        newFragment.show(getSupportFragmentManager(), "datePicker");


    }

    public void updateTripDate (LocalDate tripDate){
        TextView tripText= (TextView) findViewById(R.id.updateDate);

        tripText.setText(tripDate.toString());
    }

    public void updateOpen(){
        String name=tripName.getText().toString();
        String destination=tripDestination.getText().toString();
        String date=tripDate.getText().toString();
        String desc=tripDescription.getText().toString();
        String duration=tripDuration.getSelectedItem().toString();
        String mode=tripMode.getSelectedItem().toString();

        db.updateTrip(name,destination,date,desc,duration,mode,trip_id);
        Toast.makeText(this, "Your trip has updated!", Toast.LENGTH_SHORT).show();
        Intent ii=new Intent(this,MyTripActivity.class);
        startActivity(ii);
    }

}