package thebrightcompany.com.kdoctor.model.register;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterResponse implements Serializable{
    @SerializedName("status_code")
    private int status_code;
    @SerializedName("message")
    private String message;

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
}
