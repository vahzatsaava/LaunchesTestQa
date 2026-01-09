package framework.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import framework.core.model.enums.Mode;
import framework.core.model.enums.RetentionPolicy;
import framework.core.model.enums.Status;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LaunchDto {
    private Integer id;
    private String uuid;
    private String name; // 3-256 chars
    private String owner;
    private String description; // 0-2048 chars
    private Integer number;
    private String startTime;
    private String endTime;
    private String lastModified;
    private Status status;
    private Mode mode;
    private List<String> analysing;
    private Double approximateDuration;
    private Boolean hasRetries;
    private Boolean rerun;
    private Map<String, Object> metadata;
    private RetentionPolicy retentionPolicy;
}
