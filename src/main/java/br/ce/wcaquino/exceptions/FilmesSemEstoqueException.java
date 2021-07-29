package br.ce.wcaquino.exceptions;

public class FilmesSemEstoqueException extends Exception {

	private static final long serialVersionUID = 1L;

	public FilmesSemEstoqueException(String message) {
        super(message);
    }
}
