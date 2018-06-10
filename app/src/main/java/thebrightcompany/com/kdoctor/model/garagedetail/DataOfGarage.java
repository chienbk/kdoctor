package thebrightcompany.com.kdoctor.model.garagedetail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataOfGarage implements Serializable{
    @SerializedName("token")
    private String token;
    @SerializedName("garage_detail")
    private GarageDetail garageDetail;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public GarageDetail getGarageDetail() {
        return garageDetail;
    }

    public void setGarageDetail(GarageDetail garageDetail) {
        this.garageDetail = garageDetail;
    }
}
