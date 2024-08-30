package desktop.config;

import com.fasterxml.jackson.annotation.JsonProperty;

// NOTE: only for developers
public record ConfigRecord(
        @JsonProperty("foregroundFPS") int foregroundFPS,
        @JsonProperty("title") String title,
        @JsonProperty("windowWidth") int windowWidth,
        @JsonProperty("windowHeight") int windowHeight,
        @JsonProperty("vsync") boolean vsync,
        @JsonProperty("icon") String icon) {
}