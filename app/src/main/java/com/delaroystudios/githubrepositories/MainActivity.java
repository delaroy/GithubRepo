package com.delaroystudios.githubrepositories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.delaroystudios.githubrepositories.database.AppDatabase;
import com.delaroystudios.githubrepositories.database.RepoEntry;
import com.delaroystudios.githubrepositories.model.Item;
import com.delaroystudios.githubrepositories.model.RepositoriesResponse;
import com.delaroystudios.githubrepositories.networking.api.Service;
import com.delaroystudios.githubrepositories.networking.generator.DataGenerator;
import com.delaroystudios.githubrepositories.viewmodel.RepositoryViewModel;

import java.util.List;

import static com.delaroystudios.githubrepositories.utils.Constants.BASE_URL;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.repo_list)
    RecyclerView repoList;

    @BindView(R.id.search_text)
    AppCompatEditText searchText;

    @BindView(R.id.button_search)
    AppCompatButton buttonSearch;

    @BindView(R.id.gallery_progress)
    ProgressBar galleryProgress;

    @BindView(R.id.repo_empty)
    TextView repoEmpty;

    RepositoryAdapter adapter;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        repoList.setHasFixedSize(true);
        repoList.setLayoutManager(new LinearLayoutManager(this));
        repoList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        adapter = new RepositoryAdapter(this);
        repoList.setAdapter(adapter);

        galleryProgress.setVisibility(View.GONE);
        repoEmpty.setVisibility(View.GONE);
        mDb = AppDatabase.getInstance(getApplicationContext());

        RepositoryViewModel viewModel = ViewModelProviders.of(this).get(RepositoryViewModel.class);
        viewModel.getRepositories().observe(this, repositoryEntries -> {
            if (repositoryEntries != null) {
                adapter.setRepos(repositoryEntries);
                repoEmpty.setVisibility(View.GONE);
            } else if (repositoryEntries == null){
                repoEmpty.setVisibility(View.VISIBLE);
            } else if (repositoryEntries.isEmpty()) {
                repoEmpty.setVisibility(View.VISIBLE);
            }

        });

        buttonSearch.setOnClickListener(v -> {
            final String search_text = searchText.getText().toString();
            if (!TextUtils.isEmpty(search_text)) {
                getRepositories(search_text);
            } else {
                Toast.makeText(this, "please type a search word", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getRepositories (String search_text) {

        galleryProgress.setVisibility(View.VISIBLE);

        try {

            Service service = DataGenerator.createService(Service.class, "delaroy", BASE_URL);
            Call<RepositoriesResponse> call = service.fetchRepo(search_text);

            call.enqueue(new Callback<RepositoriesResponse>() {
                @Override
                public void onResponse(Call<RepositoriesResponse> call, Response<RepositoriesResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            List<Item> itemList = response.body().getItems();

                            AppExecutors.getInstance().diskIO().execute(() ->mDb.repositoriesDao().deleteAll());

                            for (Item item : itemList) {
                                String name = item.getName();
                                String html_url = item.getHtmlUrl();
                                String description = item.getDescription();
                                String language = item.getLanguage();
                                int stargazers_count = item.getStargazersCount();

                                RepoEntry repositoriesEntry = new RepoEntry(name, html_url, description, language, stargazers_count);
                                AppExecutors.getInstance().diskIO().execute(() ->mDb.repositoriesDao().insertRepository(repositoriesEntry));
                            }

                            galleryProgress.setVisibility(View.GONE);
                        }
                    } else {
                        galleryProgress.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Error fetching content", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RepositoriesResponse> call, Throwable t) {
                    galleryProgress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Error fetching content", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            galleryProgress.setVisibility(View.GONE);
            Toast.makeText(this, "Error fetching content", Toast.LENGTH_SHORT).show();
        }
    }

}
