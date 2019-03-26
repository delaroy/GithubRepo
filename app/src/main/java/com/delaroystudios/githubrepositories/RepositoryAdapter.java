package com.delaroystudios.githubrepositories;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.delaroystudios.githubrepositories.database.RepoEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.delaroystudios.githubrepositories.RepositoryDetail.DESCRIPTION;
import static com.delaroystudios.githubrepositories.RepositoryDetail.HTML_URL;
import static com.delaroystudios.githubrepositories.RepositoryDetail.LANGUAGE;
import static com.delaroystudios.githubrepositories.RepositoryDetail.NAME;
import static com.delaroystudios.githubrepositories.RepositoryDetail.STARGAZERS_COUNT;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ArticleViewHolder> {

    // Member variable to handle item clicks
    private List<RepoEntry> mRepoEntries;
    private Context mContext;

    public RepositoryAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.repo_items, parent, false);

        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {

        RepoEntry repoEntry = mRepoEntries.get(position);
        String name = repoEntry.getName();
        String description = repoEntry.getDescription();
        String html_url = repoEntry.getHtmlUrl();
        String language = repoEntry.getLanguage();
        int stargazersCount = repoEntry.getStargazersCount();

        //Set values
        holder.repoName.setText(name);
        holder.repoDescription.setText(description);
        holder.language.setText(language);
        holder.stars.setText("Stars: " + stargazersCount + "");
    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mRepoEntries == null) {
            return 0;
        }
        return mRepoEntries.size();
    }

    public List<RepoEntry> getRepo() {
        return mRepoEntries;
    }


    public void setRepos(List<RepoEntry> repoEntries) {
        mRepoEntries = repoEntries;
        notifyDataSetChanged();
    }

    // Inner class for creating ViewHolders
    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView repoName, repoDescription, language, stars;

        public ArticleViewHolder(View itemView) {
            super(itemView);

            repoName = itemView.findViewById(R.id.text_repo_name);
            repoDescription = itemView.findViewById(R.id.text_repo_description);
            language = itemView.findViewById(R.id.text_language);
            stars = itemView.findViewById(R.id.text_stars);


            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    RepoEntry clickedDataItem = mRepoEntries.get(pos);
                    Intent intent = new Intent(mContext, RepositoryDetail.class);
                    intent.putExtra(NAME, clickedDataItem.getName());
                    intent.putExtra(HTML_URL, clickedDataItem.getHtmlUrl());
                    intent.putExtra(DESCRIPTION, clickedDataItem.getDescription());
                    intent.putExtra(LANGUAGE, clickedDataItem.getLanguage());
                    intent.putExtra(STARGAZERS_COUNT, clickedDataItem.getStargazersCount());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);

                }
            });
        }

    }
}

