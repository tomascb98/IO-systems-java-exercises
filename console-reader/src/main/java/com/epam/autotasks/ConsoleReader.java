package com.epam.autotasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader {

    public static void readNames() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int nameCount = 0;
        try {
            while (!(line = reader.readLine()).equals("0")){
                if(line.matches("[a-zA-Z,.' -]+")) {
                    nameCount++;
                } else
                    throw new RuntimeException();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Number of names: " + nameCount);
    }

    public static void readNumbers() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        String[] numbers;
        int sum = 0;
        try {
            line = reader.readLine();
            if(line.matches("[0-9 ]+")) {
                numbers = line.split(" ");
                for (String number: numbers) {
                    sum = sum + Integer.parseInt(number);
                }
            } else
                throw new RuntimeException();

        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Numbers: " + line);
        System.out.println("Sum: " + sum);
    }


}
