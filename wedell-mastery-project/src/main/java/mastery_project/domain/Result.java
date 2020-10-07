package mastery_project.domain;

import java.util.ArrayList;

public class Result<T> {
    private T payload;
    private ArrayList<String> errorMessages = new ArrayList<>();

    public void addErrorMessage(String message) {
        errorMessages.add(message);
    }

    public boolean isSuccess() {
        return errorMessages.size() == 0;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }
}
