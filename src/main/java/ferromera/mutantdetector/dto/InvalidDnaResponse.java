package ferromera.mutantdetector.dto;

/**
 * Created by ferromera on 5/30/2018.
 */
public class InvalidDnaResponse {
    private String message;

    public InvalidDnaResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
