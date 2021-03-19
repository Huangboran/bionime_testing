package com.example.bionimetesting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bionimetesting.Adapter.MyAdapter;
import com.example.bionimetesting.RoomDataBase.DataBase;
import com.example.bionimetesting.RoomDataBase.MyData;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private MyAdapter adapter;
    private RecyclerView recyclerView;
    private List<MyData> data = new ArrayList<MyData>();
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        //下拉刷新
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.design_default_color_primary));
        swipeRefreshLayout.setOnRefreshListener(()->{
            new Thread(()->{
                data.clear();
                DataBase.getInstance(this).getDataUao().deleteDataAll();
                get_API_Data();
            }).start();

            swipeRefreshLayout.setRefreshing(false);

        });
        get_API_Data();
        init();
    }

    public void init(){
        new Thread(()->{
            Log.e("1","1");
            List<MyData> myData = DataBase.getInstance(this).getDataUao().displayAll();
            adapter = new MyAdapter(MainActivity.this,data);
            recyclerView.setAdapter(adapter);
        }).start();
    }

    public void get_API_Data(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://data.epa.gov.tw/api/v1/aqx_p_432?limit=1000&api_key=9be7b239-557b-4c10-9775-78cadfc555e9";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    for(int i = 0; i<jsonObject.getJSONArray("records").length();i++){
                        MyData myData = new MyData(jsonObject.getJSONArray("records").getJSONObject(i).getString("SiteName"),
                                jsonObject.getJSONArray("records").getJSONObject(i).getString("AQI"),
                                jsonObject.getJSONArray("records").getJSONObject(i).getString("Status"),
                                jsonObject.getJSONArray("records").getJSONObject(i).getString("O3_8hr"),
                                jsonObject.getJSONArray("records").getJSONObject(i).getString("PM2.5"),
                                jsonObject.getJSONArray("records").getJSONObject(i).getString("WindSpeed"),
                                jsonObject.getJSONArray("records").getJSONObject(i).getString("PublishTime"));
                        data.add(myData);
                        new Thread(()->{
                            DataBase.getInstance(MainActivity.this).getDataUao().insertData(myData);
                        }).start();

                        Log.e("Data",data.get(i).SiteName);
                    }
                    runOnUiThread(()->{
                        adapter.refreshView();
                    });
                }catch (Exception e){
                    Log.e("Exception",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("error",volleyError.getMessage().toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}