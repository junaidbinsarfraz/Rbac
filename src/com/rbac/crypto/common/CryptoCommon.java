package com.rbac.crypto.common;

import java.security.spec.AlgorithmParameterSpec;

import org.bouncycastle.crypto.CipherParameters;

import com.rbac.crypto.controller.EncryptionController;
import com.rbac.crypto.controller.KeyController;

import it.unisa.dia.gas.crypto.kem.cipher.engines.KEMCipher;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

/**
 * The class CryptoCommon is use to store crypto-common variables that will be
 * use across the program
 */
public class CryptoCommon {

	public static KEMCipher kemCipher;
	public static AlgorithmParameterSpec iv;

	public static String lastFileName;

	public static EncryptionController encryptionController = new EncryptionController();
	public static KeyController keyController = new KeyController();

	// Should be init from db at start if already generated
	public static CipherParameters masterSecretKey;
	public static CipherParameters publicKey;
	public static String lastBitsUsedForCircuit;
	public static Integer lastBytesUserForCircuit;

	public static Pairing paring = PairingFactory.getPairing("params/mm/ctl13/toy.properties");

}
