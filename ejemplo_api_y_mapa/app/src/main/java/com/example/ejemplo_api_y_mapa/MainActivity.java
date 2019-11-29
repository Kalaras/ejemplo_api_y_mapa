package com.example.ejemplo_api_y_mapa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    static final String REQ_TAG = "TAG";

    private TextView textoRespuesta;
    private ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = RequestQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        textoRespuesta = findViewById(R.id.textoRespuesta);
        loader = findViewById(R.id.loader);

        loader.setVisibility(View.GONE);
    }

    public void abrirMapa(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void enviarSolicitud(View view) {
        getJsonResponsePost();
    }


    public void  getJsonResponsePost(){

        loader.setVisibility(View.VISIBLE);


        JSONObject json = new JSONObject();
        try {
            json.put("parametro", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "aqui viene la url", json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        /*aqui viene el codigo cuando se recibe una respuesta */

                        textoRespuesta.setText(response.toString());
                        loader.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //serverResp.setText("Error getting response");
            }
        });
        jsonObjectRequest.setTag(REQ_TAG);
        requestQueue.add(jsonObjectRequest);
    }
}
