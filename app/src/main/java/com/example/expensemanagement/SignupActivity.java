package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    private TextView member;
    private Button signup;

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private DatabaseHelper db;
    boolean isCheckAllFields=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        member=findViewById(R.id.member);
        firstName=findViewById(R.id.firstname);
        lastName=findViewById(R.id.Lastname);
        email=findViewById(R.id.signemail);
        password=findViewById(R.id.signpass);

        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        signup=findViewById(R.id.signUpButton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               isCheckAllFields=checkALlFields();

               if (isCheckAllFields){
                   saveUser();
               }
            }
        });

    }

    public  void openMainActivity(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

   private boolean checkALlFields(){
       String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
       String passwordVal = "^" +
               //"(?=.*[0-9])" +         //at least 1 digit
               //"(?=.*[a-z])" +         //at least 1 lower case letter
               //"(?=.*[A-Z])" +         //at least 1 upper case letter
               "(?=.*[a-zA-Z])" +      //any letter
               "(?=.*[@#$%^&+=])" +    //at least 1 special character
               "(?=\\S+$)" +           //no white spaces
               ".{4,}" +               //at least 4 characters
               "$";
        if (firstName.length()==0){
            firstName.setError("This field is required!");
            return false;
        }if (lastName.length()==0){
           lastName.setError("This field is required!");
           return false;
       }if (email.getText().toString().isEmpty()) {
           email.setError("Field cannot be empty");
           return false;
       } if (!email.getText().toString().matches(emailPattern)) {
           email.setError("Invalid email address");
           return false;
       }if (password.getText().toString().isEmpty()){
            password.setError("Field cannot be empty");
            return false;
       } if (!password.getText().toString().matches(passwordVal)) {
           password.setError("Password is too weak");
           return false;
       }else{
            return true;
       }

   }

    public void saveUser(){

    db=new DatabaseHelper(SignupActivity.this);

        String fName=firstName.getText().toString();
        String lNamee=lastName.getText().toString();
        String em=email.getText().toString();
        String pass=password.getText().toString();

        String name=fName+" "+lNamee;

            boolean userExist = db.userAvialable(em);

            if (userExist){
                Toast.makeText(getApplicationContext(), "User Exist", Toast.LENGTH_LONG).show();
                firstName.setText("");
                lastName.setText("");
                email.setText("");
                password.setText("");

            }else {
                db.insertUsers(name, em, pass);
                Toast.makeText(getApplicationContext(), "Sign up completed", Toast.LENGTH_LONG).show();
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }





    }


}