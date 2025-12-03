package framework.core.model.request;

import lombok.Data;

import java.util.List;

@Data
public class FinishLaunchRequest {
    private List<Attribute> attributes;
    private String endTime;
    private String status;
    private String description;

    @Data
    public static class Attribute {
        private String key;
        private String value;
        private Boolean system;
    }
}
