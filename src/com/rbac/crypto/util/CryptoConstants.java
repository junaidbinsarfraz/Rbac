package com.rbac.crypto.util;

public interface CryptoConstants {
	
	Integer N = 32;
	Integer Q = 0;
	Integer DEPTH = 1;
	Integer BIT_LENGTH = 32;
	
	String ALGORITHM = "AES/CBC/PKCS7Padding";
	String ASSIGNMENT = "11111111111111111111111111111101";
	
	String MASTER_SECRET_KEY_FILE = "msk.txt";
	String PUBLIC_KEY_FILE = "public.txt";
	String ENCAPSULATION_BYTE_FILE = "encapsulation-";
//	String KEY_DIRECTORY = "D:\\Junaid\\Github\\Rbac\\keys\\";
//	String ENCAPSULATION_DIRECTORY = "D:\\Junaid\\Github\\Rbac\\encapsulation\\";
	String KEY_DIRECTORY = "E:\\GithubNew\\Rbac\\keys\\";
	String ENCAPSULATION_DIRECTORY = "E:\\GithubNew\\Rbac\\encapsulation\\";
	
}
