package thebrightcompany.com.kdoctor.model.login;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginData implements Serializable{

    @SerializedName("customer")
    private Customer customer;
    @SerializedName("token")
    private String token;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
