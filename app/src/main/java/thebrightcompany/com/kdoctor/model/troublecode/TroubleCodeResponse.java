
package thebrightcompany.com.kdoctor.model.troublecode;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TroubleCodeResponse implements Serializable {
    @SerializedName("status_code")
    private int status_code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataOfTroubleCode dataOfTroubleCode;

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

    public DataOfTroubleCode getDataOfTroubleCode() {
        return dataOfTroubleCode;
    }

    public void setDataOfTroubleCode(DataOfTroubleCode dataOfTroubleCode) {
        this.dataOfTroubleCode = dataOfTroubleCode;
    }
}
