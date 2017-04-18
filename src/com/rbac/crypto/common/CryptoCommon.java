package com.rbac.crypto.common;

import java.security.spec.AlgorithmParameterSpec;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

import com.rbac.crypto.controller.EncryptionController;
import com.rbac.crypto.controller.KeyController;

import it.unisa.dia.gas.crypto.kem.cipher.engines.KEMCipher;

public class CryptoCommon {
	
	public static KEMCipher kemCipher;
	public static AlgorithmParameterSpec iv;
	
	public static byte[] encapsulation;
	
	public static EncryptionController encryptionController = new EncryptionController();
	public static KeyController keyController = new KeyController();

	// Should be init from db at start if already generated
	public static AsymmetricCipherKeyPair keyPair;
	public static String lastBitsUsedForCircuit;
	public static Integer lastBytesUserForCircuit;
    
}
