package com.minimarket.JPF_SalesSystem.utility;

public class MessageErrorException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MessageErrorException(String mensaje) {
		super(mensaje);
	}
}
