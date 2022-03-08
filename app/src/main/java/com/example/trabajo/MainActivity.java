package com.example.trabajo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PostProcessor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.example.trabajo.model.Publicacion;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> datos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listViewDatos);
        arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,datos);
        listView.setAdapter(arrayAdapter);
        getDatos();

    }

    private void getDatos(){
        String url="https://travelbriefing.org/countries.json";
        // localhost String url="https://10.0.2.2:8080/posts"; //API EN RED LOCALHOST.
        //https://travelbriefing.org/api (URL pagina Api)
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //manejo Json
                pasarJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);

    }
    private void pasarJson(JSONArray array){
        for(int i=0;i< array.length();i++){
            Publicacion post= new Publicacion();
            JSONObject json=null;


            try {
                json=array.getJSONObject(i);
                post.setName(json.getString("name"));
                post.setUrl(json.getString("url"));
                datos.add(post.getName()+" "+ post.getUrl());//Informacion
            } catch (JSONException e) {
                e.printStackTrace();

            }

        }
        arrayAdapter.notifyDataSetChanged();

    }
}