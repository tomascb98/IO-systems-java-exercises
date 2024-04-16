package com.epam.autotasks;


import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class FileTree {

    // Generates a tree structure for the given directory or file path.
    public Optional<String> tree(final Path path) {
        // Check if path is null
        if (path == null)
            return Optional.empty();

        // Check if the path exists
        if (!Files.exists(path))
            return Optional.empty();
            // If the path is a regular file
        else if (Files.isRegularFile(path)) {
            try {
                // Format the file name
                return Optional.of(fileNameFormatter(path));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        // If the path is a directory
        else {
            try {
                // Format the directory structure
                String treeFile = directoryFormatter(path, false, false);
                // Clean up formatting
                treeFile = treeFile.replaceAll("\n│  ", "\n");
                // Remove initial characters to maintain consistency
                return Optional.of(treeFile.substring(3));
            } catch (Exception e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }
    }

    // Format file name with its size
    public String fileNameFormatter(Path path) throws Exception {
        if (Files.isDirectory(path))
            // Format directory name with its total size
            return path.getFileName() + " " + sizeDirectory(path) + " bytes";
        // Format file name with its size
        return path.getFileName() + " " + Files.size(path) + " bytes";
    }

    // Calculate size of directory and its contents
    // Calculate size of directory and its contents
    private long sizeDirectory(Path path) throws Exception {
        // AtomicLong to handle size accumulation in a concurrent context
        AtomicLong size = new AtomicLong();

        // Try-with-resources block to ensure the DirectoryStream is closed properly
        try (DirectoryStream<Path> streamFiles = Files.newDirectoryStream(path)) {
            // Iterate over each file or directory within the directory
            streamFiles.forEach((p1) -> {
                // Check if the current path is a directory
                if (Files.isDirectory(p1)) {
                    try {
                        // Recursively calculate and add the size of subdirectories
                        size.set(size.get() + sizeDirectory(p1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        // Add the size of regular files to the total size
                        size.set(size.get() + Files.size(p1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        // Return the accumulated size of the directory and its contents
        return size.get();
    }

    // Format directory structure
    private String directoryFormatter(Path path, boolean isLastFile, boolean lastDirectory) throws Exception {
        // If it's a regular file, format as sub-file
        if (Files.isRegularFile(path))
            return subFileFormatter(path, isLastFile);

        // StringBuilder to construct the directory structure
        StringBuilder sb = new StringBuilder();
        // Append the directory name with its size
        sb.append(fileNameFormatter(path)).append("\n");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            // Store paths in a list and sort them
            List<Path> sortedPaths = new ArrayList<>();

            stream.forEach(sortedPaths::add);
            sortedPaths.sort(new Comparator<>() {
                @Override
                public int compare(Path p1, Path p2) {
                    // Check if both paths are directories or files
                    boolean isDir1 = Files.isDirectory(p1);
                    boolean isDir2 = Files.isDirectory(p2);

                    // Compare based on directory or file status
                    if (isDir1 == isDir2) {
                        // If both are of the same type, sort alphabetically
                        return p1.getFileName().toString().compareToIgnoreCase(p2.getFileName().toString());
                    } else {
                        // Sort directories before files
                        return isDir1 ? -1 : 1;
                    }
                }
            });

            // Iterate through the sorted paths
            for (int i = 0; i < sortedPaths.size(); i++) {
                boolean lastFile = i == sortedPaths.size() - 1;
                // Append formatted sub-directory
                sb.append(directoryFormatter(sortedPaths.get(i), lastFile, lastFile));
            }
        }

        // Format and return the directory structure
        return subDirectoryFormatter(sb.toString(), lastDirectory);
    }

    // Format sub-file
    private String subFileFormatter(Path path, boolean isLastFile) throws Exception {
        // Check if it's the last file in the directory
        if (isLastFile)
            return "└─ " + fileNameFormatter(path) + "\n";
        // Format sub-file with the appropriate prefix
        return "├─ " + fileNameFormatter(path) + "\n";
    }

    // Format sub-directory
    private String subDirectoryFormatter(String directory, boolean isLastDirectory) {
        // Check if it's the last directory
        if (isLastDirectory) {
            // Format sub-directory with the appropriate prefix and adjust spacing
            String directoryFormatted = "└─ " + directory.replaceAll("\n", "\n   ");
            // Remove unnecessary characters at the end
            return directoryFormatted.substring(0, directoryFormatted.length() - 3);
        } else {
            // Format sub-directory with the appropriate prefix and adjust spacing
            String directoryFormatted = "├─ " + directory.replaceAll("\n", "\n│  ");
            // Remove unnecessary characters at the end
            return directoryFormatted.substring(0, directoryFormatted.length() - 3);
        }
    }

}

