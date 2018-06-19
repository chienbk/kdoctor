package thebrightcompany.com.kdoctor.model.troublecode;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DataOfTroubleCode implements Serializable {
    @SerializedName("token")
    private String token;
    @SerializedName("codes")
    private List<TroubleCode> troubleCodes;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<TroubleCode> getTroubleCodes() {
        return troubleCodes;
    }

    public void setTroubleCodes(List<TroubleCode> troubleCodes) {
        this.troubleCodes = troubleCodes;
    }
}
