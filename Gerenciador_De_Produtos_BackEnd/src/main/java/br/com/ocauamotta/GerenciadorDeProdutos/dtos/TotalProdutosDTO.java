package br.com.ocauamotta.GerenciadorDeProdutos.dtos;

/**
 * DTO usado para transportar informações resumidas sobre as estatísticas de produtos ativos.
 *
 * @param qntProdutos O número total de produtos ativos no sistema.
 * @param precoMedio O preço médio dos produtos ativos.
 */
public record TotalProdutosDTO(
        Integer qntProdutos,
        Integer precoMedio
) {
}
