package br.com.ocauamotta.GerenciadorDeProdutos.dtos;

/**
 * DTO de requisição para a entidade {@code Produto}.
 *
 * Este record é utilizado para a criação e atualização
 * de entidades de produtos.
 */
public record ProdutoRequestDTO(
        String nome,
        Integer preco,
        String categoria
) {}
