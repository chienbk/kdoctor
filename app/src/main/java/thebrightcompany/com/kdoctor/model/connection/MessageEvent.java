package thebrightcompany.com.kdoctor.model.connection;

/**
 * Created by ChienNv9 on 1/24/2018.
 */

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
