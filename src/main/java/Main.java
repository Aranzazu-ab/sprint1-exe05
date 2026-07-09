import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FileManager manager = new FileManager("src/main");
        System.out.println("=Directory content sorted =");
        File[] content = manager.getSortedContent();
        for (File file : content) {
            System.out.println(file.getName());
        }
        System.out.println("\n= Recursive directory tree =");
        FileManager.printFilesRecursive(manager.getDirectory());
        System.out.println("\n= Save directory tree to TXT =");
        FileManager.saveFilesRecursive(
                manager.getDirectory(),
                "directoryTree.txt"
        );

        System.out.println("Tree was saved");

        System.out.println("\n= Reading TXT file =");
        FileManager.printTxtFile("directoryTree.txt");

        System.out.println("\n= Serialization =");
        List<String> photos = new ArrayList<>();
        photos.add("photo1.jpg");
        photos.add("photo2.jpg");
        photos.add("photo3.jpg");

        Album album = new Album(
                "My holidays",
                photos
        );

        FileManager.serializeObject(
                album,
                "album.ser"
        );

        Album restoredAlbum = (Album) FileManager.deserializeObject(
                "album.ser"
        );
        System.out.println(restoredAlbum);
    }
}
