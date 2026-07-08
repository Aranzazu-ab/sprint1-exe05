import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static List<File> readFilesRecursive (File directory) {//TODO he cambiado de void a list
        List<File> result = new ArrayList<>();
        File[] files = getSortedFiles(directory);
        for (File file : files) {
            result.add(file);
            if (file.isDirectory()) {
                result.addAll(readFilesRecursive(file));
            }
        }
        return result;
    }

    public static void printFilesRecursive (File directory){//TODO meter aqui sdk
        SimpleDateFormat sdf = new SimpleDateFormat(" dd/MM/yyyy HH:mm");
        List<File> files= readFilesRecursive(directory);
        for (File f: files){
            if(f.isDirectory()){
                System.out.println("Directory: "+f.getName()+" "+sdf.format(f.lastModified()));
            } else {
                System.out.println("File: " + f.getName()+ " " + sdf.format(f.lastModified()));
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

    public static List<String> readTxtFile (String nameFile) throws IOException{
        File file = new File(nameFile);
        validateTxt(file);
        List <String> lines = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) !=null){
                lines.add(line);
            }
        }
        return lines;
    }

    public static void printTxtFile(String nameFile) throws IOException {
        List<String> lines = readTxtFile(nameFile);
        for (String line : lines) {
            System.out.println(line);
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

