package br.com.ocauamotta.GerenciadorDeProdutos.controllers;

import br.com.ocauamotta.GerenciadorDeProdutos.dtos.ProdutoRequestDTO;
import br.com.ocauamotta.GerenciadorDeProdutos.dtos.ProdutoResponseDTO;
import br.com.ocauamotta.GerenciadorDeProdutos.dtos.TotalProdutosDTO;
import br.com.ocauamotta.GerenciadorDeProdutos.enums.Categorias;
import br.com.ocauamotta.GerenciadorDeProdutos.services.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de testes unitários para a classe de controller ({@code ProdutoController}).
 * Utiliza Mockito para simular o comportamento da camada de serviço ({@code ProdutoService})
 * e verificar a interação e o formato das respostas HTTP.
 */
@ExtendWith(MockitoExtension.class)
class ProdutoControllerTest {

    @Mock
    private ProdutoService service;

    @InjectMocks
    private ProdutoController controller;

    private ProdutoResponseDTO produtoDTO;

    /**
     * Configuração inicial executada antes de cada teste.
     * Inicializa o DTO de produto com dados de exemplo.
     */
    @BeforeEach
    void setUp() {
        ZonedDateTime time = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));

        produtoDTO = new ProdutoResponseDTO(1L, "Camisa Vermelha", 1000,
                Categorias.CLOTHES, time, time, null);
    }

    /**
     * Testa o endpoint GET /produtos/{id}.
     * Deve retornar um produto com status 200 (OK) quando o ID for encontrado.
     */
    @Test
    void deveRetornarProdutoQuandoIdExistir() {
        when(service.findById(1L)).thenReturn(produtoDTO);

        ResponseEntity<ProdutoResponseDTO> response = controller.findById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Camisa Vermelha", response.getBody().nome());
        verify(service, times(1)).findById(1L);
    }

    /**
     * Testa o endpoint POST /produtos.
     * Deve salvar um novo produto e retornar o DTO salvo com status 200 (OK).
     */
    @Test
    void deveSalvarProdutoComSucesso() {
        ProdutoRequestDTO request = new ProdutoRequestDTO("Camisa Vermelha", 1000, "roupas");
        when(service.save(request)).thenReturn(produtoDTO);

        ResponseEntity<ProdutoResponseDTO> response = controller.save(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Camisa Vermelha", response.getBody().nome());
        verify(service, times(1)).save(request);
    }

    /**
     * Testa o endpoint PUT /produtos/{id}.
     * Deve atualizar um produto existente e retornar o DTO atualizado com status 200 (OK).
     */
    @Test
    void deveAtualizarProdutoComSucesso() {
        ProdutoRequestDTO request = new ProdutoRequestDTO("Camisa Vermelha", 1000, "roupas");
        when(service.update(1L, request)).thenReturn(produtoDTO);

        ResponseEntity<ProdutoResponseDTO> response = controller.update(1L, request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Camisa Vermelha", response.getBody().nome());
        verify(service, times(1)).update(1L, request);
    }

    /**
     * Testa o endpoint DELETE /produtos/{id}.
     * Deve realizar a exclusão lógica do produto e retornar uma mensagem de sucesso com status 200 (OK).
     */
    @Test
    void deveDeletarProdutoComSucesso() {
        doNothing().when(service).delete(1L);

        ResponseEntity<String> response = controller.delete(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Removido com sucesso.", response.getBody());
        verify(service, times(1)).delete(1L);
    }

    /**
     * Testa o endpoint GET /produtos para listagem de itens ativos.
     * Deve retornar uma página (Page) contendo apenas produtos ativos com status 200 (OK).
     */
    @Test
    void deveRetornarPaginaDeProdutosAtivos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProdutoResponseDTO> page = new PageImpl<>(List.of(produtoDTO));

        when(service.findAllActive(null, "id,asc", pageable)).thenReturn(page);

        ResponseEntity<Page<ProdutoResponseDTO>> response = controller.findAllActive(null, "id,asc", pageable);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals("Camisa Vermelha", response.getBody().getContent().get(0).nome());
        verify(service, times(1)).findAllActive(null, "id,asc", pageable);
    }

    /**
     * Testa o endpoint GET para listagem de itens logicamente deletados.
     * Deve retornar uma página (Page) contendo apenas produtos com o campo {@code deletedAt} preenchido, com status 200 (OK).
     */
    @Test
    void deveRetornarPaginaDeProdutosDeletados() {
        Pageable pageable = PageRequest.of(0, 10);
        ZonedDateTime time = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        produtoDTO = new ProdutoResponseDTO(1L, "Camisa Vermelha", 1000,
                Categorias.CLOTHES, time, time, time);
        Page<ProdutoResponseDTO> page = new PageImpl<>(List.of(produtoDTO));

        when(service.findAllDeleted(null, "id,asc", pageable)).thenReturn(page);

        ResponseEntity<Page<ProdutoResponseDTO>> response = controller.findAllDeleted(null, "id,asc", pageable);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals("Camisa Vermelha", response.getBody().getContent().get(0).nome());
        assertNotNull(response.getBody().getContent().get(0).deletedAt());
        verify(service, times(1)).findAllDeleted(null, "id,asc", pageable);
    }

    /**
     * Testa o endpoint GET /produtos/calcular_total.
     * Deve retornar o {@code TotalProdutosDTO} calculado pelo serviço com status 200 (OK).
     * Verifica se a quantidade e o preço médio estão sendo retornados corretamente.
     */
    @Test
    void deveRetornarTotalDeProdutosComSucesso() throws Exception {
        TotalProdutosDTO dto = new TotalProdutosDTO(5, 1200);
        when(service.calcularTotalDeProdutos(null)).thenReturn(dto);

        ResponseEntity<TotalProdutosDTO> response = controller.calcularTotal(null);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(5, response.getBody().qntProdutos());
        assertEquals(1200, response.getBody().precoMedio());

        verify(service, times(1)).calcularTotalDeProdutos(null);
    }
}
