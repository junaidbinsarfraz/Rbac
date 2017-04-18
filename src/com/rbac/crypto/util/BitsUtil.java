package com.rbac.crypto.util;

import it.unisa.dia.gas.crypto.circuit.BooleanCircuit.BooleanCircuitGate;
import it.unisa.dia.gas.crypto.circuit.Gate.Type;

public class BitsUtil {

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

}
