package desktop.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ConfigRecord(
        @JsonProperty("foregroundFPS") int foregroundFPS,
        @JsonProperty("title") String title,
        @JsonProperty("windowWidth") int windowWidth,
        @JsonProperty("windowHeight") int windowHeight,
        @JsonProperty("vsync") boolean vsync,
        @JsonProperty("icon") String icon) {
}