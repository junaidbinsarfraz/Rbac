package com.rbac.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static byte[] readFile(String fileName) {

		// Read from file
		File file = new File(Constants.FILE_DIRECTORY + fileName);
		
		if (file.exists()) {
			
			try {
				byte[] bytes = Files.readAllBytes(file.toPath());
				
				return bytes;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

		}

		return null;
	}

	public static Boolean writeIntoFile(String fileName, byte[] value, Boolean isUpdated) throws IOException {

		File file = new File(Constants.FILE_DIRECTORY + fileName);

		if(Boolean.FALSE.equals(isUpdated)) {
			if (Boolean.FALSE.equals(file.exists())) {
				file.createNewFile();
			} else {
				return Boolean.FALSE;
			}
		}
		
		FileOutputStream stream = new FileOutputStream(file);
		try {
			stream.write(value);
		} catch(Exception e) {
			return Boolean.FALSE;
		} finally {
			stream.flush();
		    stream.close();
		}
		
		return Boolean.TRUE;
		
	}

	public static void appendIntoFile(String fileName, String value) throws IOException {

		File file = new File(Constants.FILE_DIRECTORY + fileName);

		if (Boolean.FALSE.equals(file.exists())) {
			throw new IOException("File not found");
		}

		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter = null;

		try {

			fileWriter = new FileWriter(file.getAbsolutePath(), Boolean.TRUE);
			bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(((value != null) ? value : ""));

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bufferedWriter != null)
					bufferedWriter.close();

				if (fileWriter != null)
					fileWriter.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
	}
	
	public static Boolean deleteFile(String fileName) {
		
		File file = new File(Constants.FILE_DIRECTORY + fileName);
		
		return file.delete();
	}
	
	
	public static List<String> listFiles() {
		
		List<String> files = new ArrayList<String>();
		
		try {
			
			File currentDirectory = new File(new File(Constants.FILE_DIRECTORY).getAbsolutePath());
			/*System.out.println(currentDirectory.getCanonicalPath());
			System.out.println(currentDirectory.getAbsolutePath());*/
			
			if(Boolean.TRUE.equals(currentDirectory.exists())) {
				if(Boolean.TRUE.equals(currentDirectory.isDirectory())) {
					for(File file : currentDirectory.listFiles()) {
						files.add(file.getName());
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return files;
	}

}
