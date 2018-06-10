package thebrightcompany.com.kdoctor.model.commentgarage;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DataOfComment implements Serializable{

    @SerializedName("token")
    private String token;
    @SerializedName("summary")
    private SummaryStar summaryStar;
    @SerializedName("comments")
    private List<Comment> comments;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SummaryStar getSummaryStar() {
        return summaryStar;
    }

    public void setSummaryStar(SummaryStar summaryStar) {
        this.summaryStar = summaryStar;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
