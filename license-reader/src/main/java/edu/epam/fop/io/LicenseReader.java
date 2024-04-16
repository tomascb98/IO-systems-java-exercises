package edu.epam.fop.io;

import java.io.*;

public class LicenseReader {

  public void collectLicenses(File root, File outputFile) {
    if(root == null || outputFile == null || !root.exists() || !(root.isDirectory() && root.canExecute()) || !root.canRead())
      throw new IllegalArgumentException();

    File[] files = root.listFiles();
    for (File file : files) {
      if(file.isDirectory()){
        collectLicenses(file, outputFile);
        continue;
      }
      try (BufferedReader reader = new BufferedReader(new FileReader(file));
           BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))){
        writer.write(licenseFormatter(reader, file.getName()));
      }catch (IOException e){
        e.printStackTrace();
      }
    }
  }

  private String licenseFormatter (BufferedReader reader, String nameOfFile){
    StringBuilder sb = new StringBuilder();
    String nameLicense = null;
    String issuedBy = null;
    String issuedOn = null;
    String expiresOn = "unlimited";
    String line;
    try(reader) {
      line = reader.readLine();
      if(!line.equals("---")) return "";
      else{
        while((line = reader.readLine()) != null){
          String[] lineSplit = line.split(": ");
          switch (lineSplit[0]){
            case "License": nameLicense = lineSplit[1];
              break;
            case "Issued by": issuedBy = lineSplit[1];
              break;
            case "Issued on": issuedOn = lineSplit[1];
              break;
            case "Expires on": expiresOn = lineSplit[1];
              break;
            default: break;
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sb.append("License for ")
            .append(nameOfFile)
            .append(" is ")
            .append(nameLicense)
            .append(" issued by ")
            .append(issuedBy)
            .append(" [")
            .append(issuedOn)
            .append(" - ")
            .append(expiresOn)
            .append("]\n")
            .toString();
  }
}
