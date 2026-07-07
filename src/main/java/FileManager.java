import java.io.*;
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

    public FileManager(String dir){
        this.directory= new File(dir);
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

    private static File[] getFiles(File directory) {
        File[] files = directory.listFiles();
        if (files==null){
            throw new IllegalArgumentException("Can´t access directory");
        }
        return files;
    }

    private static File [] getSortedFiles(File directory){
        File[] files = getFiles(directory);
        Arrays.sort(files);
        return files;
    }

    public File[] getSortedContent(){
        return getSortedFiles(directory);
    }

    public static void readFilesRecursive (File directory) {
        File[] files = getSortedFiles(directory);
        SimpleDateFormat sdf = new SimpleDateFormat(" dd/MM/yyyy HH:mm");
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName() + sdf.format(file.lastModified()));
                readFilesRecursive(file);
            } else {
                System.out.println("File: " + file.getName() + sdf.format(file.lastModified()));
            }
        }
    }

    public static void saveFilesRecursive (File directory, String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writeFilesRecursive(directory, writer, 0);
        }
    }

    private static void writeFilesRecursive (File directory, BufferedWriter writer, int level) throws IOException{
        File[] files = getSortedFiles(directory);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String indent= "   ".repeat(level);

        for (File file : files) {
            if (file.isDirectory()) {
                writer.write(indent + "Directory: " + file.getName() + " " + sdf.format(file.lastModified()));
                writer.newLine();
                writeFilesRecursive(file, writer,level + 1);
            } else {
                writer.write(indent + "File: " + file.getName() +" "+ sdf.format(file.lastModified()));
                writer.newLine();
            }
        }
    }

    public static void readTxtFile (String nameFile) throws IOException{
        File file = new File(nameFile);
        validateTxt(file);
        try(BufferedReader reader = new BufferedReader(new FileReader(nameFile))){
            String line;
            while ((line = reader.readLine()) !=null){
                System.out.println(line);
            }
        }
    }

    private static void validateTxt(File fileTxt) {
        if (!fileTxt.exists()) {
            throw new IllegalArgumentException("File doesn't exist");
        }
        if (!fileTxt.isFile()) {
            throw new IllegalArgumentException("Path isn't a file");
        }
        if (!fileTxt.getName().toLowerCase().endsWith(".txt")) {
            throw new IllegalArgumentException("File isn't a .txt file");
        }
    }

    public static void serializeObject(Object obj, String fileDirectory) throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileDirectory))) {
            output.writeObject(obj);
        }
    }

    public static Object deserializeObject(String fileDirectory) throws IOException, ClassNotFoundException {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileDirectory))) {
            return input.readObject();
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

//    public void showSortedContent(){
//        for (File f : getSortedContent()){
//            System.out.println(f.getName());
//        }
//    }



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
//    }}



