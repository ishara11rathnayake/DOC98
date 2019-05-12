package com.industrialmaster.doc98;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    AutoCompleteTextView actvHospitals, actvSpecialities, actvDoctors;
    int HID, DID, SID; //selected values
    Map<String, Integer> data1 = new HashMap<>(); //Hospital list
    Map<String, Integer> data2 = new HashMap<>(); //Speciality list
    Map<String, Integer> data3 = new HashMap<>(); //Doctor list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences profile = getSharedPreferences("profile", Context.MODE_PRIVATE);

        if(profile.contains("NAME")) {
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            actvHospitals = findViewById(R.id.actv_hospital);
            actvSpecialities = findViewById(R.id.actv_speciality);
            actvDoctors = findViewById(R.id.actv_doctor);
        }else{
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDoctors();
        loadHospitals();
        loadSpecialities();

    }

    public void loadHospitals(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://idexserver.tk/im/channel/hospital/list.php";

        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<String> list = new ArrayList<String>();

                        for (int i = 0; i<response.length(); i++){
                            try{
                                JSONObject obj = response.getJSONObject(i);
                                list.add(obj.getString("name"));
                                data1.put(obj.getString("name"), obj.getInt("id"));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        int layout = android.R.layout.simple_list_item_1;
                        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, layout, list);
                        actvHospitals.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error :"+error, Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(request1);

    }

    public void loadSpecialities(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://idexserver.tk/im/channel/speciality/list.php";

        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<String> list = new ArrayList<String>();

                        for (int i = 0; i<response.length(); i++){
                            try{
                                JSONObject obj = response.getJSONObject(i);
                                list.add(obj.getString("name"));
                                data2.put(obj.getString("name"), obj.getInt("id"));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        int layout = android.R.layout.simple_list_item_1;
                        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, layout, list);
                        actvSpecialities.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error :"+error, Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(request1);

    }

    public void loadDoctors(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://idexserver.tk/im/channel/doctor/list.php";

        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<String> list = new ArrayList<String>();

                        for (int i = 0; i<response.length(); i++){
                            try{
                                JSONObject obj = response.getJSONObject(i);
                                list.add(obj.getString("name"));
                                data3.put(obj.getString("name"), obj.getInt("id"));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        int layout = android.R.layout.simple_list_item_1;
                        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, layout, list);
                        actvDoctors.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error :"+error, Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(request1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_appointments) {
            Intent intent = new Intent(this, AppointmentsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_doctors) {
            Intent intent = new Intent(this, DoctorListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_hospitals) {
            Intent intent = new Intent(this, HospitalListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_specialities) {
            Intent intent = new Intent(this, SpecialityListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_exit) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void search(View view){
        if(actvHospitals.getText().toString().equals("")){
            HID = 0;
        }else{
            if(data1.get(actvHospitals.getText().toString()) != null){
                HID = data1.get(actvHospitals.getText().toString());
            }
        }

        if(actvSpecialities.getText().toString().equals("")){
            SID = 0;
        }else{
            if(data1.get(actvSpecialities.getText().toString()) != null){
                SID = data2.get(actvSpecialities.getText().toString());
            }
        }

        if(actvDoctors.getText().toString().equals("")){
            DID = 0;
        }else{
            if(data1.get(actvDoctors.getText().toString()) != null){
                DID = data3.get(actvDoctors.getText().toString());
            }

        }

        Intent intent = new Intent(this, SessionListActivity.class);
        intent.putExtra("HID", HID);
        intent.putExtra("SID", SID);
        intent.putExtra("DID", DID);

        startActivity(intent);
    }

    public void  clear(View view){
        actvDoctors.setText("");
        actvSpecialities.setText("");
        actvHospitals.setText("");
    }
}
