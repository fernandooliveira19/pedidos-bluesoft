package br.com.bluesoft.desafio.exception;

public class FornecedorProdutoNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FornecedorProdutoNotFoundException() {
		
	}

	public FornecedorProdutoNotFoundException(String message) {
		super(message);
		
	}
	
}
