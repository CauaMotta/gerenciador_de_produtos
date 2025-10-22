package br.com.ocauamotta.GerenciadorDeProdutos.models;

import br.com.ocauamotta.GerenciadorDeProdutos.enums.Categorias;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Esta classe representa um produto cadastrado no sistema.
 *
 * A entidade é mapeada para a tabela "produtos" no banco de dados
 * e contém informações como nome, preço, categoria e datas de criação, atualização e exclusão.
 *
 * Os campos createdAt, updatedAt e deletedAt são úteis para
 * controle e versionamento de registros, permitindo implementar
 * exclusão lógica e histórico de alterações.
 *
 * @author Cauã Motta
 */
@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nome;
    @Column
    private Integer preco;
    @Column
    private Categorias categoria;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;
    @Column
    private LocalDateTime deletedAt;
}
