package desktop.handlers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static utils.TestUtils.discardSysOut;

public class JsonHandlerTest {
    @Test
    public void testConstructor() {
        var objectMapper = mock(ObjectMapper.class);
        var jsonHandler = new JsonHandler<>(objectMapper, DummyRecord.class);

        assertThat(jsonHandler).isNotNull();
    }

    @Test
    public void testSerializeOk() {
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
    public void testSerializeError() throws JsonProcessingException {
        discardSysOut();

        var objectMapper = mock(ObjectMapper.class);
        when(objectMapper.writeValueAsString(null)).thenThrow(new RuntimeException());
        var jsonHandler = new JsonHandler<>(objectMapper, DummyRecord.class);
        var serialized = jsonHandler.serialize(null);
        assertThat(serialized).isNull();
    }

    @Test
    public void testDeserializeOk() {
        var objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        var jsonHandler = new JsonHandler<>(objectMapper, DummyRecord.class);
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
    public void testDeserializeError() throws JsonProcessingException {
        discardSysOut();

        var objectMapper = mock(ObjectMapper.class);
        var serialized = "dummy mistake";
        when(objectMapper.readValue(serialized, DummyRecord.class)).thenThrow(new RuntimeException());
        var jsonHandler = new JsonHandler<>(objectMapper, DummyRecord.class);
        var dummyRecord = jsonHandler.deserialize(serialized);

        assertThat(dummyRecord).isNull();
    }

    record DummyRecord(
            @JsonProperty("dummyString") String dummy,
            @JsonProperty("dummyInt") int dummyInt,
            @JsonProperty("dummyBoolean") boolean dummyBoolean) {
    }
}
