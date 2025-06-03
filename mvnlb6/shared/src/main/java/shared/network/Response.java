package shared.network;

import java.io.Serializable;

public class Response implements Serializable {
    private final String message;
    private final boolean needsInput;
    public Response(String message, boolean needsInput) {
        this.message = message;
        this.needsInput = needsInput;
    }
    public String getMessage() {
        return message;
    }
    public boolean isNeedsInput() { return needsInput; }
}
