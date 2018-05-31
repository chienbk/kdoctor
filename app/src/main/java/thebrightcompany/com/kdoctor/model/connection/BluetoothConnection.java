package thebrightcompany.com.kdoctor.model.connection;

public class BluetoothConnection {
    private String nameOfDevice;
    private String macAddress;
    private String expireDate;
    private String addressOfVin;
    private boolean isConnected;
    private boolean isExpired;

    public BluetoothConnection() {
    }

    public BluetoothConnection(String nameOfDevice, String macAddress, String expireDate, boolean isConnected, boolean isExpired) {
        this.nameOfDevice = nameOfDevice;
        this.macAddress = macAddress;
        this.expireDate = expireDate;
        this.isConnected = isConnected;
        this.isExpired = isExpired;
    }

    public BluetoothConnection(String nameOfDevice, String macAddress, String expireDate, String addressOfVin, boolean isConnected, boolean isExpired) {
        this.nameOfDevice = nameOfDevice;
        this.macAddress = macAddress;
        this.expireDate = expireDate;
        this.addressOfVin = addressOfVin;
        this.isConnected = isConnected;
        this.isExpired = isExpired;
    }

    public String getAddressOfVin() {
        return addressOfVin;
    }

    public void setAddressOfVin(String addressOfVin) {
        this.addressOfVin = addressOfVin;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public String getNameOfDevice() {
        return nameOfDevice;
    }

    public void setNameOfDevice(String nameOfDevice) {
        this.nameOfDevice = nameOfDevice;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
