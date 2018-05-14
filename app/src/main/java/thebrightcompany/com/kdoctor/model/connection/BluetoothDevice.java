package thebrightcompany.com.kdoctor.model.connection;

public class BluetoothDevice {
    private String nameOfDevice;
    private String macAddress;
    private String expireDate;
    private boolean isConnected;

    public BluetoothDevice(String nameOfDevice, String macAddress, String expireDate, boolean isConnected) {
        this.nameOfDevice = nameOfDevice;
        this.macAddress = macAddress;
        this.expireDate = expireDate;
        this.isConnected = isConnected;
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
