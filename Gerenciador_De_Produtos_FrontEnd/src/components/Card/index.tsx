import { useState } from 'react'

import Modal from '../Modal'

import dateFormatter from '../../utils/dateFormatter'
import priceFormatter from '../../utils/priceFormatter'

import { useApiMutation } from '../../hooks/useApiMutation'

import { Button, StyledClipLoader } from '../../styles'
import { ModalContent, Container } from './styles'
import variables from '../../styles/variables'
import Form from '../Form'

type Props = {
  produto: Produto
}

const Card = ({ produto }: Props) => {
  const { mutate, data, loading, error } = useApiMutation<Produto, Produto>()
  const [showDeleteModal, setShowDeleteModal] = useState(false)
  const [showEditModal, setShowEditModal] = useState(false)

  const deleteProduct = async () => {
    const result = await mutate({
      method: 'DELETE',
      endpoint: `/produtos/${produto.id}`
    })

    if (result) window.location.reload()
  }

  return (
    <>
      <Container>
        <div className="card-content">
          <div className="card-icon">
            <span>
              <i className="fa-solid fa-shirt"></i>
            </span>
          </div>
          <div className="card-info">
            <div className="info-1">
              <h2 className="card-title">
                <span className="card-id">{produto.id}</span> - {produto.nome}
              </h2>
              <p>{produto.categoria}</p>
            </div>
            <div className="info-2">
              <p>R$ {priceFormatter(produto.preco)}</p>
              {produto.deletedAt === null && (
                <p>Criado em {dateFormatter(produto.createdAt)}</p>
              )}
              {produto.deletedAt !== null && (
                <p>Apagado em {dateFormatter(produto.deletedAt)}</p>
              )}
            </div>
          </div>
        </div>
        {produto.deletedAt === null && (
          <div className="btn-group">
            <Button onClick={() => setShowDeleteModal(true)}>
              <i className="fa-solid fa-trash"></i> Remover
            </Button>
            <Button onClick={() => setShowEditModal(true)}>
              <i className="fa-solid fa-pen-to-square"></i> Editar
            </Button>
          </div>
        )}
      </Container>

      <Modal
        title="Deseja remover este produto?"
        onClose={() => setShowDeleteModal(false)}
        isOpen={showDeleteModal}
      >
        <ModalContent>
          {loading && (
            <div className="cliploader-container">
              <StyledClipLoader color={variables.fontColor} />
            </div>
          )}
          {!loading && !data && (
            <>
              <div>
                <Container className="delete-info">
                  <div className="card-content">
                    <div className="card-icon">
                      <span>
                        <i className="fa-solid fa-shirt"></i>
                      </span>
                    </div>
                    <div className="card-info">
                      <div className="info-1">
                        <h2 className="card-title">
                          <span className="card-id">{produto.id}</span> -{' '}
                          {produto.nome}
                        </h2>
                        <p>{produto.categoria}</p>
                      </div>
                      <div className="info-2">
                        <p>R$ {priceFormatter(produto.preco)}</p>
                      </div>
                    </div>
                  </div>
                </Container>
              </div>

              <div className="modal-btns">
                <Button onClick={() => deleteProduct()}>Sim</Button>
                <Button onClick={() => setShowDeleteModal(false)}>Não</Button>
              </div>
            </>
          )}
        </ModalContent>
      </Modal>

      <Modal
        title="Editar produto"
        onClose={() => setShowEditModal(false)}
        isOpen={showEditModal}
      >
        <ModalContent>
          {loading && (
            <div className="cliploader-container">
              <StyledClipLoader color={variables.fontColor} />
            </div>
          )}
          {!loading && !data && (
            <>
              <Form produto={produto} />
            </>
          )}
        </ModalContent>
      </Modal>
    </>
  )
}

export default Card
