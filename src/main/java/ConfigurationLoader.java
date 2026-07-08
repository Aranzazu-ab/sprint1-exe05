import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationLoader {
    private final Properties properties;

    public ConfigurationLoader() throws IOException {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream("configuration.properties")) {
            properties.load(input);
        }
    }

    public String getPropertyDirectory() {
        return properties.getProperty("directory");
    }

    public String getOutputFile() {
        return properties.getProperty("outputFile");
    }
}
