package com.bytedance.practice5;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.bytedance.practice5.model.Message;
import com.bytedance.practice5.socket.SocketActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "chapter5";
    private FeedAdapter adapter = new FeedAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UploadActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_mine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Constants.STUDENT_ID);
            }
        });

        findViewById(R.id.btn_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(null);
            }
        });
        findViewById(R.id.btn_socket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SocketActivity.class);
                startActivity(intent);
            }
        });



    }

    private void getData(String studentId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlStr=null;
                if(studentId==null){
                    urlStr="https://api-sjtu-camp-2021.bytedance.com/homework/invoke/messages";
                }else{
                    urlStr=String.format("https://api-sjtu-camp-2021.bytedance.com/homework/invoke/messages?student_id=%s", studentId);
                }
                System.out.println(urlStr);
                List<Message> result=null;

                try{
                    URL url=new URL(urlStr);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setConnectTimeout(6000);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("token","U0pUVS1ieXRlZGFuY2UtYW5kcm9pZA==");
                    if(conn.getResponseCode()==200){
                        InputStream inputStream=conn.getInputStream();
                        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                        Gson gson=new Gson();
                        JsonObject jsonObject=gson.fromJson(reader,JsonObject.class);
                        boolean success=jsonObject.get("success").getAsBoolean();
                        if(!success){
                            Log.d(TAG,"return failed");
                        }
                        result=gson.fromJson(jsonObject.get("feeds").getAsJsonArray(),new TypeToken<List<Message>>(){}.getType());
                        reader.close();
                        inputStream.close();
                    }else{
                        Log.d(TAG,"getData response error");
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG,"network error");
                }
                if(result!=null && !result.isEmpty()){
                    List<Message> finalResult = result;
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setData(finalResult);
                        }
                    });
                }
            }
        }).start();

    }


}