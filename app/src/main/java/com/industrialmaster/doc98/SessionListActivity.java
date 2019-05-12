package com.industrialmaster.doc98;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

public class SessionListActivity extends AppCompatActivity {

    int SID, HID, DID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_list);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        HID = data.getInt("HID");
        SID = data.getInt("SID");
        DID = data.getInt("DID");
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ListView lv = findViewById(R.id.session_list);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://idexserver.tk/im/channel/doctor_session/list.php?hid="+HID+"&sid="+SID+"&did="+DID;

        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<HashMap<String, String>> list = new ArrayList<>();

                        for (int i = 0; i<response.length(); i++){
                            try{
                                JSONObject obj = response.getJSONObject(i);
                                HashMap<String, String> map = new HashMap();
                                map.put("doctor_name", obj.getString("doctor_name"));
                                map.put("hospital_name", obj.getString("hospital_name"));
                                map.put("speciality_name", obj.getString("speciality_name"));
                                map.put("date", obj.getString("date"));
                                map.put("id", obj.getString("id"));
                                list.add(map);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        int layout = R.layout.single_session;
                        int[] views = {R.id.doctor_name, R.id.doctor_speciality, R.id.hospital_name, R.id.date, R.id.session_id};
                        String[] cols = {"doctor_name", "speciality_name", "hospital_name", "date", "id"};
                        SimpleAdapter adapter = new SimpleAdapter(SessionListActivity.this, list, layout, cols, views);
                        lv.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SessionListActivity.this, "Error :"+error, Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(request1);
    }

    public void bookNow(View view){
        LinearLayout layout = (LinearLayout) view.getParent();
        TextView tv = layout.findViewById(R.id.session_id);

        String sessionId = tv.getText().toString();
        Intent intent = new Intent(this, BookingActivity.class);
        intent.putExtra("SID", sessionId);
        startActivity(intent);
    }
}
