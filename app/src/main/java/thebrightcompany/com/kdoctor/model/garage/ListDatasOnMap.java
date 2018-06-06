package thebrightcompany.com.kdoctor.model.garage;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ListDatasOnMap implements Serializable{
    @SerializedName("token")
    private String token;
    @SerializedName("data")
    private List<GarageOnMap> garageOnMaps;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<GarageOnMap> getGarageOnMaps() {
        return garageOnMaps;
    }

    public void setGarageOnMaps(List<GarageOnMap> garageOnMaps) {
        this.garageOnMaps = garageOnMaps;
    }
}
