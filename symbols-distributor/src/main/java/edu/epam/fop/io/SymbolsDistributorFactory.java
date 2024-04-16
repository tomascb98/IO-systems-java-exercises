package edu.epam.fop.io;

import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class SymbolsDistributorFactory {

  public SymbolsDistributor getInstance() {
    return new SymbolsDistributor() {
      @Override
      public void distribute(File inputFile, Map<Predicate<Integer>, File> outputMapping) {
        if(!inputFile.exists() || inputFile.isDirectory() || !inputFile.canRead()) throw new IllegalArgumentException();
        Set<Map.Entry<Predicate<Integer>, File>> keys = outputMapping.entrySet();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
          int character;
          while((character = reader.read()) != -1){
            for (Map.Entry<Predicate<Integer>, File> key: keys) {
              if(!key.getValue().exists()) key.getValue().createNewFile();
              if(key.getKey().test(character)){
                BufferedWriter writer = new BufferedWriter(new FileWriter(key.getValue(), true));
                writer.write(character);
                writer.close();
              }
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };
  }
}
