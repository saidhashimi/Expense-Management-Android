package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button signUp;
    private Button login;

    private EditText userName;
    private EditText password;

    private TextView forgotPassword;
    private Session session;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    public static Context contextOfApplication;


    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String autoLogin=(shared.getString("id",""));

        contextOfApplication = getApplicationContext();

        if (!autoLogin.isEmpty()){
            Intent intent=new Intent(this, MyTripActivity.class);
            startActivity(intent);
        }

        db=new DatabaseHelper(this);
        signUp=findViewById(R.id.signup);
        login=findViewById(R.id.login);

        userName=findViewById(R.id.username);
        password=findViewById(R.id.password);

        forgotPassword=findViewById(R.id.forgot);

        session=new Session(this);






        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpActivity();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProcess();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgotPassword();
            }
        });


    }

    public void openSignUpActivity(){
        Intent intent=new Intent(this,SignupActivity.class);
        startActivity(intent);
    }

    public void loginProcess(){
        String user=userName.getText().toString();
        String pass=password.getText().toString();

        if (user.isEmpty()){
            userName.setError("Required Field");

        }else if (pass.isEmpty()){
            password.setError("Required Field");
        }else{
            String userExist=db.userLogin(user,pass);

            if (userExist=="Wrong"){
                Toast.makeText(getApplicationContext(), "Incorrect User", Toast.LENGTH_LONG).show();
            }else{
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("id",userExist);
                editor.commit();

                Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(), MyTripActivity.class);
                startActivity(intent);

            }

        }
    }

    public void openForgotPassword(){
        Intent intent= new Intent(getApplicationContext(),ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }




}