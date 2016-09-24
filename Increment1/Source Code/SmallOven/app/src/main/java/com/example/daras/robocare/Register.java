package com.example.daras.robocare;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Register extends AppCompatActivity {



    EditText Name;
    EditText Age;
    EditText Email;
    EditText Password;
    RadioButton rdMale;
    RadioButton rdFemale;
    Button Register;

    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Name = (EditText)findViewById(R.id.txtName);
        Age = (EditText)findViewById(R.id.txtAge);
        Email = (EditText)findViewById(R.id.txtEmail);
        Password = (EditText)findViewById(R.id.txtPassword);
        Register = (Button)findViewById(R.id.btnRegister);
        rdMale = (RadioButton)findViewById(R.id.rdMale);
        rdFemale = (RadioButton)findViewById(R.id.rdFemale);


        gson = new Gson();

    }

    public void RegisterClicked(View v)
    {
        try
        {

            Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_LONG).show();
            Intent Options = new Intent(Register.this,Login_Activity.class);
            startActivity(Options);

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }




}
