package com.industrialmaster.doc98;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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
import java.util.List;

public class HospitalListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ListView lv = findViewById(R.id.hospital_list);

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
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        int layout = android.R.layout.simple_list_item_1;
                        ArrayAdapter adapter = new ArrayAdapter(HospitalListActivity.this, layout, list);
                        lv.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HospitalListActivity.this, "Error :"+error, Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(request1);
    }
}
