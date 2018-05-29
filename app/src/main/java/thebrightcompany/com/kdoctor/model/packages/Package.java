package thebrightcompany.com.kdoctor.model.packages;

import java.io.Serializable;

public class Package implements Serializable{

    private int id;
    private String nameOfPackage;
    private String price;
    private String detail;

    public Package(int id, String nameOfPackage, String price, String detail) {
        this.id = id;
        this.nameOfPackage = nameOfPackage;
        this.price = price;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfPackage() {
        return nameOfPackage;
    }

    public void setNameOfPackage(String nameOfPackage) {
        this.nameOfPackage = nameOfPackage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
