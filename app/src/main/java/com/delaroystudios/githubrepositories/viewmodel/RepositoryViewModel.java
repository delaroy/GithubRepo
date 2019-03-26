package com.delaroystudios.githubrepositories.viewmodel;

import android.app.Application;
import android.util.Log;

import com.delaroystudios.githubrepositories.database.AppDatabase;
import com.delaroystudios.githubrepositories.database.RepoEntry;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RepositoryViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = RepositoryViewModel.class.getSimpleName();

    private LiveData<List<RepoEntry>> repositories;

    public RepositoryViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the repository from the DataBase");
        repositories = database.repositoriesDao().loadAllRepositories();
    }

    public LiveData<List<RepoEntry>> getRepositories() {
        return repositories;
    }
}
