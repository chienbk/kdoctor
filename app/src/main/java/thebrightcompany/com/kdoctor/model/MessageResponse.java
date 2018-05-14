package thebrightcompany.com.kdoctor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ChienNV on 10/26/16.
 */

public class MessageResponse {

    @SerializedName("messages")
    private String message;
    @SerializedName("status_code")
    private int status_code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }
}
