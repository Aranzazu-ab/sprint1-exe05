import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length < 1) {
            System.out.println("Usage: java Main <directory-path>");
            return;
        }

        String targetDirectory = args[0];

        FileManager fileManager = new FileManager(targetDirectory);
        System.out.println("=== Exercise 1: Sorted directory content ===");
        for (File file : fileManager.getSortedContent()) {
            System.out.println(file.getName());
        }

        System.out.println("\n=== Exercise 2: Recursive tree (console) ===");
        FileManager.readFilesRecursive(fileManager.getDirectory());

        String outputPath = "output" + File.separator + "tree.txt";
        new File("output").mkdirs();
        FileManager.saveFilesRecursive(fileManager.getDirectory(), outputPath);
        System.out.println("\n=== Exercise 3: Tree saved to " + outputPath + " ===");

        System.out.println("\n=== Exercise 4: Reading " + outputPath + " ===");
        FileManager.readTxtFile(outputPath);

        String albumPath = "output" + File.separator + "album.ser";
        Album album = new Album("Summer Trip", Arrays.asList("beach.jpg", "sunset.jpg", "friends.jpg"));

        FileManager.serializeObject(album, albumPath);
        System.out.println("\n=== Exercise 5: Serialized album ===");
        System.out.println(album);

        Album recoveredAlbum = (Album) FileManager.deserializeObject(albumPath);
        System.out.println("Deserialized album:");
        System.out.println(recoveredAlbum);
    }
}
