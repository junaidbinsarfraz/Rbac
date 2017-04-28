package com.rbac.crypto.util;

import java.util.ArrayList;
import java.util.List;

import com.rbac.model.UserRole;

import it.unisa.dia.gas.crypto.circuit.BooleanCircuit;
import it.unisa.dia.gas.crypto.circuit.BooleanCircuit.BooleanCircuitGate;
import it.unisa.dia.gas.crypto.circuit.Gate.Type;

/**
 * The class BitsUtil is use to manipulate bits
 */
public class BitsUtil {

	/**
	 * The get32BitString() method is use to generate 32 long bits from given
	 * bits
	 * 
	 * @param bits
	 * @return 32 bit long bits
	 */
	public static String get32BitString(String bits) {

		StringBuilder sb = new StringBuilder(bits);

		for (int i = 0; i < 32 - bits.length(); i++) {
			sb.insert(0, "0");
		}

		return sb.toString();
	}

	public static BooleanCircuitGate on(int index, int depth) {
		BooleanCircuitGate r = new BooleanCircuitGate(Type.INPUT, index, depth);
		return (BooleanCircuitGate) r.set(Boolean.TRUE);
	}

	public static BooleanCircuitGate off(int index, int depth) {
		return new BooleanCircuitGate(Type.INPUT, index, depth);
	}

	/**
	 * The getBytesFromUserRoles() is use to combine bytes of all user's roles
	 * 
	 * @param userRoles
	 * @return combined bytes
	 */
	public static Integer getBytesFromUserRoles(List<UserRole> userRoles) {

		Integer bytes = 0;

		for (UserRole userR : userRoles) {
			bytes += userR.getRole().getBytes();
		}

		return bytes;
	}

	/**
	 * The generateBooleanCircuit() is use to generate BooleanCircuit from given bits
	 * @param bits
	 * @return generated BooleanCircuit
	 */
	public static BooleanCircuit generateBooleanCircuit(String bits) {
		List<BooleanCircuitGate> bcgList = new ArrayList<BooleanCircuitGate>();

		for (int i = 0; i < bits.length(); i++) {
			char bit = bits.charAt(i);

			if (bit == '1') {
				bcgList.add(BitsUtil.on(i, 1));
			} else {
				bcgList.add(BitsUtil.off(i, 1));
			}
		}

		BooleanCircuit circuit = new BooleanCircuit(CryptoConstants.N, CryptoConstants.Q, 3, bcgList.toArray(new BooleanCircuitGate[bcgList.size()]));
		return circuit;
	}

}
