package br.com.ocauamotta.GerenciadorDeProdutos.dtos;

import br.com.ocauamotta.GerenciadorDeProdutos.enums.Categorias;

import java.time.ZonedDateTime;

/**
 * DTO de resposta para a entidade {@code Produto}.
 *
 * Este record é utilizado para transportar informações de produtos
 * entre as camadas da aplicação
 * sem expor diretamente a entidade JPA.
 */
public record ProdutoResponseDTO(
        Long id,
        String nome,
        Integer preco,
        Categorias categoria,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt,
        ZonedDateTime deletedAt
) {}
