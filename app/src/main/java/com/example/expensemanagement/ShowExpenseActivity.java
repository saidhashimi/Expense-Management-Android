package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowExpenseActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private DatabaseHelper db;

    private Spinner expenseSpinner;
    private Button expenseShow;
    private Button delteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expenses);

        db=new DatabaseHelper(this);

        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String trip_id=(shared.getString("selectedtrip",""));

        //Total Expense

        int totalExpense=db.getTotalExpense();
        TextView vv=findViewById(R.id.expenseView);
        vv.setText(String.valueOf(totalExpense)+"£");

        Cursor cc=db.getTripExpenses(trip_id);

        int count=cc.getCount();
        ArrayList<String> arrayList = new ArrayList<>();
        expenseSpinner=findViewById(R.id.expenseSpinner);

        if (count==0){
            arrayList.add("No Expenses");
        }else {

            arrayList.add("Your Expenses");
            while (cc.moveToNext()) {
                int index;

                index = cc.getColumnIndexOrThrow("exp_id");
                String exp_id = cc.getString(index);

                index = cc.getColumnIndexOrThrow("exp_type");
                String exp_type = cc.getString(index);

                arrayList.add(exp_type);
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(exp_type, exp_id);
                editor.commit();
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseSpinner.setAdapter(arrayAdapter);

        expenseShow=findViewById(R.id.expenseShow);

        expenseShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExpense();
            }
        });


        delteButton=findViewById(R.id.expenseDelete);
        delteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDelete();
            }
        });

    }

    public void showExpense(){
        String checkTrip=expenseSpinner.getSelectedItem().toString();



        if(checkTrip.equals("Your Expenses")){
            Toast.makeText(this, "Select expense", Toast.LENGTH_SHORT).show();
        }else if(checkTrip.equals("No Expenses")){
            Toast.makeText(this, "No Expenses", Toast.LENGTH_SHORT).show();
        }else {
            SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            String selectedExpense = (shared.getString(checkTrip, ""));

            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("selectedexpense", selectedExpense);
            editor.commit();

            Intent ii = new Intent(this, ShowExpenses.class);
            startActivity(ii);

        }

    }

    public void openDelete(){
        String checkTrip=expenseSpinner.getSelectedItem().toString();

        if(checkTrip.equals("Your Expenses")){
            Toast.makeText(this, "Select expense", Toast.LENGTH_SHORT).show();
        }else if(checkTrip.equals("No Expenses")){
            Toast.makeText(this, "No Expenses", Toast.LENGTH_SHORT).show();
        }else {
            SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            String exp_id = (shared.getString(checkTrip, ""));

            db.deleteExpense(exp_id);

            Toast.makeText(this, "Your expense has been deleted!", Toast.LENGTH_SHORT).show();
            Intent ii=new Intent(this, ShowExpenseActivity.class);
            startActivity(ii);

        }


    }
}