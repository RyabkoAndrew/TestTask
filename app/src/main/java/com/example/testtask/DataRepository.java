package com.example.testtask;

import android.app.Application;
import android.os.AsyncTask;

import com.example.testtask.data.FirstPlayListTable;
import com.example.testtask.data.PlayListDao;
import com.example.testtask.data.PlayListDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DataRepository {
    private PlayListDao mPlayListDao;
    private LiveData<List<FirstPlayListTable>> mAll;

    public DataRepository(Application application){
        PlayListDatabase db = PlayListDatabase.getDatabase(application);
        mPlayListDao = db.playListDao();
        mAll = mPlayListDao.getAllVideo();
    }

    public LiveData<List<FirstPlayListTable>> getAll(){
        return mAll;
    }

    public void insert (FirstPlayListTable firstPlayListTable) {
        new insertAsyncTask(mPlayListDao).execute(firstPlayListTable);
    }

    private static class insertAsyncTask extends AsyncTask<FirstPlayListTable, Void, Void> {

        private PlayListDao mAsyncTaskDao;

        insertAsyncTask(PlayListDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(FirstPlayListTable... firstPlayListTables) {
            mAsyncTaskDao.insert(firstPlayListTables[0]);
            return null;
        }
    }
}
