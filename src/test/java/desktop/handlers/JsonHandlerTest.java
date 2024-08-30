package desktop.handlers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.TestUtils.discardSysOut;

public class JsonHandlerTest {
    @Test
    public void testSerialize() {
        var objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        var jsonHandler = new JsonHandler<>(objectMapper, DummyRecord.class);
        var dummyRecord = new DummyRecord("dummy", 1, true);
        var serialized = jsonHandler.serialize(dummyRecord);

        assertThat(serialized).isEqualToNormalizingNewlines("""
                {
                  "dummyString" : "dummy",
                  "dummyInt" : 1,
                  "dummyBoolean" : true
                }""");
    }

    @Test
    public void testDeserializeOk() {
        var objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        var jsonHandler = new JsonHandler<DummyRecord>(objectMapper, DummyRecord.class);
        var serialized = """
                {
                  "dummyString" : "dummy",
                  "dummyInt" : 1,
                  "dummyBoolean" : true
                }""";
        var dummyRecord = jsonHandler.deserialize(serialized);

        assertThat(dummyRecord).isEqualTo(new DummyRecord("dummy", 1, true));
    }

    @Test
    public void testDeserializeError() {
        discardSysOut();

        var objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        var jsonHandler = new JsonHandler<>(objectMapper, DummyRecord.class);
        var serialized = "dummy mistake";
        var dummyRecord = jsonHandler.deserialize(serialized);

        assertThat(dummyRecord).isNull();
    }

    record DummyRecord(
            @JsonProperty("dummyString") String dummy,
            @JsonProperty("dummyInt") int dummyInt,
            @JsonProperty("dummyBoolean") boolean dummyBoolean) {
    }
}
