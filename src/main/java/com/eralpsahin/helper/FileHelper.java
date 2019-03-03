package com.eralpsahin.helper;

import com.eralpsahin.model.AkariBoard;

import java.io.*;

/**
 * Created by eralpsahin on 5.04.2017.
 */
public class FileHelper {


    public static AkariBoard readFile(String filePath) {
        int row = 0;
        int col = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                row++;
                col = line.stripLeading().stripTrailing().length();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AkariBoard(filePath, row, (col + 1) /2);
    }

}