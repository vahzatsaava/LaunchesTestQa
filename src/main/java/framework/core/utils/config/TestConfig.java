package framework.core.utils.config;

import lombok.Data;

@Data
public class TestConfig {
    private Api api;
    private Base base;
    private Url url;

    @Data
    public static class Api {
        private String key;
    }

    @Data
    public static class Base {
        private String url;
        private String projectName;
    }

    @Data
    public static class Url {
        private String launches;
    }
}

