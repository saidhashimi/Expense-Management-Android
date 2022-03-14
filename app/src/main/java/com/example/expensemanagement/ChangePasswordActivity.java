package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

    private DatabaseHelper db;

    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    private String userOP;

    private Button changeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepass);

        oldPassword=findViewById(R.id.oldPassword);
        newPassword=findViewById(R.id.newPassword);
        confirmPassword=findViewById(R.id.confirmPassword);

        changeButton=findViewById(R.id.changeButton);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonPassword();
            }
        });










    }

    public void changeButtonPassword(){
        String oldP=oldPassword.getText().toString();
        String newP=newPassword.getText().toString();
        String conP=confirmPassword.getText().toString();

        db=new DatabaseHelper(this);
        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String userid=(shared.getString("id",""));

        Cursor cc=db.getUserDetails(userid);

        while (cc.moveToNext()){
            int index;

            index = cc.getColumnIndexOrThrow("user_password");
            userOP = cc.getString(index);

        }

        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if ((oldP.isEmpty()) && (newP.isEmpty()) && (conP.isEmpty())){
            Toast.makeText(this, "Enter passwords", Toast.LENGTH_SHORT).show();
            oldPassword.setError("Required Field");
            newPassword.setError("Required Field");
            confirmPassword.setError("Required Field");
        }
        else if (oldP.isEmpty()){
            Toast.makeText(this, "Enter your old password", Toast.LENGTH_SHORT).show();
            oldPassword.setError("Required Field");

        }else if (newP.isEmpty()){
            Toast.makeText(this, "Enter your new password", Toast.LENGTH_SHORT).show();
            newPassword.setError("Required Field");
        }else if (conP.isEmpty()){
            Toast.makeText(this, "Enter your confirm password", Toast.LENGTH_SHORT).show();
            confirmPassword.setError("Required Field");
        }
        else if(!oldP.equals(userOP)){
            Toast.makeText(this, "Your old password is wrong", Toast.LENGTH_SHORT).show();
            oldPassword.setError("wrong old password");
        } else if (!newP.matches(passwordVal)){
            Toast.makeText(this, "Weak Password", Toast.LENGTH_SHORT).show();
            newPassword.setError("weak password");
        }
        else if(!newP.equals(conP)){
            Toast.makeText(this, "Enter correct confirm password", Toast.LENGTH_SHORT).show();
            confirmPassword.setError("wrong password");
        }else{
            db.updatePassword(newP, userid);
            Toast.makeText(this, "Your Password is updated", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,UserProfileActivity.class);
            startActivity(intent);

        }

    }
}