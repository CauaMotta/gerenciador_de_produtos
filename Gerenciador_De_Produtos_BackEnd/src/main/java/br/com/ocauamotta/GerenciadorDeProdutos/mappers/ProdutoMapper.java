package br.com.ocauamotta.GerenciadorDeProdutos.mappers;

import br.com.ocauamotta.GerenciadorDeProdutos.dtos.ProdutoResponseDTO;
import br.com.ocauamotta.GerenciadorDeProdutos.models.Produto;
import org.springframework.stereotype.Component;

/**
 * Classe utilitária para conversão entre {@code Produto} e {@code ProdutoDTO}.
 *
 * Fornece métodos estáticos que facilitam o mapeamento de entidades para DTOs
 * e vice-versa.
 */
@Component
public class ProdutoMapper {

    /**
     * Construtor privado para evitar que a classe seja instanciada.
     */
    private ProdutoMapper() {
    }

    /**
     * Converte um {@code ProdutoResponseDTO} em uma entidade {@code Produto}.
     *
     * @param dto DTO a ser convertido.
     * @return Entidade {@code Produto} correspondente, ou {@code null} se o DTO for nulo.
     */
    public static Produto toEntity(ProdutoResponseDTO dto) {
        if (dto == null) return null;

        return new Produto(
                dto.id(),
                dto.nome(),
                dto.preco(),
                dto.categoria(),
                dto.createdAt(),
                dto.updatedAt(),
                dto.deletedAt()
        );
    }

    /**
     * Converte uma entidade {@code Produto} para {@code ProdutoResponseDTO}.
     *
     * @param produto Entidade a ser convertida.
     * @return DTO correspondente, ou {@code null} se o produto for nulo.
     */
    public static ProdutoResponseDTO toResponseDTO(Produto produto) {
        if (produto == null) return null;

        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getCategoria(),
                produto.getCreatedAt(),
                produto.getUpdatedAt(),
                produto.getDeletedAt()
        );
    }
}
