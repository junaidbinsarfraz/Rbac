package com.rbac.crypto.util;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import it.unisa.dia.gas.crypto.jpbc.fe.abe.gghsw13.params.GGHSW13MasterSecretKeyParameters;

public class KeyStoreUtil {
	
	public static void writeMasterKeyToFile(GGHSW13MasterSecretKeyParameters masterSecretKey) throws IOException {
		
		DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(CryptoConstants.MASTER_SECRET_KEY_FILE));
		
		dataOut.writeInt(masterSecretKey.getAlpha().getLengthInBytes());
		dataOut.write(masterSecretKey.getAlpha().toBytes());
		
		masterSecretKey.getParameters().getN();
		
	}
	
}
