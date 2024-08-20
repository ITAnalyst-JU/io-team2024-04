package desktop.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHandler<T extends Record> {
    private final ObjectMapper mapper;
    private final Class<T> type;

    public JsonHandler(ObjectMapper mapper, Class<T> type) {
        this.mapper = mapper;
        this.type = type;
    }

    public String serialize(T record) {
        try {
            return mapper.writeValueAsString(record);
        } catch (Exception e) {
            System.err.println(
                    "[JsonHandler.serialize] Failed to serialize record!");
        }
        return null;
    }

    public T deserialize(String json) {
        try {
            return mapper.readValue(json, type);
        } catch (Exception e) {
            System.err.println(
                    "[JsonHandler.deserialize] Failed to deserialize record!");
        }
        return null;
    }

}
