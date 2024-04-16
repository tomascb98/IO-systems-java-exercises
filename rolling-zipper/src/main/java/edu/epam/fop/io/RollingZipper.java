package edu.epam.fop.io;

import java.io.*;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class RollingZipper {

  // This method takes an iterable collection of input files, a rolling policy, and an iterator of output streams
  public void zip(Iterable<File> inputFiles, RollingPolicy policy, Iterator<OutputStream> outputs) throws IOException {
    // Get an iterator over the input files
    Iterator<File> fileIterator = inputFiles.iterator();

    // Loop through the output streams provided
    while(outputs.hasNext()) {
      // Try-with-resources: create a ZipOutputStream from the next output stream
      try (ZipOutputStream outputStream = new ZipOutputStream(outputs.next())) {
        // Set compression level to DEFLATED
        outputStream.setLevel(ZipOutputStream.DEFLATED);

        // Loop through the input files
        while (fileIterator.hasNext()) {
          // Get the current file
          File currentFile = fileIterator.next();

          // Create a new entry in the zip file with the name of the current file
          ZipEntry zipEntry = new ZipEntry(currentFile.getName());
          outputStream.putNextEntry(zipEntry);

          // Open an input stream for the current file to write on the zip archive (file --> zip)
          try(FileInputStream in = new FileInputStream(currentFile)){
            int len;
            // Read bytes from the input stream and write them to the zip output stream
            while((len = in.read()) != -1){
              outputStream.write(len);
            }
            // Close the zip entry, necessary.
            outputStream.closeEntry();
          }

          // Check if the rolling policy indicates that it's time to stop adding files to this zip archive
          if(policy.readyToRoll(zipEntry)) break;
        }
      }

      // Check if there are more input files to process
      if(!fileIterator.hasNext()) break;
    }
  }
}
