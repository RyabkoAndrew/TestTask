package com.example.testtask.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testtask.R;
import com.example.testtask.RecyclerViewPosition;
import com.example.testtask.adapters.MyAdapter;
import com.example.testtask.adapters.RoomAdapter;
import com.example.testtask.data.FirstPlayListTable;
import com.example.testtask.model.Example;
import com.example.testtask.model.JSONResponse;
import com.example.testtask.viewmodel.ListViewModel;
import com.example.testtask.webservice.App;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstPlayListActivity extends AppCompatActivity implements RecyclerViewPosition {

    private ArrayList<Example> mList;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ListViewModel viewModel;
    private RoomAdapter roomAdapter;
    ImageLoader imageLoader;

    FirstPlayListTable firstPlayListTable;
    //Bitmap bit;


    String title;
    String description;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_play_list);

        setTitle(R.string.hillsong_play_list);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mList = new ArrayList<>();

        roomAdapter = new RoomAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(mList, this, this::recyclerViewPosition);
        mRecyclerView.setAdapter(mAdapter);
        httpRequest();

        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        viewModel.getGetAllVideo().observe(this, new Observer<List<FirstPlayListTable>>() {
            @Override
            public void onChanged(List<FirstPlayListTable> firstPlayListTables) {
                if (isOnline() == false) {
                    mRecyclerView.setAdapter(roomAdapter);
                    roomAdapter.setWords(firstPlayListTables);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.play_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.refresh:
                if (isOnline() == false) {
                    viewModel.getGetAllVideo().observe(this, new Observer<List<FirstPlayListTable>>() {
                        @Override
                        public void onChanged(List<FirstPlayListTable> firstPlayListTables) {
                            mRecyclerView.setAdapter(roomAdapter);
                            roomAdapter.setWords(firstPlayListTables);
                        }
                    });
                }else {
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setWords(mList);
                }
                break;
                default:
                    return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void httpRequest() {
        Call<JSONResponse> call = App.getApi().getData();

        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.isSuccessful()) {
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

    public boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void recyclerViewPosition(Example data) {
        title = data.getSnippet().getTitle();
        description = data.getSnippet().getDescription();
        imageUrl = data.getSnippet().getThumbnails().getMedium().getUrl();
        firstPlayListTable = new FirstPlayListTable(title, description, imageUrl);
        viewModel.insert(firstPlayListTable);
    }


}
