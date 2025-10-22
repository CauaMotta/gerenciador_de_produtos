package br.com.ocauamotta.GerenciadorDeProdutos.services;

import br.com.ocauamotta.GerenciadorDeProdutos.dtos.ProdutoRequestDTO;
import br.com.ocauamotta.GerenciadorDeProdutos.dtos.ProdutoResponseDTO;
import br.com.ocauamotta.GerenciadorDeProdutos.enums.Categorias;
import br.com.ocauamotta.GerenciadorDeProdutos.exceptions.BadRequestException;
import br.com.ocauamotta.GerenciadorDeProdutos.exceptions.EntityNotFoundException;
import br.com.ocauamotta.GerenciadorDeProdutos.mappers.ProdutoMapper;
import br.com.ocauamotta.GerenciadorDeProdutos.models.Produto;
import br.com.ocauamotta.GerenciadorDeProdutos.repositories.IProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Classe de Serviço responsável por implementar as regras de negócio
 * para a entidade {@code Produto}.
 */
@Service
public class ProdutoService {

    private final IProdutoRepository repository;

    /**
     * Construtor para injeção de dependência do repositório de produtos.
     *
     * @param repository O repositório responsável pela persistência dos dados de {@code Produto}.
     */
    public ProdutoService(IProdutoRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca todos os produtos ativos (aqueles que possuem {@code deletedAt} null),
     * podendo filtrar por categoria e ordenar de acordo com o parâmetro {@code sort}.
     *
     * @param categoria Categoria opcional para filtrar os produtos.
     * @param sort      Campo e direção de ordenação (ex: "preco,asc" ou "preco,desc").
     * @param pageable  Objeto de paginação.
     * @return Uma {@code Page} de {@code ProdutoResponseDTO} dos produtos ativos.
     */
    public Page<ProdutoResponseDTO> findAllActive(String categoria, String sort, Pageable pageable) {
        Pageable sortedPageable = sortPage(pageable, sort);

        if (categoria != null && !categoria.isBlank()) {
            return repository.findAllByDeletedAtIsNullAndCategoria(Categorias.fromString(categoria), sortedPageable)
                    .map(ProdutoMapper::toResponseDTO);
        } else {
            return repository.findAllByDeletedAtIsNull(sortedPageable)
                    .map(ProdutoMapper::toResponseDTO);
        }
    }

    /**
     * Busca todos os produtos deletados,
     * podendo filtrar por categoria e ordenar de acordo com o parâmetro {@code sort}.
     *
     * @param categoria Categoria opcional para filtrar os produtos.
     * @param sort      Campo e direção de ordenação (ex: "preco,asc" ou "preco,desc").
     * @param pageable  Objeto de paginação.
     * @return Uma {@code Page} de {@code ProdutoResponseDTO} dos produtos deletados.
     */
    public Page<ProdutoResponseDTO> findAllDeleted(String categoria, String sort, Pageable pageable) {
        Pageable sortedPageable = sortPage(pageable, sort);

        if (categoria != null && !categoria.isBlank()) {
            return repository.findAllByDeletedAtIsNotNullAndCategoria(Categorias.fromString(categoria), sortedPageable)
                    .map(ProdutoMapper::toResponseDTO);
        } else {
            return repository.findAllByDeletedAtIsNotNull(sortedPageable)
                    .map(ProdutoMapper::toResponseDTO);
        }
    }

    /**
     * Busca um produto específico pelo seu ID.
     *
     * @param id O ID do produto a ser buscado.
     * @return O {@code ProdutoResponseDTO} correspondente ao ID.
     * @throws EntityNotFoundException Se o produto com o ID fornecido não for encontrado.
     */
    public ProdutoResponseDTO findById(Long id) {
        Produto entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));
        return ProdutoMapper.toResponseDTO(entity);
    }

    /**
     * Salva um novo produto no sistema.
     * Preenche os campos {@code createdAt} e {@code updatedAt} e converte a categoria
     * de {@code String} para o {@code enum Categorias}.
     *
     * @param produtoRequestDTO O DTO de requisição contendo os dados do produto.
     * @return O {@code ProdutoResponseDTO} do produto salvo.
     */
    public ProdutoResponseDTO save(ProdutoRequestDTO produtoRequestDTO) {
        LocalDateTime localDateTime = LocalDateTime.now();

        Produto produto = new Produto();
        produto.setNome(produtoRequestDTO.nome());
        produto.setPreco(produtoRequestDTO.preco());
        produto.setCategoria(Categorias.fromString(produtoRequestDTO.categoria()));
        produto.setCreatedAt(localDateTime);
        produto.setUpdatedAt(localDateTime);

        return ProdutoMapper.toResponseDTO(repository.save(produto));
    }

    /**
     * Atualiza um produto existente.
     * Primeiro busca a entidade, lança {@code EntityNotFoundException} se não existir,
     * e então aplica as modificações.
     *
     * @param id                O ID do produto a ser atualizado.
     * @param produtoRequestDTO O DTO de requisição com os novos dados.
     * @return O {@code ProdutoResponseDTO} do produto atualizado.
     * @throws BadRequestException     Se o ID não for fornecido na requisição.
     * @throws EntityNotFoundException Se o produto com o ID fornecido não for encontrado.
     */
    public ProdutoResponseDTO update(Long id, ProdutoRequestDTO produtoRequestDTO) {
        if (id == null) throw new BadRequestException("O campo ID não foi informado.");

        Produto entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));

        updateEntity(entity, produtoRequestDTO);

        return ProdutoMapper.toResponseDTO(repository.save(entity));
    }

    /**
     * Realiza a exclusão lógica (Soft Delete) de um produto.
     * Ao invés de remover o registro do banco de dados, preenche o campo {@code deletedAt}
     * com a data/hora atual.
     *
     * @param id O ID do produto a ser logicamente excluído.
     * @throws EntityNotFoundException Se o produto com o ID fornecido não for encontrado.
     */
    public void delete(Long id) {
        Produto entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));

        LocalDateTime localDateTime = LocalDateTime.now();
        entity.setDeletedAt(localDateTime);

        repository.save(entity);
    }

    /**
     * Método auxiliar privado para aplicar as atualizações do DTO na entidade {@code Produto}.
     * Apenas atualiza campos se os valores correspondentes no DTO não forem nulos ou em branco.
     * Atualiza o campo {@code updatedAt}.
     *
     * @param entity A entidade {@code Produto} a ser modificada.
     * @param dto    O DTO de requisição com os dados de atualização.
     */
    private void updateEntity(Produto entity, ProdutoRequestDTO dto) {
        LocalDateTime localDateTime = LocalDateTime.now();

        if (dto.nome() != null && !dto.nome().isBlank()) entity.setNome(dto.nome());
        if (dto.preco() != null) entity.setPreco(dto.preco());
        if (dto.categoria() != null && !dto.categoria().isBlank())
            entity.setCategoria(Categorias.fromString(dto.categoria()));
        entity.setUpdatedAt(localDateTime);
    }

    /**
     * Define um {@code Pageable} incorporando as informações de ordenação
     * fornecidas pelo parâmetro {@code sort}.
     *
     * <p>O parâmetro {@code sort} deve estar no formato "campo,direção" (ex: "nome,asc" ou "preco,desc").
     * Se a direção não for especificada ou for inválida, o padrão é ascendente (ASC).</p>
     *
     * @param pageable O {@code Pageable} original contendo informações de número da página e tamanho.
     * @param sort A string de ordenação, que pode ser nula ou em branco.
     * @return Um novo {@code Pageable} com as informações de ordenação aplicadas, ou o {@code Pageable}
     * original se a string de ordenação for nula ou vazia.
     */
    private Pageable sortPage(Pageable pageable, String sort) {
        Pageable sortedPageable = pageable;
        if (sort != null && !sort.isBlank()) {
            String[] sortParams = sort.split(",");
            String sortField = sortParams[0];
            Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            sortedPageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(direction, sortField)
            );
        }
        return sortedPageable;
    }
}