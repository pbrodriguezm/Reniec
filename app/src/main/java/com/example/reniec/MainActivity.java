package com.example.reniec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btn_buscar;
    private EditText et_dni;
    private TextView tx_nombre, txt_apellido1, txt_apellido2;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_buscar = findViewById(R.id.btn_buscar);
        et_dni=findViewById(R.id.et_dni);
        tx_nombre=findViewById(R.id.txt_nombre);
        linearLayout= findViewById(R.id.linearLayout);
        txt_apellido1 = findViewById(R.id.txt_apellido1);
        txt_apellido2 = findViewById(R.id.txt_apellido2);
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExtraerJSON("a");

            }
        });

    }
    RequestQueue mRequestQueue;
    private void ExtraerJSON(String urlspoty) {
        Response.Listener<String> response_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Responsess",response);

                try {
                    JSONArray items = new JSONObject(response).getJSONArray("items");

                    System.out.println("*****JARRAY*****"+items.length());
                    for(int i=0;i<items.length();i++){
                        JSONObject json_data = items.getJSONObject(i);
                        TextView tx= new TextView(getBaseContext());
                        tx.setText(json_data.getString("name"));
                        linearLayout.addView(tx);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        Response.ErrorListener response_error_listener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //TODO
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    //TODO
                } else if (error instanceof NetworkError) {
                    //TODO
                } else if (error instanceof ParseError) {
                    //TODO
                }
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://api.spotify.com/v1/me/playlists",
                response_listener,response_error_listener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer BQB3Q6aF0j90jI_5x1gmbBD6pkqh3mM8KYF-IwgmzZtZf49hG4D_KsShRLp5Q7mEmvcirQAD6jwi1qvMNDcW6JN5_Or_amtZMeKx0L2XdzQzpgZyJfdgn34uHn0E-4EHXkr_mlUDfK2SLWgVdXQsEyLBzOHCZqTq");

                return params;
            }
        };

        getRequestQueue().add(stringRequest);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }



}

