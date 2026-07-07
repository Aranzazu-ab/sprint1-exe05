import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileManager {
    private final File directory;

    public FileManager(String path){
        this.directory= new File(path);
        validateDirectory();
    }

    public File getDirectory() {
        return directory;
    }

    private void validateDirectory(){
        if (!directory.exists()) {
            throw new IllegalArgumentException("Directory doesn't exists ");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Directory isn't a directory ");
        }
    }

    public File [] getSortedContent(){
        File[] files = getFiles(directory);
        Arrays.sort(files);
        return files;
    }

    private static File[] getFiles(File directory) {
        File[] files = directory.listFiles();
        if (files==null){
            throw new IllegalArgumentException("Can´t access directory");
        }
        return files;
    }

    public static void readFilesRecursive (File directory) {
        File[] files = getFiles(directory);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName() + sdf.format(file.lastModified()));
                readFilesRecursive(file);
            } else {
                System.out.println("File: " + file.getName() + sdf.format(file.lastModified()));
            }
        }
    }

    public static void saveFilesRecursive (File directory, String newFile) {
        File[] files = getFiles(directory);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName() + sdf.format(file.lastModified()));
                readFilesRecursive(file);
            } else {
                System.out.println("File: " + file.getName() + sdf.format(file.lastModified()));
            }
        }
    }
}



//    private void showDirectoryTree(File directory, int depth) {
//        File[] files = getFiles(directory);
//        Arrays.sort(files, Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER));
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//
//        for (File f : files) {
//            String type = f.isDirectory() ? "D" : "F";
//            String lastModified = sdf.format(new Date(f.lastModified()));
//            String indent = "  ".repeat(depth);
//
//            System.out.println(indent + "[" + type + "] " + f.getName() + " - " + lastModified);
//
//            if (f.isDirectory()) {
//                showDirectoryTree(f, depth + 1);
//            }
//        }
//    }

    public void showSortedContent(){
        for (File f : getSortedContent()){
            System.out.println(f.getName());
        }
    }



//    public List<Path> getSortedContent() throws IOException {
//        try (Stream<Path> stream = Files.list(directory)) {
//            return stream
//                    .sorted(Comparator.comparing(
//                            path -> path.getFileName().toString().toLowerCase()
//                    ))
//                    .collect(Collectors.toList());
//        }
//    }

//    public void showSortedContent() throws IOException {
//        for (Path path : getSortedContent()) {
//            System.out.println(path.getFileName());
//        }
//    }






//    private void useDirectory(){
//        File[] filesdirectory = directory.listFiles();
//        if (filesdirectory==null){
//            throw new IllegalArgumentException("Can´t have access to directory");
//        }
//    }
}



