import { useState } from 'react'
import Select, { type SingleValue } from 'react-select'

import Modal from '../Modal'
import Card from '../Card'
import Form from '../Form'

import priceFormatter from '../../utils/priceFormatter'
import buildProdutoQuery from '../../utils/buildProdutoQuery'
import { useApi } from '../../hooks/useApi'

import { Container, Title, StyledSelectWrapper, Content } from './styles'
import { Button, StyledClipLoader, Line } from '../../styles'
import variables from '../../styles/variables'

const options: Option[] = [
  { value: 'todas', label: 'Todas' },
  { value: 'roupas', label: 'Roupas' },
  { value: 'roupas_intimas', label: 'Roupas íntimas' },
  { value: 'calcados', label: 'Calçados' },
  { value: 'acessorios', label: 'Acessórios' }
]

const MainContent = () => {
  const [selectedOption, setSelectedOption] = useState<Option | null>(
    options[0]
  )
  const [showModal, setShowModal] = useState(false)
  const [priceSort, setPriceSort] = useState(false)
  const [showDeleted, setShowDeleted] = useState(false)
  const { data, loading, error } = useApi(
    buildProdutoQuery(selectedOption, priceSort, showDeleted)
  )
  const { data: statistics } = useApi<Statistics>(
    `/produtos/calcular_total${
      selectedOption !== options[0] ? `?categoria=${selectedOption?.value}` : ''
    }`
  )

  const handleChange = (option: SingleValue<Option>) => {
    setSelectedOption(option)
  }

  return (
    <>
      <Container>
        <header>
          <Title>
            <i className="fa-solid fa-folder"></i> Gerenciador de Produtos
          </Title>
          <Line />
          <div className="statistics">
            <p>Quantidade de produtos ativos: {statistics.qntProdutos}</p>
            <p>Preço médio: R$ {priceFormatter(statistics.precoMedio)}</p>
          </div>
          <div className="menu">
            <StyledSelectWrapper>
              <h3 className="select-title">Categoria: </h3>
              <Select
                classNamePrefix="custom-select"
                value={selectedOption}
                onChange={handleChange}
                isSearchable={false}
                placeholder="Todas"
                options={options}
              />
            </StyledSelectWrapper>
            <Button onClick={() => setPriceSort(!priceSort)}>
              {priceSort ? (
                <>
                  <i className="fa-solid fa-arrow-down-wide-short"></i> Ordenar
                  por maior preço
                </>
              ) : (
                <>
                  <i className="fa-solid fa-arrow-down-short-wide"></i> Ordenar
                  por menor preço
                </>
              )}
            </Button>
            <Button
              className={!showDeleted ? 'enable' : 'disabled'}
              onClick={() => setShowDeleted(!showDeleted)}
            >
              <i className="fa-solid fa-check"></i> Produtos Ativos
            </Button>
            <Button
              className={showDeleted ? 'enable' : 'disabled'}
              onClick={() => setShowDeleted(!showDeleted)}
            >
              <i className="fa-solid fa-check"></i> Produtos Inativos
            </Button>
          </div>
        </header>
        <Content>
          <Button onClick={() => setShowModal(true)}>
            <i className="fa-solid fa-plus"></i> Adicionar produto
          </Button>
          {loading && <StyledClipLoader color={variables.fontColor} />}
          {error && (
            <p className="warning">
              <i className="fa-solid fa-file-circle-xmark"></i> <br /> Houve um
              erro ao tentar carregar os dados.
            </p>
          )}
          {data.length === 0 && !loading && !error && (
            <p className="warning">
              <i className="fa-solid fa-file-circle-exclamation"></i> <br />{' '}
              Nenhum registro encontrado!
            </p>
          )}
          {data.map(produto => (
            <Card key={produto.id} produto={produto} />
          ))}
        </Content>
      </Container>
      <Modal
        title="Adicionar novo produto"
        onClose={() => setShowModal(false)}
        isOpen={showModal}
      >
        <Form />
      </Modal>
    </>
  )
}

export default MainContent
