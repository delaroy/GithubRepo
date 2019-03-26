package com.delaroystudios.githubrepositories;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.delaroystudios.githubrepositories.database.AppDatabase;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryDetail extends AppCompatActivity {

    public static String NAME = "name";
    public static String HTML_URL = "html_url";
    public static String DESCRIPTION = "description";
    public static String LANGUAGE = "language";
    public static String STARGAZERS_COUNT = "stargazers_count";

    @BindView(R.id.text_repo_name)
    TextView repoName;

    @BindView(R.id.text_repo_description)
    TextView repoDescription;

    @BindView(R.id.text_language)
    TextView language;

    @BindView(R.id.text_stars)
    TextView stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repository_detail);

        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra(NAME)) {
            String name = Objects.requireNonNull(intent.getExtras()).getString(NAME);
            String html_url = intent.getExtras().getString(HTML_URL);
            String description = intent.getExtras().getString(DESCRIPTION);
            String repo_language = intent.getExtras().getString(LANGUAGE);
            int stargazers = intent.getExtras().getInt(STARGAZERS_COUNT);

            setTitle(name);

            repoName.setText(name);
            repoDescription.setText(description);
            language.setText(repo_language);
            stars.setText("Stars: " + stargazers);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
