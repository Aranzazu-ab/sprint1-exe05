import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {
    @Test
    public void shouldThrowWhenDirectoryDoesNotExist() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> new FileManager("notadirectory"));
        assertTrue(ex.getMessage().contains("doesn't exist"));
    }

    @Test
    public void shouldThrowWhenDirectoryIsNotADirectory() throws IOException {
        File temporalFile = File.createTempFile("fortest", ".txt");
        temporalFile.deleteOnExit();
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new FileManager(temporalFile.getPath()));
        assertTrue(ex.getMessage().contains("isn't a directory"));
    }

    @Test
    public void shouldNotThrowWhenPathIsAValidDirectory() {
        assertDoesNotThrow(() -> new FileManager("src"));
    }





}
