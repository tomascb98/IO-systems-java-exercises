package com.epam.autotasks;

import java.io.*;

public class CipherCreator {
    TransformerInputStream transformerInputStream;

    private CipherCreator() {
        this.transformerInputStream = new TransformerInputStream();
    }

    public static StringBuilder cipherText(File input) {
        if(input.isDirectory() || !input.exists()) throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(input))){
            int currentCharacter;
            while((currentCharacter = reader.read()) != -1){
                if(currentCharacter == 10) {
                    sb.append("\n");
                    continue;
                }else if(currentCharacter>=97 && currentCharacter<=122){
                    currentCharacter = currentCharacter >=121 ? 97+1 - (122-currentCharacter) : currentCharacter + 2;
                }else if(currentCharacter>=65 && currentCharacter<=90){
                    currentCharacter = currentCharacter >=89 ? 65+1 - (90-currentCharacter) : currentCharacter + 2;
                }else{
                    continue;
                }
                sb.append(Character.toChars(currentCharacter));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb;
    }
}
