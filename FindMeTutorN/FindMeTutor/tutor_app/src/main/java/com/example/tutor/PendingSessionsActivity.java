package com.example.tutor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PendingSessionsActivity extends AppCompatActivity implements tutor_AsyncResponse{

    List<Pending> list = new ArrayList<Pending>() ;
    RecyclerView rv ;

    tutor_getPending connect2server ;
    tutor_getPending refresh2server ;
    String id ;
    SharedPreferences myprefs;
    SwipeRefreshLayout swipeRefreshLayout ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_sessions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefreshlayout4);

        rv = (RecyclerView) findViewById(R.id.rvPending) ;
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));



         myprefs = getSharedPreferences("user",MODE_PRIVATE ) ;
         id = myprefs.getString("tutor_id", null) ;

       // Toast.makeText(getApplicationContext(), "tutor: " + id , Toast.LENGTH_SHORT).show();

        connect2server = new tutor_getPending(this , id , list) ;
        connect2server.delegate = this;
        connect2server.execute();

        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright)
                ,getResources().getColor(android.R.color.holo_green_light)
                , getResources().getColor(android.R.color.holo_orange_light)
                , getResources().getColor(android.R.color.holo_red_light));





        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems() ;
                Toast.makeText(getApplicationContext(), "Refreshed", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void refreshItems() {
        list.clear();
        refresh2server = new tutor_getPending(this , id , list) ;
        refresh2server.delegate = this ;
        refresh2server.execute()  ;
    }

    public void onItemsLoadComplete()
    {
        swipeRefreshLayout.setRefreshing(false);

    }



    @Override
    public void processFinish(String output) {
       PendingAdapter adapter = new PendingAdapter(connect2server.getList(),this.getApplicationContext()) ;
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
        onItemsLoadComplete();
    }
}
