package br.com.ocauamotta.GerenciadorDeProdutos.controllers;

import br.com.ocauamotta.GerenciadorDeProdutos.dtos.ErrorResponse;
import br.com.ocauamotta.GerenciadorDeProdutos.exceptions.BadRequestException;
import br.com.ocauamotta.GerenciadorDeProdutos.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Classe de tratamento global de exceções.
 * Utiliza a anotação {@code @ControllerAdvice} para interceptar exceções
 * lançadas por quaisquer controllers e retornar respostas padronizadas.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata a exceção {@code EntityNotFoundException}, que geralmente é lançada
     * quando uma busca por ID não retorna um recurso.
     * Retorna um status HTTP 404 NOT FOUND.
     *
     * @param ex A exceção {@code EntityNotFoundException} capturada.
     * @param request O contexto da requisição web.
     * @return {@code ResponseEntity} contendo o status 404 e um {@code ErrorResponse} detalhado.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Trata a exceção {@code BadRequestException} lançada para indicar que a requisição do cliente
     * é inválida ou mal formada.
     * Retorna um status HTTP 400 BAD REQUEST.
     *
     * @param ex A exceção {@code BadRequestException} capturada.
     * @param request O contexto da requisição web.
     * @return {@code ResponseEntity} contendo o status 400 e um {@code ErrorResponse} detalhado.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Trata quaisquer exceções de ({@code RuntimeException}) que não foram tratadas
     * especificamente por outras exceptions.
     * Retorna um status HTTP 500 INTERNAL SERVER ERROR.
     *
     * @param ex A {@code RuntimeException} capturada.
     * @param request O contexto da requisição web.
     * @return {@code ResponseEntity} contendo o status 500 e um {@code ErrorResponse} detalhado.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Handler genérico para capturar qualquer {@code Exception} que não foi
     * capturada pelos manipuladores mais específicos.
     * Retorna um status HTTP 500 INTERNAL SERVER ERROR e uma mensagem genérica de erro no servidor
     * para segurança (evitar expor detalhes internos).
     *
     * @param ex A {@code Exception} capturada.
     * @param request O contexto da requisição web.
     * @return {@code ResponseEntity} contendo o status 500 e um {@code ErrorResponse} genérico.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Ocorreu um erro no servidor.",
                request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
