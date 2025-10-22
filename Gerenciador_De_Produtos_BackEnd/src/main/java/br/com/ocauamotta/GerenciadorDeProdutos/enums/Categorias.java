package br.com.ocauamotta.GerenciadorDeProdutos.enums;

/**
 * Este enum representa as categorias que um produto pode conter.
 *
 * @author Cauã Motta
 */
public enum Categorias {
    SHOES("calcados"),
    CLOTHES("roupas"),
    UNDERWEAR("roupas_intimas"),
    ACCESSORIES("acessorios");

    private final String categoria;

    Categorias(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public static Categorias fromString(String categoria) {
        for (Categorias cat : values()) {
            if(cat.getCategoria().equalsIgnoreCase(categoria)) return cat;
        }
        throw new IllegalArgumentException("Categoria inválida: " + categoria);
    }
}
