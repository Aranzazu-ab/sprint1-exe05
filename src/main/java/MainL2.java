import java.io.IOException;

public class MainL2 {
    public static void main(String[] args) throws IOException {
            ConfigurationLoader config = new ConfigurationLoader();
            FileManager manager = new FileManager(config.getPropertyDirectory());
            FileManager.saveFilesRecursive(
                    manager.getDirectory(),
                    config.getOutputFile()
            );
            System.out.println("File load was satisfactory.");
    }
}
