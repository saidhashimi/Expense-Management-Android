package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddExpenseActivity extends AppCompatActivity {

    private DatabaseHelper db;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;



    private Button saveExpense;

    //Variables
    private Spinner type;
    private Spinner tripSpinner;
    private EditText amount;
    private TextView date;
    private EditText comments;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addexpense);

        db=new DatabaseHelper(this);
        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String userid=(shared.getString("id",""));

        type=findViewById(R.id.expenseType);
        amount=findViewById(R.id.amount);
        date=findViewById(R.id.expenseDate);
        comments=findViewById(R.id.comments);



        tripSpinner=findViewById(R.id.userTrips);
        ArrayList<String> arrayList = new ArrayList<>();

        Cursor cc=db.getUserTrips(userid);

        int count=cc.getCount();

        if (count==0){
            Toast.makeText(this, "You don't have any trip!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,MyTripActivity.class);
            startActivity(intent);
        }

        arrayList.add("Select Here");
        while (cc.moveToNext()){
            int index;

            index = cc.getColumnIndexOrThrow("trip_id");
            String trip_id = cc.getString(index);

            index = cc.getColumnIndexOrThrow("trip_name");
            String trip_name = cc.getString(index);

            arrayList.add(trip_name);
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(trip_name,trip_id);
            editor.commit();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tripSpinner.setAdapter(arrayAdapter);

        saveExpense=findViewById(R.id.saveExpense);

        saveExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpense();
            }
        });

    }

    public void addExpense(){

        String tt=type.getSelectedItem().toString();
        String tr=tripSpinner.getSelectedItem().toString();
        String am=amount.getText().toString();
        String da=date.getText().toString();
        String coom=comments.getText().toString();

        if (tt=="Select Here"){
            Toast.makeText(this, "Please select expense type", Toast.LENGTH_SHORT).show();
        } else if (tr=="Select Here") {
            Toast.makeText(this, "Please select trip type", Toast.LENGTH_SHORT).show();

        }else if (am.isEmpty()){
            amount.setError("Required Field");
            Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show();
        }else if (da.isEmpty()){
            date.setError("Required Field");
            Toast.makeText(this, "Please select the date", Toast.LENGTH_SHORT).show();
        }else{
            SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            String trip_id=(shared.getString(tr,""));

            db.addExpense(tt,am,da,coom,Integer.parseInt(trip_id));

            Toast.makeText(this, "Expense Added Successfully", Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(this,MyTripActivity.class);
            startActivity(intent);
        }






    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new da();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void updateTripDate (LocalDate tripDate){
        TextView tripText= (TextView) findViewById(R.id.expenseDate);
        tripText.setText(tripDate.toString());
    }

}