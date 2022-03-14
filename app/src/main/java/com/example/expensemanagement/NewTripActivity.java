package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

public class NewTripActivity extends AppCompatActivity {

    private Button save;

    //Variables
    private EditText tripName;
    private EditText destination;
    private TextView date;

    private RadioGroup risk;
    private RadioButton radrioRisk;

    private EditText tripDes;
    private Spinner type;
    private Spinner mode;
    private DatabaseHelper db;



    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtrip);

        save=findViewById(R.id.updateTripButton);

        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        userid=(shared.getString("id",""));

        tripName=findViewById(R.id.updateName);
        destination=findViewById(R.id.updateDestination);
        date=findViewById(R.id.updateDate);
        tripDes=findViewById(R.id.updateDescription);
        type=findViewById(R.id.updateDuration);
        mode=findViewById(R.id.updateMode);

        risk=(RadioGroup) findViewById(R.id.updateRisk);








        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewTrip();
            }
        });

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void saveNewTrip(){

        db=new DatabaseHelper(NewTripActivity.this);
        String trip=tripName.getText().toString();
        String dest=destination.getText().toString();
        String dat=date.getText().toString();



        String des=tripDes.getText().toString();
        String tripDuration=type.getSelectedItem().toString();
         String typp=mode.getSelectedItem().toString();

         int selectedID=risk.getCheckedRadioButtonId();
         radrioRisk=(RadioButton) findViewById(selectedID);


        if (trip.isEmpty()){
            tripName.setError("Required Field");
            Toast.makeText(this, "Complete the required Fields", Toast.LENGTH_SHORT).show();
        }else if(dest.isEmpty()){
            destination.setError("Required Field");
            Toast.makeText(this, "Complete the required Fields", Toast.LENGTH_SHORT).show();
        }else if(dat.isEmpty()){
            date.setError("Required Field");
            Toast.makeText(this, "Complete the required Fields", Toast.LENGTH_SHORT).show();
        }else if (risk.getCheckedRadioButtonId()==-1){
            Toast.makeText(this, "Select Risk Assessment", Toast.LENGTH_SHORT).show();

        }else{

            int us=Integer.parseInt(userid);
            db.newTrip(trip,dest,dat,radrioRisk.getText().toString(),des,tripDuration,typp,us);
            Toast.makeText(this, "Your trip has been added", Toast.LENGTH_SHORT).show();


            Intent intent=new Intent(getApplicationContext(), MyTripActivity.class);
            startActivity(intent);



        }








    }

    public void updateTripDate(LocalDate tripDate) {
        TextView tripText= (TextView) findViewById(R.id.updateDate);
        tripText.setText(tripDate.toString());
    }
}