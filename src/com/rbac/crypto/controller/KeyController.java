package com.rbac.crypto.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.rbac.crypto.common.CryptoCommon;
import com.rbac.crypto.util.BitsUtil;
import com.rbac.crypto.util.CryptoConstants;
import com.rbac.crypto.util.KeyStoreUtil;

import it.unisa.dia.gas.crypto.circuit.BooleanCircuit;
import it.unisa.dia.gas.crypto.jpbc.fe.abe.gghsw13.engines.GGHSW13KEMEngine;
import it.unisa.dia.gas.crypto.jpbc.fe.abe.gghsw13.generators.GGHSW13KeyPairGenerator;
import it.unisa.dia.gas.crypto.jpbc.fe.abe.gghsw13.generators.GGHSW13ParametersGenerator;
import it.unisa.dia.gas.crypto.jpbc.fe.abe.gghsw13.generators.GGHSW13SecretKeyGenerator;
import it.unisa.dia.gas.crypto.jpbc.fe.abe.gghsw13.params.GGHSW13KeyPairGenerationParameters;
import it.unisa.dia.gas.crypto.jpbc.fe.abe.gghsw13.params.GGHSW13MasterSecretKeyParameters;
import it.unisa.dia.gas.crypto.jpbc.fe.abe.gghsw13.params.GGHSW13PublicKeyParameters;
import it.unisa.dia.gas.crypto.jpbc.fe.abe.gghsw13.params.GGHSW13SecretKeyGenerationParameters;
import it.unisa.dia.gas.crypto.kem.cipher.engines.KEMCipher;

public class KeyController {
	
	private AsymmetricCipherKeyPair keyPair;
	
	public void initKeys() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		
		CryptoCommon.kemCipher = new KEMCipher(
                Cipher.getInstance(CryptoConstants.ALGORITHM, "BC"),
                new GGHSW13KEMEngine()
        );

        // build the initialization vector.  This example is all zeros, but it
        // could be any value or generated using a random number generator.
		CryptoCommon.iv = new IvParameterSpec(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
		
		setup(CryptoConstants.N);
		
		CryptoCommon.masterSecretKey = keyPair.getPrivate();
		
		CryptoCommon.publicKey = keyPair.getPublic();
		
		KeyStoreUtil.serializeMasterSecretKey((GGHSW13MasterSecretKeyParameters) CryptoCommon.masterSecretKey, new FileOutputStream(CryptoConstants.KEY_DIRECTORY + CryptoConstants.MASTER_SECRET_KEY_FILE));
		
		KeyStoreUtil.serializePublicKey((GGHSW13PublicKeyParameters) CryptoCommon.publicKey, new FileOutputStream(CryptoConstants.KEY_DIRECTORY + CryptoConstants.PUBLIC_KEY_FILE));
		
//		KeyStoreUtil.serializeEncapsulation(CryptoCommon.encapsulation, new FileOutputStream(CryptoConstants.ENCAPSULATION_BYTE_FILE));
	}
	
	public void init() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		
		CryptoCommon.kemCipher = new KEMCipher(
                Cipher.getInstance(CryptoConstants.ALGORITHM, "BC"),
                new GGHSW13KEMEngine()
        );

        // build the initialization vector.  This example is all zeros, but it
        // could be any value or generated using a random number generator.
		CryptoCommon.iv = new IvParameterSpec(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
		
		CryptoCommon.masterSecretKey = KeyStoreUtil.deserializeMasterSecretKey(new FileInputStream(CryptoConstants.KEY_DIRECTORY + CryptoConstants.MASTER_SECRET_KEY_FILE), CryptoCommon.paring);
		
		CryptoCommon.publicKey = KeyStoreUtil.deserializePublicKey(new FileInputStream(CryptoConstants.KEY_DIRECTORY + CryptoConstants.PUBLIC_KEY_FILE), CryptoCommon.paring);
		
//		CryptoCommon.encapsulation = KeyStoreUtil.desreializeEncapsulation(new FileInputStream(CryptoConstants.ENCAPSULATION_BYTE_FILE));
		
		
	}
	
	public CipherParameters keyGen(BooleanCircuit circuit) {
        GGHSW13SecretKeyGenerator keyGen = new GGHSW13SecretKeyGenerator();
        keyGen.init(new GGHSW13SecretKeyGenerationParameters(
                (GGHSW13PublicKeyParameters) CryptoCommon.publicKey,
                (GGHSW13MasterSecretKeyParameters) CryptoCommon.masterSecretKey,
                circuit
        ));

        return keyGen.generateKey();
    }
	
	public AsymmetricCipherKeyPair setup(int n) {
        GGHSW13KeyPairGenerator setup = new GGHSW13KeyPairGenerator();
        setup.init(new GGHSW13KeyPairGenerationParameters(
                new SecureRandom(),
                new GGHSW13ParametersGenerator().init(
                		CryptoCommon.paring,
                        n).generateParameters()
        ));

        return (keyPair = setup.generateKeyPair());
    }
	
	// Circuit generation
	
	// User KeyGen. Can also be use for master secret key to generate dummy user-secret-key
	public CipherParameters generateUserKey(String bits) {
		
		if(bits.length() < CryptoConstants.BIT_LENGTH) {
			return null;
		}
		
		// Generate Circuit 
		
		BooleanCircuit circuit = BitsUtil.generateBooleanCircuit(bits);
		
		GGHSW13SecretKeyGenerator keyGen = new GGHSW13SecretKeyGenerator();
        keyGen.init(new GGHSW13SecretKeyGenerationParameters(
                (GGHSW13PublicKeyParameters) CryptoCommon.publicKey,
                (GGHSW13MasterSecretKeyParameters) CryptoCommon.masterSecretKey,
                circuit
        ));
        
        CryptoCommon.lastBitsUsedForCircuit = bits;

        return keyGen.generateKey();
	}

}
