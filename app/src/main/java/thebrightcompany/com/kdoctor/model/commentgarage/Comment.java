package thebrightcompany.com.kdoctor.model.commentgarage;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Comment implements Serializable{

    @SerializedName("id")
    private int id;
    @SerializedName("customer_id")
    private int customer_id;
    @SerializedName("customer_name")
    private String customer_name;
    @SerializedName("customer_avatar")
    private String customer_avatar;
    @SerializedName("rating")
    private float rating;
    @SerializedName("comment")
    private String comment;
    @SerializedName("time")
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_avatar() {
        return customer_avatar;
    }

    public void setCustomer_avatar(String customer_avatar) {
        this.customer_avatar = customer_avatar;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
