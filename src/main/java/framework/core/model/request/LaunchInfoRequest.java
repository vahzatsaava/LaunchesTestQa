package framework.core.model.request;

import lombok.Data;

import java.util.List;

@Data
public class LaunchInfoRequest {

    private List<Integer> ids;
    private Description description;
    private List<AttributeChange> attributes;

    @ Data
    public static class Description {
        private String comment;
        private String action;
    }

    @Data
    public static class AttributeChange {
        private KeyValue from;
        private KeyValue to;
        private String action;
    }

    @Data
    public static class KeyValue {
        private String key;
        private String value;
    }
}

