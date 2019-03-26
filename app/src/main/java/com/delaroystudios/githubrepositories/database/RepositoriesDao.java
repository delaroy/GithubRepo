package com.delaroystudios.githubrepositories.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RepositoriesDao {

    @Query("SELECT * FROM RepoEntry")
    LiveData<List<RepoEntry>> loadAllRepositories();

    @Insert
    void insertRepository(RepoEntry repoEntry);

    @Query("SELECT * FROM RepoEntry WHERE id = :id")
    LiveData<RepoEntry> loadRepositoryById(int id);

    @Query("DELETE FROM RepoEntry")
    void deleteAll();
}
