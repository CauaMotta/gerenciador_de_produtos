package br.com.ocauamotta.GerenciadorDeProdutos.models;

import br.com.ocauamotta.GerenciadorDeProdutos.enums.Categorias;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * Esta classe representa um produto cadastrado no sistema.
 *
 * <p>A entidade é mapeada para a tabela "produtos" no banco de dados
 * e contém informações como ID, nome, preço, categoria e datas de criação, atualização e exclusão.</p>
 *
 * <p>Os campos createdAt, updatedAt e deletedAt são úteis para
 * controle e versionamento de registros, permitindo implementar
 * exclusão lógica e histórico de alterações.</p>
 *
 * <p>Esta classe utiliza anotações JPA para persistência de dados e anotações Lombok
 * para geração automática de getters, setters e construtores.</p>
 */
@Entity
@Table(name = "produtos")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 150, nullable = false)
    private String nome;
    @Column(nullable = false)
    private Integer preco;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categorias categoria;
    @Column
    private ZonedDateTime createdAt;
    @Column
    private ZonedDateTime updatedAt;
    @Column
    private ZonedDateTime deletedAt;
}
