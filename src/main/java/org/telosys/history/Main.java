package org.telosys.history;

import org.telosys.history.Generator;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Generator.generateHistory();
        /*File folder = new File(Configuration.HISTORY_FOLDER_PATH);
        File folder = new File(Configuration.HISTORY_FOLDER_PATH);
        System.out.println(Configuration.HISTORY_FOLDER_PATH);
        System.out.println(System.getProperty("user.dir"));
        System.out.println(folder.exists());
        for (File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                String fileName = fileEntry.getName();
                System.out.println(fileName);
            }
        }*/
    }
}
