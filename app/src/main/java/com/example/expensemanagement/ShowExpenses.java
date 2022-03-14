package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowExpenses extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private DatabaseHelper db;

    private TextView type;
    private TextView amount;
    private TextView date;
    private TextView comments;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);

        type=findViewById(R.id.expenseViewType);
        amount=findViewById(R.id.expenseViewAmount);
        date=findViewById(R.id.expenseViewDate);
        comments=findViewById(R.id.expenseViewComments);


        db=new DatabaseHelper(this);
        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String exp_id=(shared.getString("selectedexpense",""));


        Cursor cc=db.getExpenseDetails(exp_id);

        while (cc.moveToNext())
        {
            int index;

            index = cc.getColumnIndexOrThrow("exp_type");
            String expType = cc.getString(index);

            index = cc.getColumnIndexOrThrow("exp_amount");
            String expAmount = cc.getString(index);


            index = cc.getColumnIndexOrThrow("exp_date");
            String expDate = cc.getString(index);

            index = cc.getColumnIndexOrThrow("exp_comment");
            String expComment = cc.getString(index);


            type.setText(expType);
            amount.setText(expAmount+"£");
            date.setText(expDate);
            comments.setText(expComment);



        }

        back=findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBack();
            }
        });



    }

    public void openBack(){
        Intent ii=new Intent(this, ShowExpenseActivity.class);
        startActivity(ii);
    }

}