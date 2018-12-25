package com.bjitgroup.file.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

public class Base64Converter {

	
	/**
	* This method converts the content of a source file into Base64 encoded data
	*/
	public static String encodeFile(String sourceFile) throws Exception {
		byte[] base64EncodedData = Base64.getEncoder().encode(loadFileAsBytesArray(sourceFile));
		String base64Label = new String(base64EncodedData);
		return base64Label;
	}
	
	/**
	* This method loads a file from file system and returns the byte array of the content.
	*
	* @param fileName
	* @return
	* @throws Exception
	*/
	private static byte[] loadFileAsBytesArray(String fileName) throws Exception {
		File file = new File(fileName);
		int length = (int) file.length();
		BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
		byte[] bytes = new byte[length];
		reader.read(bytes, 0, length);
		reader.close();
		return bytes;
	}

}
