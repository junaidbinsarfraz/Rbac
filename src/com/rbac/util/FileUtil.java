package com.rbac.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static String readFile(String fileName) {

		StringBuilder sb = new StringBuilder();

		// Read from file
		File file = new File(Constants.FILE_DIRECTORY + fileName);

		if (file.exists()) {

			BufferedReader bufferedReader = null;
			FileReader fileReader = null;

			try {
				fileReader = new FileReader(file);
				bufferedReader = new BufferedReader(fileReader);

				String line;
				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line + "\n");
				}

			} catch (Exception ex) {
				return null;
			} finally {
				try {

					if (bufferedReader != null) {
						bufferedReader.close();
					}

					if (fileReader != null) {
						fileReader.close();
					}

				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			return sb.toString();
		}

		return null;
	}

	public static void writeIntoFile(String fileName, String value) throws IOException {

		File file = new File(Constants.FILE_DIRECTORY + fileName);

		if (Boolean.FALSE.equals(file.exists())) {
			file.createNewFile();
		}

		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter = null;

		try {

			fileWriter = new FileWriter(file.getAbsolutePath());
			bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(value != null ? value : "");

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

	public static void appendIntoFile(String fileName, String value) throws IOException {

		File file = new File(Constants.FILE_DIRECTORY + fileName);

		if (Boolean.FALSE.equals(file.exists())) {
			file.createNewFile();
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
		
		File file = new File(fileName);
		
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

	public static Boolean isFileExists(String fileName) {

		return Boolean.FALSE;
	}

}
