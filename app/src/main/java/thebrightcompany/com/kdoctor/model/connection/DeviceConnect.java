package thebrightcompany.com.kdoctor.model.connection;

public class DeviceConnect {
    private boolean isConnected;
    private String msg;

    public DeviceConnect(boolean isConnected, String msg) {
        this.isConnected = isConnected;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
