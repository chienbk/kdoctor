package thebrightcompany.com.kdoctor.model.commentgarage;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SummaryStar implements Serializable{

    @SerializedName("total_of_comments")
    private int total_of_comments;
    @SerializedName("total_of_1_star_comments")
    private int total_of_1_star_comments;
    @SerializedName("total_of_2_star_comments")
    private int total_of_2_star_comments;
    @SerializedName("total_of_3_star_comments")
    private int total_of_3_star_comments;
    @SerializedName("total_of_4_star_comments")
    private int total_of_4_star_comments;
    @SerializedName("total_of_5_star_comments")
    private int total_of_5_star_comments;

    public int getTotal_of_comments() {
        return total_of_comments;
    }

    public void setTotal_of_comments(int total_of_comments) {
        this.total_of_comments = total_of_comments;
    }

    public int getTotal_of_1_star_comments() {
        return total_of_1_star_comments;
    }

    public void setTotal_of_1_star_comments(int total_of_1_star_comments) {
        this.total_of_1_star_comments = total_of_1_star_comments;
    }

    public int getTotal_of_2_star_comments() {
        return total_of_2_star_comments;
    }

    public void setTotal_of_2_star_comments(int total_of_2_star_comments) {
        this.total_of_2_star_comments = total_of_2_star_comments;
    }

    public int getTotal_of_3_star_comments() {
        return total_of_3_star_comments;
    }

    public void setTotal_of_3_star_comments(int total_of_3_star_comments) {
        this.total_of_3_star_comments = total_of_3_star_comments;
    }

    public int getTotal_of_4_star_comments() {
        return total_of_4_star_comments;
    }

    public void setTotal_of_4_star_comments(int total_of_4_star_comments) {
        this.total_of_4_star_comments = total_of_4_star_comments;
    }

    public int getTotal_of_5_star_comments() {
        return total_of_5_star_comments;
    }

    public void setTotal_of_5_star_comments(int total_of_5_star_comments) {
        this.total_of_5_star_comments = total_of_5_star_comments;
    }
}
