package com.example.teukuatjeh.finalprojectmp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kosalgeek.android.json.JsonConverter;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String URL = "http://palvipln.com/belajar/";
    String TAG = "MainActivity";

//    Widget Val Init
    ListView lv;

//  Firebase Init
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Firebase Login Action
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser(); //Check if user is signed in (non-null) and update UI accordingly.

//        Widget Init
        lv = (ListView)findViewById(R.id.postList);

//        Http Request
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
//            If request fail
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
//            If success read JSON response
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Log.d(TAG,myResponse);

                        ArrayList<Post> posts =
                                new JsonConverter<Post>().toArrayList(myResponse, Post.class);

                        ArrayList<Integer> id = new ArrayList<Integer>();
                        ArrayList<String> titles = new ArrayList<String>();
                        ArrayList<String> descs = new ArrayList<String>();
                        ArrayList<String> images = new ArrayList<String>();
                        for(Post value: posts){
                            id.add(value.id);
                            titles.add(value.judul);
                            descs.add(value.deskripsi);
                            images.add(value.image);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                MainActivity.this,
                                android.R.layout.simple_list_item_1,
                                titles);

                        lv.setAdapter(adapter);

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent intent1  = new Intent(MainActivity.this,ProfileActivity.class);
                this.startActivity(intent1);
                return true;
            case R.id.logout:
                try{
                    mAuth.signOut();
                    Intent in = new Intent(MainActivity.this, LoginActivity.class);
                    this.startActivity(in);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
