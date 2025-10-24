package br.com.ocauamotta.GerenciadorDeProdutos.controllers;

import br.com.ocauamotta.GerenciadorDeProdutos.dtos.ProdutoRequestDTO;
import br.com.ocauamotta.GerenciadorDeProdutos.dtos.ProdutoResponseDTO;
import br.com.ocauamotta.GerenciadorDeProdutos.dtos.TotalProdutosDTO;
import br.com.ocauamotta.GerenciadorDeProdutos.enums.Categorias;
import br.com.ocauamotta.GerenciadorDeProdutos.services.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Classe de testes unitários para a classe de controller ({@code ProdutoController}).
 * Utiliza {@code MockMvc} para simular requisições HTTP e verificar as respostas.
 * A camada de serviço ({@code ProdutoService}) é mockada com {@code @MockitoBean}.
 */
@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProdutoService service;

    private ProdutoResponseDTO produtoDTO;
    private ZonedDateTime time;

    /**
     * Configuração inicial executada antes de cada teste.
     * Inicializa o DTO de produto com dados de exemplo.
     */
    @BeforeEach
    void setUp() {
        time = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));

        produtoDTO = new ProdutoResponseDTO(1L, "Camisa Vermelha", 1000,
                Categorias.CLOTHES, time, time, null);
    }

    /**
     * Testa o endpoint GET /produtos/{id}.
     * Deve retornar um produto com status 200 (OK) quando o ID for encontrado.
     */
    @Test
    void deveRetornarProdutoQuandoIdExistir() throws Exception {
        when(service.findById(1L)).thenReturn(produtoDTO);

        mockMvc.perform(get("/produtos/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Camisa Vermelha"));

        verify(service, times(1)).findById(1L);
    }

    /**
     * Testa o endpoint POST /produtos.
     * Deve salvar um novo produto e retornar o DTO salvo com status 200 (OK).
     */
    @Test
    void deveSalvarProdutoComSucesso() throws Exception {
        ProdutoRequestDTO request = new ProdutoRequestDTO("Camisa Vermelha", 1000, "roupas");

        when(service.save(any(ProdutoRequestDTO.class))).thenReturn(produtoDTO);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Camisa Vermelha"));

        verify(service, times(1)).save(any(ProdutoRequestDTO.class));
    }

    /**
     * Testa o endpoint PUT /produtos/{id}.
     * Deve atualizar um produto existente e retornar o DTO atualizado com status 200 (OK).
     */
    @Test
    void deveAtualizarProdutoComSucesso() throws Exception {
        ProdutoRequestDTO request = new ProdutoRequestDTO("Camisa Vermelha", 1000, "roupas");

        when(service.update(eq(1L), any(ProdutoRequestDTO.class))).thenReturn(produtoDTO);

        mockMvc.perform(put("/produtos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Camisa Vermelha"));

        verify(service, times(1)).update(eq(1L), any(ProdutoRequestDTO.class));
    }

    /**
     * Testa o endpoint DELETE /produtos/{id}.
     * Deve realizar a exclusão lógica do produto e retornar uma mensagem de sucesso com status 200 (OK).
     */
    @Test
    void deveDeletarProdutoComSucesso() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/produtos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Removido com sucesso."));

        verify(service, times(1)).delete(1L);
    }

    /**
     * Testa o endpoint GET /produtos para listagem de itens ativos.
     * Deve retornar uma página (Page) contendo apenas produtos ativos com status 200 (OK).
     */
    @Test
    void deveRetornarPaginaDeProdutosAtivos() throws Exception {
        Page<ProdutoResponseDTO> page = new PageImpl<>(List.of(produtoDTO), PageRequest.of(0, 10), 1);

        when(service.findAllActive(isNull(), eq("id,asc"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/produtos")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].nome").value("Camisa Vermelha"))
                .andExpect(jsonPath("$.totalElements").value(1));

        verify(service, times(1)).findAllActive(isNull(), eq("id,asc"), any(Pageable.class));
    }

    /**
     * Testa o endpoint GET para listagem de itens logicamente deletados.
     * Deve retornar uma página (Page) contendo apenas produtos com o campo {@code deletedAt} preenchido, com status 200 (OK).
     */
    @Test
    void deveRetornarPaginaDeProdutosDeletados() throws Exception {
        produtoDTO = new ProdutoResponseDTO(1L, "Camisa Vermelha", 1000,
                Categorias.CLOTHES, time, time, time);

        Page<ProdutoResponseDTO> page = new PageImpl<>(List.of(produtoDTO), PageRequest.of(0, 10), 1);

        when(service.findAllDeleted(isNull(), eq("id,asc"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/produtos/apagados")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].nome").value("Camisa Vermelha"))
                .andExpect(jsonPath("$.content[0].deletedAt").isNotEmpty());

        verify(service, times(1)).findAllDeleted(isNull(), eq("id,asc"), any(Pageable.class));
    }

    /**
     * Testa o endpoint GET /produtos/calcular_total.
     * Deve retornar o {@code TotalProdutosDTO} calculado pelo serviço com status 200 (OK).
     * Verifica se a quantidade e o preço médio estão sendo retornados corretamente.
     */
    @Test
    void deveRetornarTotalDeProdutosComSucesso() throws Exception {
        TotalProdutosDTO dto = new TotalProdutosDTO(5, 1200);
        when(service.calcularTotalDeProdutos(isNull())).thenReturn(dto);

        mockMvc.perform(get("/produtos/calcular_total")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.qntProdutos").value(5))
                .andExpect(jsonPath("$.precoMedio").value(1200));

        verify(service, times(1)).calcularTotalDeProdutos(isNull());
    }
}
