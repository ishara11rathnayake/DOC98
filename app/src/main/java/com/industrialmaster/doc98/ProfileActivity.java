package com.industrialmaster.doc98;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences profile = getSharedPreferences("profile", Context.MODE_PRIVATE);
        String name = profile.getString("NAME", "Guest");
        EditText etname = findViewById(R.id.etname);
        etname.setText(name);

        String eamil = profile.getString("EMAIL", "example@gmail.com");
        EditText eteamil = findViewById(R.id.etemail);
        eteamil.setText(eamil);

        String currency = profile.getString("DOB", "11/03/1993");
        EditText etdob = findViewById(R.id.etdob);
        etdob.setText(currency);
    }

    public void save(View view){
        SharedPreferences profile = getSharedPreferences("profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor profileEditor = profile.edit();

        EditText etname = findViewById(R.id.etname);
        EditText etemail = findViewById(R.id.etemail);
        EditText etdob = findViewById(R.id.etdob);


        profileEditor.putString("NAME", etname.getText().toString());
        profileEditor.putString("EMAIL", etemail.getText().toString());
        profileEditor.putString("DOB", etdob.getText().toString());

        profileEditor.apply();

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
