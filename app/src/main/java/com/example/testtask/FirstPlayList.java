package com.example.testtask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.testtask.adapters.MyAdapter;
import com.example.testtask.model.Example;
import com.example.testtask.model.JSONResponse;
import com.example.testtask.webservice.App;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstPlayList extends AppCompatActivity {

    private ArrayList<Example> mList;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_play_list);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(mList,this);
        mRecyclerView.setAdapter(mAdapter);
        httpRequest();
    }

    private void httpRequest(){
        Call<JSONResponse> call = App.getApi().getData();

        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.isSuccessful()){
                    JSONResponse jsonResponse = response.body();

                    mList.addAll(Arrays.asList(jsonResponse.getItems()));
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });
    }
}
