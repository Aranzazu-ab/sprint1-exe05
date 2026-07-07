import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class MainL2 {
    public static void main(String[] args) throws IOException {

        String configPath = args.length > 0 ? args[0] : "config.properties";

        Properties config = ConfigurationLoader.loadConfig(configPath);

        String targetDirectory = config.getProperty("directory");
        String outputFile = config.getProperty("outputFile");

        if (targetDirectory == null || outputFile == null) {
            System.out.println("Missing 'directory' or 'outputFile' in config file.");
            return;
        }

        FileManager fileManager = new FileManager(targetDirectory);

        File output = new File(outputFile);
        if (output.getParentFile() != null) {
            output.getParentFile().mkdirs();
        }

        FileManager.saveFilesRecursive(fileManager.getDirectory(), outputFile);

        System.out.println("Tree saved to " + outputFile);
    }
}
