# LEVEL 1 FILE MANAGER
This Java application manages files and directories. The goal is to create a class 
that can list directory contents, read text files, save directory information into TXT 
files, and serialize Java objects.

## STRUCTURE
FileManagerProject/
├── FileManager.java  
├── FileManagerTest.java  
├── Album.java  
└── Main.java

 **FileManager**
* getSortedContent()
* readFilesRecursive()
* printFilesRecursive()
* saveFilesRecursive()
* readTxtFile()
* printTxtFile()
* serializeObject()
* deserializeObject()

**Album**
* Serializable Java object used to test object serialization.
* Stores an album name and a list of photo names.

**FileManagerTest**
* shouldThrowWhenDirectoryDoesNotExist()
* shouldThrowWhenDirectoryIsNotADirectory()
* shouldThrowWhenIsNotATxtFile()
* shouldReturnContentSortedAlphabetically()
* shouldPrintTreeDirectorySortedAlphabetically()
* shouldSaveDirectoryTreeToTextFile()
* shouldReadTextFileContent()

## BEHAVIOUR
The application starts by receiving a directory path and validating that it exists and is a valid directory.
The first exercise allows the user to list the content of a directory in alphabetical order. The second exercise 
adds recursive directory reading. The application can explore subdirectories and return all files and folders 
sorted alphabetically. It also shows if the element is a directory or a file, including its last modification 
date.
The third exercise saves the directory tree information into a TXT file instead of only showing it on the screen.
The fourth exercise adds the possibility to read any TXT file and return its content. The content can also be printed 
using a separate method. The fifth exercise implements object serialization. A Java object can be converted into a `.ser` file and later restored using deserialization.

During development, unit tests were created to check the main behaviours:
- directory validation
- alphabetical ordering
- recursive reading
- TXT file reading
- TXT file validation

---

# BEHAVIOUR

The application starts by receiving a directory path and validating that it exists and is a valid directory.

The first exercise lists the content of a directory in alphabetical order.

The second exercise reads the directory tree recursively. It shows all folders and files inside each level, including the last modification date.

The third exercise saves the directory tree information into a TXT file.

The fourth exercise allows reading any TXT file and displaying its content in the console.

The fifth exercise serializes a Java object into a `.ser` file and then restores the object using deserialization.

Example of program execution:

```text
=Directory content sorted =
java
resources

= Recursive directory tree =
Directory: java 08/07/2026 23:29
File: Album.java 08/07/2026 09:43
File: ConfigurationLoader.java 08/07/2026 12:40
File: FileManager.java 08/07/2026 22:59
File: Main.java 08/07/2026 23:29
File: MainL2.java 08/07/2026 23:11
Directory: resources 01/07/2026 10:28

= Save directory tree to TXT =
Tree was saved

= Reading TXT file =
Directory: java 08/07/2026 23:29
   File: Album.java 08/07/2026 09:43
   File: ConfigurationLoader.java 08/07/2026 12:40
   File: FileManager.java 08/07/2026 22:59
   File: Main.java 08/07/2026 23:29
   File: MainL2.java 08/07/2026 23:11
Directory: resources 01/07/2026 10:28

= Serialization =
Album: My holidays, photos: [photo1.jpg, photo2.jpg, photo3.jpg]
```

The application separates the methods that read information from the methods that print or save information. This makes the code easier to test and maintain.

The unit tests verify the main behaviours:
- directory validation
- alphabetical ordering
- recursive directory reading
- TXT file reading
- TXT file validation
- file tree saving

## CONCLUSIONS

This exercise helped me practice working with files and directories in Java.

I learned how to use `File`, `BufferedReader`, `BufferedWriter`, and Java serialization classes.

Separating reading methods from printing methods made the code easier to test because the methods return data instead of only printing results.

JUnit tests helped verify each functionality independently and detect possible errors during development.

# LEVEL 2 CONFIGURATION FILE

## EXERCISE 1 PARAMETERIZE APPLICATION CONFIGURATION

This Java application modifies the previous file management exercise by moving the configuration from the code into an external `.properties` file.

The goal is to separate the application configuration from the program logic, making the application easier to modify and reuse in different environments.

The application uses a Java Properties file to configure:
- The directory that must be read.
- The name and location of the output TXT file.

---

# STRUCTURE

L2Exe1/

├── ConfigurationLoader.java  
├── MainL2.java  
├── FileManager.java  
└── configuration.properties


## ConfigurationLoader.java

- Loads configuration values from the properties file.
- Provides access to configured values through getter methods.

Methods:

- ConfigurationLoader()
- getPropertyDirectory()
- getOutputFile()


## MainL2.java

- Starts the application.
- Loads the configuration.
- Creates the FileManager.
- Saves the directory tree into the configured TXT file.


## FileManager.java

- Reuses the previous exercise functionality.
- Reads the directory recursively.
- Saves the directory tree into a TXT file.


## configuration.properties

Stores external application configuration:

```
directory=src/main/java
outputFile=output/tree.txt
```

---

# BEHAVIOUR

The application no longer needs fixed paths inside the Java code.

Instead of writing the directory and output file manually:

```java
new FileManager("src/main/java");

FileManager.saveFilesRecursive(
        directory,
        "output/tree.txt"
);
```

the values are loaded from the external configuration file.

The application flow is:

1. `ConfigurationLoader` reads `configuration.properties`.
2. The directory path is obtained from the property `directory`.
3. The output TXT file path is obtained from the property `outputFile`.
4. `FileManager` uses these values to create the directory tree file.

Example configuration:

```properties
directory=src/main/java
outputFile=output/tree.txt
```

Example execution:

```text
= Configuration loaded =

Directory: src/main/java

Output file: output/tree.txt


File load was satisfactory.
```

After execution, the application creates the TXT file:

```text
output/tree.txt
```

with the directory information:

```text
Directory: java 08/07/2026 23:29
File: Album.java 08/07/2026 09:43
File: FileManager.java 08/07/2026 22:59
File: MainL2.java 08/07/2026 23:11
```

The configuration can be changed without modifying the Java code. For example, another directory or output file can be used only by editing `configuration.properties`.

---

# CONCLUSIONS

This exercise helped me understand the importance of separating configuration from application logic.

Using a `.properties` file makes the application more flexible because paths and settings can be changed without recompiling the code.

I also practiced creating a configuration loader class responsible only for reading external settings.

This structure improves code organization and is commonly used in professional Java applications.