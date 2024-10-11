package com.lendingcatalog.util;

import com.lendingcatalog.util.exception.FileStorageException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileStorageService {


    // Requirement: File I/O
    public static void writeContentsToFile(String contents, String filename, boolean appendFile) throws FileStorageException {
        //create a writer that will append files when needed and print out the contents on next available line
        try (PrintWriter dataOutput = new PrintWriter(new FileOutputStream(filename, appendFile))) {
            dataOutput.println(contents);
        } catch (FileNotFoundException e) {
            throw new FileStorageException("File not found: " + filename);
        }
    }

    public static List<String> readContentsOfFile(String filename) throws FileStorageException {
        //create variable that turns the string filename into a file object so it can be read
        File fileToRead = new File(filename);
        List<String> fileContents = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(fileToRead)) {
            do { //read each line and create a list of strings that holds each line of the file.
                String lineOfText = fileScanner.nextLine();
                fileContents.add(lineOfText);
            } while (fileScanner.hasNextLine());
        } catch (FileNotFoundException e) {
            throw new FileStorageException("File not found: " + filename);
        }
        return fileContents;
    }
}
