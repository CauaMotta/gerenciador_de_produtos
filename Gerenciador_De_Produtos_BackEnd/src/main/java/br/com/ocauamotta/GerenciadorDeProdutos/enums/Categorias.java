package br.com.ocauamotta.GerenciadorDeProdutos.enums;

/**
 * Este enum define as categorias de produtos disponíveis.
 */
public enum Categorias {
    SHOES("calcados"),
    CLOTHES("roupas"),
    UNDERWEAR("roupas_intimas"),
    ACCESSORIES("acessorios");

    private final String categoria;

    /**
     * Construtor privado para associar o valor de string a cada constante do enum.
     *
     * @param categoria A representação em {@code String} da categoria.
     */
    Categorias(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Retorna a representação em {@code String} da categoria.
     *
     * @return A {@code String} da categoria.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Converte uma {@code String} para o valor correspondente do enum {@code Categorias}.
     * A comparação não diferencia maiúsculas e minúsculas.
     *
     * @param categoria A {@code String} da categoria a ser convertida.
     * @return A constante {@code Categorias} correspondente.
     * @throws IllegalArgumentException Se a {@code String} fornecida não corresponder a nenhuma categoria.
     */
    public static Categorias fromString(String categoria) {
        for (Categorias cat : values()) {
            if(cat.getCategoria().equalsIgnoreCase(categoria)) return cat;
        }
        throw new IllegalArgumentException("Categoria inválida: " + categoria);
    }
}
