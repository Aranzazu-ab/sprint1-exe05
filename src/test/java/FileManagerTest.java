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
    public void shouldThrowWhenIsNotATxtFile() throws IOException {
        File temporalFile = File.createTempFile("test", ".pdf");
        temporalFile.deleteOnExit();
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> FileManager.readTxtFile(temporalFile.getPath())
        );
        assertTrue(ex.getMessage().contains("isn't a .txt file"));
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

        List<File> treeTest = FileManager.readFilesRecursive(temporalDir);
        assertEquals(4, treeTest.size());
        assertEquals("atest", treeTest.get(0).getName());
        assertEquals("btest.txt", treeTest.get(1).getName());
        assertEquals("ztest.txt", treeTest.get(2).getName());
        assertEquals("test.txt", treeTest.get(3).getName());
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
    public void shoulReadTextFileContent() throws IOException{
        File temporalFile = File.createTempFile("readTest", ".txt");
        temporalFile.deleteOnExit();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(temporalFile))) {
            writer.write("This is a test.");
            writer.newLine();
            writer.write("To prove code.");
        }

        List<String> result = FileManager.readTxtFile(temporalFile.getPath());
        assertEquals(2, result.size());
        assertEquals("This is a test.", result.get(0));
        assertEquals("To prove code.", result.get(1));
    }
}

