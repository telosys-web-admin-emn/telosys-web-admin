package org.demo.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class FileInfo {

	public static void main(String[] args) {
		
		Path file = Paths.get("D:/TMP", "foo.txt");
		BasicFileAttributes attr;
		try {
			// File info 
			attr = Files.readAttributes(file, BasicFileAttributes.class);
			
			// creationTime : OK / Windows but NOT AVAILABLE / LINUX (returns lastModifiedTime)
			System.out.println("creationTime     : " + attr.creationTime());
			
			// lastModifiedTime : OK / Windows and Linux
			System.out.println("lastModifiedTime : " + attr.lastModifiedTime());
			
		} catch (IOException e) {
			e.printStackTrace();
			// e.g. : NoSuchFileException
		}


	}

}
