package com.example.enviarnombre;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import devazt.devazt.networking.HttpClient;
import devazt.devazt.networking.OnHttpRequestComplete;
import devazt.devazt.networking.Response;

public class MainActivity extends AppCompatActivity implements OnHttpRequestComplete {

    LinearLayout stackContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stackContent = (LinearLayout) findViewById(R.id.StackContent);


        HttpClient client = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if(status.isSuccess()){
                    Gson gson = new GsonBuilder().create();
                    try{
                        JSONObject jsono = new JSONObject(status.getResult());
                        JSONArray jsonArray = jsono.getJSONArray("records");
                        ArrayList<Person> personas = new ArrayList<Person>();
                        for(int i=0;i<jsonArray.length();i++){
                            String person = jsonArray.getString(i);
                            System.out.println(person);
                            Person p =gson.fromJson(person,Person.class);
                            personas.add(p);
                            System.err.println(p.getName());
                            TextView t = new TextView(getBaseContext());
                            t.setText(p.getName());
                            stackContent.addView(t);
                        }

                    }
                    catch(Exception e){
                        System.out.println("Fallo!");
                        e.printStackTrace();
                    }

                    Toast.makeText(MainActivity.this, status.getResult(),Toast.LENGTH_LONG).show();
                }
            }
        });

        client.excecute("http://www.w3schools.com/angular/customers.php");
    }

    @Override
    public void onComplete(Response status) {

    }
}
