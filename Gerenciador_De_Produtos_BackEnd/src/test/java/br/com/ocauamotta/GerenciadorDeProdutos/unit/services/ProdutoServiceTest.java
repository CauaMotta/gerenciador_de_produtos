package br.com.ocauamotta.GerenciadorDeProdutos.services;

import br.com.ocauamotta.GerenciadorDeProdutos.dtos.ProdutoRequestDTO;
import br.com.ocauamotta.GerenciadorDeProdutos.dtos.ProdutoResponseDTO;
import br.com.ocauamotta.GerenciadorDeProdutos.enums.Categorias;
import br.com.ocauamotta.GerenciadorDeProdutos.exceptions.BadRequestException;
import br.com.ocauamotta.GerenciadorDeProdutos.exceptions.EntityNotFoundException;
import br.com.ocauamotta.GerenciadorDeProdutos.models.Produto;
import br.com.ocauamotta.GerenciadorDeProdutos.repositories.IProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de testes unitários para a classe de service ({@code ProdutoService}).
 * Utiliza Mockito para simular o comportamento do repositório ({@code IProdutoRepository}),
 * garantindo que apenas a lógica de negócio do serviço seja testada.
 */
@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private IProdutoRepository repository;

    @InjectMocks
    private ProdutoService service;

    private Produto produto;

    /**
     * Configuração inicial executada antes de cada teste.
     * Inicializa a entidade {@code Produto} com dados de exemplo.
     */
    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Camisa Vermelha");
        produto.setPreco(1000);
        produto.setCategoria(Categorias.CLOTHES);
        produto.setCreatedAt(LocalDateTime.now());
        produto.setUpdatedAt(LocalDateTime.now());
    }

    /**
     * Testa o cenário de sucesso para busca por ID.
     * Deve retornar o DTO de produto correspondente quando o ID existir.
     */
    @Test
    void deveRetornarProdutoQuandoIdExistir() {
        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        ProdutoResponseDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals("Camisa Vermelha", result.nome());
        verify(repository, times(1)).findById(1L);
    }

    /**
     * Testa o cenário de falha para busca por ID.
     * Deve lançar {@code EntityNotFoundException} quando o ID não for encontrado.
     */
    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistir() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> service.findById(1L)
        );

        assertEquals("Produto não encontrado com ID: 1", exception.getMessage());
        verify(repository, times(1)).findById(1L);
    }

    /**
     * Testa a criação de um novo produto.
     * Deve mapear o DTO de requisição para a entidade e salvar com sucesso no repositório.
     */
    @Test
    void deveSalvarProdutoComSucesso() {
        ProdutoRequestDTO request = new ProdutoRequestDTO("Calça Jeans", 1500, "roupas");

        when(repository.save(any(Produto.class))).thenAnswer(invocation -> {
            Produto saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        ProdutoResponseDTO result = service.save(request);

        assertNotNull(result);
        assertEquals("Calça Jeans", result.nome());
        assertEquals(1500, result.preco());
        assertEquals(Categorias.CLOTHES, result.categoria());
        verify(repository, times(1)).save(any(Produto.class));
    }

    /**
     * Testa a atualização de um produto existente com dados válidos.
     * Deve buscar a entidade, aplicar as alterações e salvar no repositório.
     */
    @Test
    void deveAtualizarProdutoComSucesso() {
        ProdutoRequestDTO request = new ProdutoRequestDTO("Calça Jeans", 1000, "roupas");
        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(repository.save(any(Produto.class))).thenReturn(produto);

        ProdutoResponseDTO result = service.update(1L, request);

        assertEquals("Calça Jeans", result.nome());
        verify(repository).save(any(Produto.class));
    }

    /**
     * Testa o cenário de falha na atualização quando o ID é nulo.
     * Deve lançar {@code BadRequestException}.
     */
    @Test
    void deveLancarExcecaoQuandoIdForNuloNoUpdate() {
        ProdutoRequestDTO request = new ProdutoRequestDTO("Calça Jeans", 1000, "roupas");

        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.update(null, request));

        assertEquals("O campo ID não foi informado.", ex.getMessage());
    }

    /**
     * Testa o cenário de falha na atualização quando o produto não existe.
     * Deve lançar {@code EntityNotFoundException}.
     */
    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistirNoUpdate() {
        ProdutoRequestDTO request = new ProdutoRequestDTO("Calça Jeans", 1000, "roupas");
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.update(1L, request));
    }

    /**
     * Testa a operação de exclusão lógica (Soft Delete).
     * Deve buscar o produto, preencher o campo {@code deletedAt} e salvar a entidade atualizada.
     */
    @Test
    void deveRealizarSoftDeleteComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        service.delete(1L);

        assertNotNull(produto.getDeletedAt());
        verify(repository, times(1)).save(produto);
    }

    /**
     * Testa o cenário de falha na exclusão quando o produto não existe.
     * Deve lançar {@code EntityNotFoundException}.
     */
    @Test
    void deveLancarExcecaoAoDeletarProdutoInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(1L));
        verify(repository, never()).save(any());
    }

    /**
     * Testa a busca de todos os produtos ativos sem aplicar filtros de categoria.
     */
    @Test
    void deveBuscarTodosProdutosAtivosSemFiltro() {
        Page<Produto> page = new PageImpl<>(List.of(produto));
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findAllByDeletedAtIsNull(pageable)).thenReturn(page);

        Page<ProdutoResponseDTO> result = service.findAllActive(null, null, pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("Camisa Vermelha", result.getContent().get(0).nome());
    }

    /**
     * Testa a busca de produtos ativos com filtro de categoria.
     */
    @Test
    void deveBuscarProdutosAtivosPorCategoria() {
        Page<Produto> page = new PageImpl<>(List.of(produto));
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findAllByDeletedAtIsNullAndCategoria(Categorias.CLOTHES, pageable)).thenReturn(page);

        Page<ProdutoResponseDTO> result = service.findAllActive("roupas", null, pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("Camisa Vermelha", result.getContent().get(0).nome());
    }

    /**
     * Testa a busca de todos os produtos logicamente apagados sem aplicar filtros de categoria.
     */
    @Test
    void deveBuscarTodosProdutosApagadosSemFiltro() {
        produto.setDeletedAt(LocalDateTime.now());
        Page<Produto> page = new PageImpl<>(List.of(produto));
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findAllByDeletedAtIsNotNull(pageable)).thenReturn(page);

        Page<ProdutoResponseDTO> result = service.findAllDeleted(null, null, pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("Camisa Vermelha", result.getContent().get(0).nome());
    }

    /**
     * Testa a busca de produtos logicamente apagados com filtro de categoria.
     */
    @Test
    void deveBuscarProdutosApagadosPorCategoria() {
        produto.setDeletedAt(LocalDateTime.now());
        Page<Produto> page = new PageImpl<>(List.of(produto));
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findAllByDeletedAtIsNotNullAndCategoria(Categorias.CLOTHES, pageable)).thenReturn(page);

        Page<ProdutoResponseDTO> result = service.findAllDeleted("roupas", null, pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("Camisa Vermelha", result.getContent().get(0).nome());
    }
}
