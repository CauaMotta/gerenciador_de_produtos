package br.com.ocauamotta.GerenciadorDeProdutos.dtos;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * DTO usado para padronizar as respostas de erro da API.
 * Garante que todos os erros retornados aos clientes sigam uma estrutura consistente
 * e contenham informações essenciais para o diagnóstico, como status, mensagem e timestamp.
 *
 * @param status O código de status HTTP da resposta de erro.
 * @param error A frase de erro associada ao status HTTP.
 * @param message Uma mensagem detalhada sobre o erro ocorrido.
 * @param path O URI que foi acessado quando o erro ocorreu.
 * @param timestamp O momento exato em que o erro foi gerado.
 */
public record ErrorResponse(
        int status,
        String error,
        String message,
        String path,
        ZonedDateTime timestamp
) {
    /**
     * Construtor auxiliar que define automaticamente
     * o campo {@code timestamp} como o momento atual da criação.
     *
     * @param status O código de status HTTP da resposta de erro.
     * @param error A frase de erro associada ao status HTTP.
     * @param message Uma mensagem detalhada sobre o erro ocorrido.
     * @param path O URI que foi acessado.
     */
    public ErrorResponse(int status, String error, String message, String path) {
        this(status, error, message, path, ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
    }
}
