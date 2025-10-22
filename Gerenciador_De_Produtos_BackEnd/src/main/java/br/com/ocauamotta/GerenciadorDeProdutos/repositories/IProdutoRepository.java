package br.com.ocauamotta.GerenciadorDeProdutos.repositories;

import br.com.ocauamotta.GerenciadorDeProdutos.enums.Categorias;
import br.com.ocauamotta.GerenciadorDeProdutos.models.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de Repositório para a entidade {@code Produto}.
 * Extends {@code JpaRepository} para fornecer métodos CRUD básicos e funcionalidades
 * de paginação e ordenação para a entidade {@code Produto}, usando {@code Long} como
 * o tipo do ID da chave primária.
 */
@Repository
public interface IProdutoRepository extends JpaRepository<Produto, Long> {
    /**
     * Busca uma página de produtos que **não foram logicamente excluídos** (ou seja,
     * onde o campo {@code deletedAt} é nulo).
     * Este método é usado para implementar o conceito de Soft Delete (exclusão lógica).
     *
     * @param pageable Objeto que contém informações de paginação.
     * @return Uma {@code Page} contendo os produtos ativos.
     */
    Page<Produto> findAllByDeletedAtIsNull(Pageable pageable);
    /**
     * Busca uma página de produtos que **não foram logicamente excluídos** e
     * pertencem a uma categoria específica.
     *
     * @param categorias O valor do enum {@code Categorias} para filtro.
     * @param pageable Objeto que contém informações de paginação.
     * @return Uma {@code Page} contendo os produtos ativos e filtrados pela categoria.
     */
    Page<Produto> findAllByDeletedAtIsNullAndCategoria(Categorias categorias, Pageable pageable);
    /**
     * Busca uma página de produtos que **foram logicamente excluídos**
     * (ou seja, onde o campo {@code deletedAt} **não** é nulo).
     *
     * @param pageable Objeto que contém informações de paginação.
     * @return Uma {@code Page} contendo os produtos inativos.
     */
    Page<Produto> findAllByDeletedAtIsNotNull(Pageable pageable);
    /**
     * Busca uma página de produtos que **foram logicamente excluídos** e
     * pertencem a uma categoria específica.
     *
     * @param categorias O valor do enum {@code Categorias} para filtro.
     * @param pageable Objeto que contém informações de paginação.
     * @return Uma {@code Page} contendo os produtos inativos e filtrados pela categoria.
     */
    Page<Produto> findAllByDeletedAtIsNotNullAndCategoria(Categorias categorias, Pageable pageable);
}
