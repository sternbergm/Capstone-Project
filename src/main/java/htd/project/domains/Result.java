package htd.project.domains;

import java.util.ArrayList;
import java.util.List;

public class Result<Payload> {

    private final List<String> messages;
    private Payload payload;

    public Result() {
        messages = new ArrayList<>();
    }

    public void addMessage(String message) {
        messages.add(message);
    }
	public List<String> getMessages() {
        return messages;

    }
	public boolean isSuccessful() {
        return messages.size() == 0;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}
