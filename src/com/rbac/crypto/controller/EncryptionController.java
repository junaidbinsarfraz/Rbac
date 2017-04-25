package com.rbac.crypto.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.bouncycastle.crypto.CipherParameters;

import com.rbac.crypto.common.CryptoCommon;
import com.rbac.crypto.util.CryptoConstants;
import com.rbac.crypto.util.KeyStoreUtil;

import it.unisa.dia.gas.crypto.jpbc.fe.abe.gghsw13.params.GGHSW13EncryptionParameters;
import it.unisa.dia.gas.crypto.jpbc.fe.abe.gghsw13.params.GGHSW13PublicKeyParameters;
import it.unisa.dia.gas.crypto.kem.cipher.params.KEMCipherDecryptionParameters;
import it.unisa.dia.gas.crypto.kem.cipher.params.KEMCipherEncryptionParameters;

public class EncryptionController {
	
	public byte[] initEncryption(String assignment) {
        try {
            return CryptoCommon.kemCipher.init(
                    true,
                    new KEMCipherEncryptionParameters(
                            128,
                            new GGHSW13EncryptionParameters(
                                    (GGHSW13PublicKeyParameters) CryptoCommon.publicKey,
                                    assignment
                            )
                    ),
                    CryptoCommon.iv
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	public byte[] decrypt(CipherParameters secretKey, byte[] ciphertext) {
        try {
        	
        	byte[] encapsulation = KeyStoreUtil.desreializeEncapsulation(new FileInputStream(CryptoConstants.ENCAPSULATION_DIRECTORY + CryptoConstants.ENCAPSULATION_BYTE_FILE + CryptoCommon.lastFileName));
        	
        	CryptoCommon.kemCipher.init(
                    false,
                    new KEMCipherDecryptionParameters(secretKey, encapsulation, 128),
                    CryptoCommon.iv
            );
            return CryptoCommon.kemCipher.doFinal(ciphertext);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	public byte[] encrypt(byte[] message) {
        try {
        	
        	byte[] encapsulation = CryptoCommon.encryptionController.initEncryption(CryptoConstants.ASSIGNMENT);
        	
        	KeyStoreUtil.serializeEncapsulation(encapsulation, new FileOutputStream(CryptoConstants.ENCAPSULATION_DIRECTORY + CryptoConstants.ENCAPSULATION_BYTE_FILE + CryptoCommon.lastFileName));
        	
            return CryptoCommon.kemCipher.doFinal(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	// EncPE(FS[0], y, DecPE(msk, FS[p*]))
	public byte[] reEncrypt(byte[] ciphertext, String bits) {
		return this.encrypt(this.decrypt(CryptoCommon.keyController.generateUserKey(bits == null ? CryptoCommon.lastBitsUsedForCircuit : bits), ciphertext));
	}
	
}
