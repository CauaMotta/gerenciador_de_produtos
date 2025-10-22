package br.com.ocauamotta.GerenciadorDeProdutos.exceptions;

/**
 * Exception personalizada lançada para indicar que uma entidade
 * não foi encontrada no banco de dados.
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
