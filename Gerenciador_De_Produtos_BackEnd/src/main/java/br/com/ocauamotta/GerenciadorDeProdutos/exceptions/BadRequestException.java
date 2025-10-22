package br.com.ocauamotta.GerenciadorDeProdutos.exceptions;

/**
 * Exception personalizada lançada para indicar que a requisição do cliente
 * é inválida ou mal formada.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
