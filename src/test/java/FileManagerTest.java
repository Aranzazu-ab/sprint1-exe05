import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

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

    @Test
    public void shouldReturnContentSortedAlphabetically() throws IOException {
        File temporalDir = Files.createTempDirectory("testDir").toFile();
        assertTrue(new File(temporalDir, "atest.txt").createNewFile());
        assertTrue(new File(temporalDir, "ctest.txt").createNewFile());
        assertTrue(new File(temporalDir, "btest.txt").createNewFile());

        FileManager fileTest = new FileManager(temporalDir.getPath());
        File[] sorted = fileTest.getSortedContent();

        assertEquals("atest.txt", sorted[0].getName());
        assertEquals("btest.txt", sorted[1].getName());
        assertEquals("ctest.txt", sorted[2].getName());
    }

    @Test
    public void shouldPrintTreeDirectorySortedAlphabetically() throws IOException{
        File temporalDir= Files.createTempDirectory("testDir").toFile();
        assertTrue(new File(temporalDir, "test.txt").createNewFile());
        File subDir = new File(temporalDir, "atest");
        assertTrue(subDir.mkdir());
        assertTrue(new File(subDir, "ztest.txt").createNewFile());
        assertTrue(new File(subDir, "btest.txt").createNewFile());

        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream(); //crearbuffer para guardar
        PrintStream originalOut = System.out;//guardar el print original
        System.setOut(new PrintStream(outputBuffer));//sistemout guardara en buffer
        FileManager.readFilesRecursive(temporalDir);
        System.setOut(originalOut);//regreso a imprimir el pantalla no en buffer
        String output = outputBuffer.toString();

        assertTrue(output.contains("Directory: atest"));
        assertTrue(output.contains("File: btest.txt"));
        assertTrue(output.contains("File: ztest.txt"));
        assertTrue(output.contains("File: test.txt"));

        int positionAtest = output.indexOf("atest");
        int positionBtest = output.indexOf("btest.txt");
        int positionZtest = output.indexOf("ztest.txt");
        int positionTest = output.indexOf("test.txt");

        assertTrue(positionAtest < positionBtest);
        assertTrue(positionBtest < positionZtest);
        assertTrue(positionZtest < positionTest);
    }

    @Test
    public void shouldSaveDirectoryTreeToTextFile() throws IOException {
        File temporalDir= Files.createTempDirectory("testDir").toFile();
        assertTrue(new File(temporalDir, "test.txt").createNewFile());
        File subDir = new File(temporalDir, "atest");
        assertTrue(subDir.mkdir());
        assertTrue(new File(subDir, "ztest.txt").createNewFile());
        assertTrue(new File(subDir, "btest.txt").createNewFile());

        File outputFile = File.createTempFile("output", ".txt");
        outputFile.deleteOnExit();

        FileManager.saveFilesRecursive(temporalDir, outputFile.getPath());
        List<String> lines = Files.readAllLines(outputFile.toPath());
        String content = String.join(System.lineSeparator(), lines);

        assertTrue(content.contains("Directory: atest"));
        assertTrue(content.contains("File: btest.txt"));
        assertTrue(content.contains("File: ztest.txt"));
        assertTrue(content.contains("File: test.txt"));

        int posAtest = content.indexOf("Directory: atest");
        int posBtest = content.indexOf("File: btest.txt");
        int posZtest = content.indexOf("File: ztest.txt");
        int posTest = content.indexOf("File: test.txt");

        assertTrue(posAtest < posBtest);
        assertTrue(posBtest < posZtest);
        assertTrue(posZtest < posTest);
    }

    @Test
    public void shouldPrintTextFileContent() throws IOException{
        File temporalFile = File.createTempFile("readTest", ".txt");
        temporalFile.deleteOnExit();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(temporalFile))) {
            writer.write("This is a test.");
            writer.newLine();
            writer.write("To prove code.");
        }

        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputBuffer));

        FileManager.readTxtFile(temporalFile.getPath());
        System.setOut(originalOut);

        String output = outputBuffer.toString();
        assertTrue(output.contains("This is a test."));
        assertTrue(output.contains("To prove code."));
    }


}

