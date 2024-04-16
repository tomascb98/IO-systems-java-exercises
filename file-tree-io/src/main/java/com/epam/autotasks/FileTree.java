package com.epam.autotasks;

import java.io.File;
import java.util.*;

public class FileTree {

    // Method to generate a tree structure for a given directory path
    public Optional<String> tree(final String path) {
        // If path is null, return Optional.empty()
        if (path == null)
            return Optional.empty();

        // Create a File object for the specified path
        File currentFile = new File(path);

        // If the file does not exist, return Optional.empty()
        if (!currentFile.exists())
            return Optional.empty();
            // If the file is a regular file, return its formatted name and size
        else if (currentFile.isFile())
            return Optional.of(fileNameFormatter(currentFile));
            // If the file is a directory, generate the tree structure for it
        else {
            // Format the directory structure and remove unnecessary characters
            String treeFile = directoryFormatter(currentFile, false, false);
            treeFile = treeFile.replaceAll("\n│  ", "\n");
            return Optional.of(treeFile.substring(3));
        }
    }

    // Method to format the name and size of a file
    public String fileNameFormatter(File file) {
        // If the file is a directory, include its name and total size in bytes
        if (file.isDirectory())
            return file.getName() + " " + sizeDirectory(file) + " bytes";
        // If the file is a regular file, include its name and size in bytes
        return file.getName() + " " + file.length() + " bytes";
    }

    // Method to calculate the total size of a directory and its subdirectories
    private long sizeDirectory(File file) {
        long size = 0;
        // Iterate through each file in the directory
        for (File listFile : file.listFiles()) {
            // If the file is a directory, recursively calculate its size
            if (listFile.isDirectory())
                size += sizeDirectory(listFile);
                // If the file is a regular file, add its size to the total
            else
                size += listFile.length();
        }
        return size;
    }

    // Method to format the structure of subdirectories and files within a directory
    private String directoryFormatter(File file, boolean isLastFile, boolean lastDirectory) {
        // If the file is a regular file, format it as a subfile
        if (file.isFile())
            return subFileFormatter(file, isLastFile);

        // If the file is a directory, format its subdirectories and files
        StringBuilder sb = new StringBuilder();
        sb.append(fileNameFormatter(file)).append("\n");

        // Get the list of files within the directory and sort them
        File[] files = file.listFiles();
        Arrays.sort(files, new Comparator<>() {
            @Override
            public int compare(File o1, File o2) {
                // Sort directories first, then sort files alphabetically
                if ((o1.isDirectory() && o2.isDirectory()) || (o1.isFile() && o2.isFile()))
                    return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                else {
                    if (o1.isDirectory())
                        return -1;
                    else
                        return 1;
                }
            }
        });

        // Format each file within the directory
        for (int i = 0; i < files.length; i++) {
            boolean lastFile = i == files.length - 1;
            sb.append(directoryFormatter(files[i], lastFile, lastFile));
        }

        // Return the formatted directory structure
        return subDirectoryFormatter(sb.toString(), lastDirectory);
    }

    // Method to format a subfile within a directory
    private String subFileFormatter(File file, boolean isLastFile) {
        // If it's the last file, use a different formatting
        if (isLastFile)
            return "└─ " + fileNameFormatter(file) + "\n";
        // Otherwise, use standard formatting
        return "├─ " + fileNameFormatter(file) + "\n";
    }

    // Method to format a subdirectory within a directory
    private String subDirectoryFormatter(String directory, boolean isLastDirectory) {
        // If it's the last directory, use a different formatting
        if (isLastDirectory) {
            // Format the directory string and remove unnecessary characters
            String directoryFormatted = "└─ " + directory.replaceAll("\n", "\n   ");
            return directoryFormatted.substring(0, directoryFormatted.length() - 3);
        } else {
            // Format the directory string and remove unnecessary characters
            String directoryFormatted = "├─ " + directory.replaceAll("\n", "\n│  ");
            return directoryFormatted.substring(0, directoryFormatted.length() - 3);
        }
    }
}

