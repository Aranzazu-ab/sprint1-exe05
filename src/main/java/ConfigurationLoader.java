import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationLoader {
    public static Properties loadConfig(String configPath) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(configPath)) {
            properties.load(input);
        }
        return properties;
    }
}
//Responsabilidad única — ConfigLoader solo sabe hacer una cosa: cargar un archivo de configuración y devolver los valores. No sabe nada de directorios, archivos TXT, ni serialización.
//        Reutilizable — si en el Nivell 3 (u otro ejercicio futuro) necesitas cargar configuración otra vez, ya tienes la clase lista, sin tocar FileManager.
//Fácil de testear de forma aislada — puedes escribir un test que solo verifique "¿esta clase lee bien un .properties?", sin mezclar con la lógica de archivos/directorios.
//No ensucias Main — Main simplemente le pide a ConfigLoader los valores, y usa FileManager para la lógica de negocio real. Cada clase hace lo suyo.