package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends AppCompatActivity {

    private DatabaseHelper db;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    private String userName;
    private String userEmail;

    private TextView name;
    private TextView email;

    private Button changePassword;

    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


        db=new DatabaseHelper(this);
        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String userid=(shared.getString("id",""));

        name=findViewById(R.id.userName);
        email=findViewById(R.id.userEmail);

        changePassword=findViewById(R.id.changePassword);

        Cursor cc=db.getUserDetails(userid);

        while (cc.moveToNext()){
            int index;

            index = cc.getColumnIndexOrThrow("user_name");
             userName = cc.getString(index);

            index = cc.getColumnIndexOrThrow("user_email");
            userEmail = cc.getString(index);

        }

        name.setText(userName);
        email.setText(userEmail);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangePassword();
            }
        });

        logout=findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileLogout();
            }
        });








    }

    public void openChangePassword(){
        Intent intent=new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    public void profileLogout(){
        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        editor.clear();
        editor.commit();

        Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}