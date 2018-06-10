package thebrightcompany.com.kdoctor.model.commentgarage;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommentResponse implements Serializable{

    @SerializedName("status_code")
    private int status_code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataOfComment dataOfComment;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataOfComment getDataOfComment() {
        return dataOfComment;
    }

    public void setDataOfComment(DataOfComment dataOfComment) {
        this.dataOfComment = dataOfComment;
    }
}
