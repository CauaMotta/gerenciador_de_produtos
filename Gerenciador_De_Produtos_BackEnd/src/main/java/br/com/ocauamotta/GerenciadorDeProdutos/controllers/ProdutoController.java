package br.com.ocauamotta.GerenciadorDeProdutos.controllers;

import br.com.ocauamotta.GerenciadorDeProdutos.dtos.ProdutoRequestDTO;
import br.com.ocauamotta.GerenciadorDeProdutos.dtos.ProdutoResponseDTO;
import br.com.ocauamotta.GerenciadorDeProdutos.dtos.TotalProdutosDTO;
import br.com.ocauamotta.GerenciadorDeProdutos.services.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gerenciar operações relacionadas a Produtos.
 * Expõe endpoints para listagem, busca por ID, criação, atualização e exclusão de produtos.
 * Todos os endpoints são acessíveis atraves de /produtos.
 */
@RestController
@RequestMapping(path = "/produtos")
public class ProdutoController {

    private final ProdutoService service;

    /**
     * Construtor para injeção de dependência do serviço de produtos.
     *
     * @param service O serviço que contém a lógica de negócio para a entidade Produto.
     */
    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    /**
     * Busca e retorna uma página de todos os produtos cadastrados ativos, com suporte a filtragem e ordenação customizada.
     * A listagem considera apenas produtos que não foram logicamente excluídos (soft delete).
     *
     * <p>Exemplo de requisição sem filtro: {@code GET /produtos?page=0&size=10}</p>
     * <p>Exemplo de requisição com filtro e ordenação customizada: {@code GET /produtos?categoria=roupas&sort=preco,desc&size=5}</p>
     *
     * @param categoria (Opcional) O nome da categoria a ser filtrada.
     * @param sort (Opcional, Padrão: "id,asc") Define os campos e a direção da ordenação (ex: "preco,asc" ou "preco,desc").
     * @param pageable Objeto que contém informações de paginação.
     * @return {@code ResponseEntity} contendo um {@code Page} de {@code ProdutoResponseDTO} dos produtos ativos.
     */
    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDTO>> findAllActive(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false, defaultValue = "id,asc") String sort,
            Pageable pageable
            ) {
        return ResponseEntity.ok(service.findAllActive(categoria, sort, pageable));
    }

    /**
     * Busca e retorna uma página de todos os produtos deletados, com suporte a filtragem e ordenação customizada.
     * A listagem considera apenas produtos que foram logicamente excluídos (soft delete).
     *
     * <p>Exemplo de requisição sem filtro: {@code GET /produtos/apagados?page=0&size=10}</p>
     * <p>Exemplo de requisição com filtro e ordenação customizada: {@code GET /produtos/apagados?categoria=roupas&sort=preco,desc&size=5}</p>
     *
     * @param categoria (Opcional) O nome da categoria a ser filtrada.
     * @param sort (Opcional, Padrão: "id,asc") Define os campos e a direção da ordenação (ex: "preco,asc" ou "preco,desc").
     * @param pageable Objeto que contém informações de paginação.
     * @return {@code ResponseEntity} contendo um {@code Page} de {@code ProdutoResponseDTO} dos produtos apagados.
     */
    @GetMapping(value = "/apagados")
    public ResponseEntity<Page<ProdutoResponseDTO>> findAllDeleted(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false, defaultValue = "id,asc") String sort,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAllDeleted(categoria, sort, pageable));
    }

    /**
     * Calcula a quantidade total de produtos ativos e o preço médio de acordo com a categoria,
     * se não informada retorna o calculo de todos.
     *
     * @return {@code ResponseEntity} contendo o {@code TotalProdutosDTO} com a quantidade total
     * de produtos ativos e o preço médio.
     */
    @GetMapping(value = "/calcular_total")
    public ResponseEntity<TotalProdutosDTO> calcularTotal(@RequestParam(required = false) String categoria) {
        return ResponseEntity.ok(service.calcularTotalDeProdutos(categoria));
    }

    /**
     * Busca um produto específico pelo seu ID.
     *
     * @param id O ID do produto a ser buscado.
     * @return {@code ResponseEntity} contendo o {@code ProdutoResponseDTO} correspondente ao ID.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoResponseDTO> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Salva um novo produto no banco de dados.
     * O produto a ser salvo é fornecido no corpo da requisição.
     *
     * @param produtoRequestDTO O DTO de requisição contendo os dados do produto a ser salvo.
     * @return {@code ResponseEntity} contendo o {@code ProdutoResponseDTO} do produto salvo.
     */
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> save(@RequestBody ProdutoRequestDTO produtoRequestDTO) {
        return ResponseEntity.ok(service.save(produtoRequestDTO));
    }

    /**
     * Atualiza um produto existente.
     * O ID do produto a ser atualizado é passado como variável na URL e os novos dados no body da requisição.
     *
     * @param id O ID do produto a ser atualizado.
     * @param produtoRequestDTO O DTO de requisição contendo os novos dados do produto.
     * @return {@code ResponseEntity} contendo o {@code ProdutoResponseDTO} do produto atualizado.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProdutoResponseDTO> update(@PathVariable(value = "id") Long id, @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        return ResponseEntity.ok(service.update(id, produtoRequestDTO));
    }

    /**
     * Atualiza o status de deletado a um produto do sistema através do ID.
     *
     * @param id O ID do produto a ser excluído.
     * @return {@code ResponseEntity} contendo a mensagem de sucesso da remoção.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Removido com sucesso.");
    }
}
