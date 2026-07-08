import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationLoader {
    private final Properties properties;

    public ConfigurationLoader() throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream("configuration.properties")); //Cerrar FileInputStream
    }

    public String getPropertyDirectory() {
        return properties.getProperty("directory");
    }

    public String getOutputFile() {
        return properties.getProperty("outputFile");
    }
}
