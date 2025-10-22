package br.com.ocauamotta.GerenciadorDeProdutos.controllers;

import br.com.ocauamotta.GerenciadorDeProdutos.dtos.ErrorResponse;
import br.com.ocauamotta.GerenciadorDeProdutos.exceptions.BadRequestException;
import br.com.ocauamotta.GerenciadorDeProdutos.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de testes unitários para o handler de exceções global ({@code GlobalExceptionHandler}).
 * O objetivo é garantir que cada exceção lançada resulte em uma resposta HTTP
 * padronizada e com o código de status correto.
 */
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private WebRequest request;

    /**
     * Configuração inicial executada antes de cada teste.
     * Inicializa o {@code GlobalExceptionHandler} e simula o {@code WebRequest}
     * para garantir que o URI esteja disponível para o DTO de erro.
     */
    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        request = mock(WebRequest.class);
    }

    /**
     * Testa o tratamento da exceção {@code EntityNotFoundException}.
     * Deve garantir que o status HTTP retornado seja 404 NOT FOUND e que a
     * mensagem da exceção seja corretamente mapeada.
     */
    @Test
    void deveTratarEntityNotFoundException() {
        when(request.getDescription(false)).thenReturn("uri=/produtos/1");

        EntityNotFoundException ex = new EntityNotFoundException("Produto não encontrado");

        ResponseEntity<ErrorResponse> response = handler.handleEntityNotFound(ex, request);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Produto não encontrado", response.getBody().message());
        assertEquals("/produtos/1", response.getBody().path());
    }

    /**
     * Testa o tratamento da exceção {@code BadRequestException}.
     * Deve garantir que o status HTTP retornado seja 400 BAD REQUEST e que a
     * mensagem da exceção seja corretamente mapeada.
     */
    @Test
    void deveTratarBadRequestException() {
        when(request.getDescription(false)).thenReturn("uri=/produtos");

        BadRequestException ex = new BadRequestException("ID não informado");

        ResponseEntity<ErrorResponse> response = handler.handleBadRequest(ex, request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("ID não informado", response.getBody().message());
        assertEquals("/produtos", response.getBody().path());
    }

    /**
     * Testa o tratamento de ({@code RuntimeException}).
     * Deve garantir que o status HTTP retornado seja 500 INTERNAL SERVER ERROR e
     * que a mensagem de erro seja corretamente mapeada.
     */
    @Test
    void deveTratarRuntimeException() {
        when(request.getDescription(false)).thenReturn("uri=/produtos");

        RuntimeException ex = new RuntimeException("Erro inesperado");

        ResponseEntity<ErrorResponse> response = handler.handleRuntimeException(ex, request);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Erro inesperado", response.getBody().message());
        assertEquals("/produtos", response.getBody().path());
    }

    /**
     * Testa o tratamento de exceções genéricas ({@code Exception}).
     * Deve garantir que o status HTTP retornado seja 500 INTERNAL SERVER ERROR
     * e que a mensagem de erro genérica seja corretamente mapeada.
     */
    @Test
    void deveTratarExceptionGenerica() {
        when(request.getDescription(false)).thenReturn("uri=/produtos");

        Exception ex = new Exception("Erro crítico");

        ResponseEntity<ErrorResponse> response = handler.handleException(ex, request);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Ocorreu um erro no servidor.", response.getBody().message());
        assertEquals("/produtos", response.getBody().path());
    }
}
