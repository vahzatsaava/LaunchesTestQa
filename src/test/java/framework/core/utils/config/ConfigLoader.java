package framework.core.utils.config;



import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class ConfigLoader {

    private static TestConfig config;

    static {
        try (InputStream input = ConfigLoader.class
                .getClassLoader()
                .getResourceAsStream("application.yaml")) {

            Yaml yaml = new Yaml();
            config = yaml.loadAs(input, TestConfig.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.yaml", e);
        }
    }

    public static TestConfig get() {
        return config;
    }
}
