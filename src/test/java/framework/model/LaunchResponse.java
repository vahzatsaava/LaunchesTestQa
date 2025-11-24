package framework.model;

import lombok.Data;

import java.util.List;

@Data
public class LaunchResponse {
    private List<LaunchDto> content;
    private Page page;

    @Data
    public static class Page {
        private int number;
        private int size;
        private int totalElements;
        private int totalPages;
    }
}

