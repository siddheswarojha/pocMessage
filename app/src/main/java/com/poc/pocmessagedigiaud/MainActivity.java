package com.poc.pocmessagedigiaud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    Button btnSend;
    EditText etMessage, etPhoneNumber, etApiKey;
    String url = "http://web.cloudwhatsapp.com/wapp/api/send?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = findViewById(R.id.btnSend);
        etMessage = findViewById(R.id.etMessage);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etApiKey = findViewById(R.id.etApiKey);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apikey = etApiKey.getText().toString();
                String message = etMessage.getText().toString();
                String phone = etPhoneNumber.getText().toString();
                url = url + "apikey=" + apikey + "&mobile=" + phone + "&msg=" + message;
                Log.d("urlWA", url);

                String URL = url;   //parsing to URI
                sendWhatsAppMessage(URL, phone, message);


            }
        });
    }

    private void sendWhatsAppMessage(String message, String phone, String url) {

//        Toast.makeText(MainActivity.this, "Btn Clicked", Toast.LENGTH_SHORT).show();

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("responseFromServer", response.toString());
                try {
                    String message = response.getString("status");
                    if (message.equals("success")) {
                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}

