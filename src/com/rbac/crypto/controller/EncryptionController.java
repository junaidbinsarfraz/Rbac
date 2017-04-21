package com.rbac.crypto.controller;

import org.bouncycastle.crypto.CipherParameters;

import com.rbac.crypto.common.CryptoCommon;

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
                                    (GGHSW13PublicKeyParameters) CryptoCommon.keyPair.getPublic(),
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
        	CryptoCommon.kemCipher.init(
                    false,
                    new KEMCipherDecryptionParameters(secretKey, CryptoCommon.encapsulation, 128),
                    CryptoCommon.iv
            );
            return CryptoCommon.kemCipher.doFinal(ciphertext);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	public byte[] encrypt(String message) {
        try {
            return CryptoCommon.kemCipher.doFinal(message.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	// EncPE(FS[0], y, DecPE(msk, FS[p*]))
	public byte[] reEncrypt(byte[] ciphertext, String bits) {
		return this.encrypt(new String(this.decrypt(CryptoCommon.keyController.generateUserKey(bits == null ? CryptoCommon.lastBitsUsedForCircuit : bits), ciphertext)));
	}
	
}
