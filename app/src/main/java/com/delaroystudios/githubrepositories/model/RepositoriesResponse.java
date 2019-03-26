
package com.delaroystudios.githubrepositories.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RepositoriesResponse implements Parcelable
{

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("incomplete_results")
    @Expose
    private Boolean incompleteResults;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    public final static Creator<RepositoriesResponse> CREATOR = new Creator<RepositoriesResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public RepositoriesResponse createFromParcel(Parcel in) {
            return new RepositoriesResponse(in);
        }

        public RepositoriesResponse[] newArray(int size) {
            return (new RepositoriesResponse[size]);
        }

    }
    ;

    protected RepositoriesResponse(Parcel in) {
        this.totalCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.incompleteResults = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.items, (com.delaroystudios.githubrepositories.model.Item.class.getClassLoader()));
    }

    public RepositoriesResponse() {
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(totalCount);
        dest.writeValue(incompleteResults);
        dest.writeList(items);
    }

    public int describeContents() {
        return  0;
    }

}
