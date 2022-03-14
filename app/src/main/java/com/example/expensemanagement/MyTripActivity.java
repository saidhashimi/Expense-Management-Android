package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyTripActivity extends AppCompatActivity {

    private FloatingActionButton trip;
    private FloatingActionButton addExpense;
    private FloatingActionButton userProfile;

    private DatabaseHelper db;

    private Spinner tripsList;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    private Button viewTrip;
    private Button updateButton;
    private Button expenseButton;

    private Button deleteButton;
    private TextView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mytrips);

        db=new DatabaseHelper(this);

        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String user_id=(shared.getString("id",""));

        //Trips Count
        int totalTrips=db.getTripCount(user_id);
        TextView v=findViewById(R.id.countTrips);
        v.setText(String.valueOf(totalTrips));

        //Total Expense

        int totalExpense=db.getTotalExpense();
        TextView vv=findViewById(R.id.countExpense);
        vv.setText(String.valueOf(totalExpense)+"£");

        // Start
        tripsList=findViewById(R.id.tripUser);
        ArrayList<String> arrayList = new ArrayList<>();

        Cursor cc=db.getUserTrips(user_id);

        int count=cc.getCount();

        if (count==0){
            arrayList.add("No trips");
        }else {

            arrayList.add("Select Here");
            while (cc.moveToNext()) {
                int index;

                index = cc.getColumnIndexOrThrow("trip_id");
                String trip_id = cc.getString(index);

                index = cc.getColumnIndexOrThrow("trip_name");
                String trip_name = cc.getString(index);

                arrayList.add(trip_name);
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(trip_name, trip_id);
                editor.commit();
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tripsList.setAdapter(arrayAdapter);

        //End











        trip= findViewById(R.id.newTrip);
        addExpense=findViewById(R.id.addExpense);
        userProfile=findViewById(R.id.userProfile);

        trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTripActivity();
            }
        });

        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddExpense();
            }
        });
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserProfile();
            }
        });


        //Buttons Handling

        viewTrip=findViewById(R.id.viewButton);

        viewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOpen();
            }
        });

        updateButton=findViewById(R.id.updateButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpdate();
            }
        });

        expenseButton=findViewById(R.id.expenseButton);

        expenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExpense();
            }
        });

        deleteButton=findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTrip();
            }
        });



        search=findViewById(R.id.searchhere);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch();
            }
        });

    }

    public void showTripActivity(){
        Toast.makeText(this, "Add new trip", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(this, NewTripActivity.class);
        startActivity(intent);
    }

    public void openAddExpense(){
        Toast.makeText(this, "Add your expense", Toast.LENGTH_LONG).show();

        Intent ii=new Intent(this, AddExpenseActivity.class);
        startActivity(ii);
    }

    public void openUserProfile(){
        Toast.makeText(this, "Your Profile", Toast.LENGTH_LONG).show();

        Intent ii=new Intent(this, UserProfileActivity.class);
        startActivity(ii);
    }

    public void viewOpen(){

        String checkTrip=tripsList.getSelectedItem().toString();

        if (checkTrip.equals("Select Here")){
            Toast.makeText(this, "Please select a trip", Toast.LENGTH_SHORT).show();
        }else if(checkTrip.equals("No trips")){
            Toast.makeText(this, "You don't have any trip!", Toast.LENGTH_SHORT).show();
        }else{


            SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            String selectedTrip=(shared.getString(checkTrip,""));

            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("selectedtrip", selectedTrip);
            editor.commit();

            Intent ii=new Intent(this,ViewTripActivity.class);
            startActivity(ii);
        }

    }
    public void openUpdate(){

        String checkTrip=tripsList.getSelectedItem().toString();

        if (checkTrip.equals("Select Here")){
            Toast.makeText(this, "Please select a trip", Toast.LENGTH_SHORT).show();
        }else if(checkTrip.equals("No trips")){
            Toast.makeText(this, "You don't have any trip!", Toast.LENGTH_SHORT).show();
        }else{


            SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            String selectedTrip=(shared.getString(checkTrip,""));

            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("selectedtrip", selectedTrip);
            editor.commit();

            Intent ii=new Intent(this,UpdateTripActivity.class);
            startActivity(ii);
        }

    }

    public void openExpense(){
        String checkTrip=tripsList.getSelectedItem().toString();

        if (checkTrip.equals("Select Here")){
            Toast.makeText(this, "Please select a trip", Toast.LENGTH_SHORT).show();
        }else if(checkTrip.equals("No trips")){
            Toast.makeText(this, "You don't have any trip!", Toast.LENGTH_SHORT).show();
        }else{


            SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            String selectedTrip=(shared.getString(checkTrip,""));

            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("selectedtrip", selectedTrip);
            editor.commit();

            Intent ii=new Intent(this,ShowExpenseActivity.class);
            startActivity(ii);
        }
    }

    public void deleteTrip(){
        String checkTrip=tripsList.getSelectedItem().toString();

        if (checkTrip.equals("Select Here")){
            Toast.makeText(this, "Please select a trip", Toast.LENGTH_SHORT).show();
        }else if(checkTrip.equals("No trips")){
            Toast.makeText(this, "You don't have any trip!", Toast.LENGTH_SHORT).show();
        }else{


            SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            String trip_id=(shared.getString(checkTrip,""));

            db.deleteTripss(trip_id);
            Toast.makeText(this, "Trip has been deleted", Toast.LENGTH_SHORT).show();
            Intent ii=new Intent(this,MyTripActivity.class);
            startActivity(ii);
        }
    }



    public void openSearch(){
        Intent ii=new Intent(this,SearchActivity.class);

        Toast.makeText(this, "Search Here", Toast.LENGTH_SHORT).show();
        startActivity(ii);
    }



}