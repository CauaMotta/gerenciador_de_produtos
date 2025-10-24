import { type JSX, useEffect } from 'react'

import { ModalContainer, ModalCard, Overlay } from './styles'

type Props = {
  isOpen: boolean
  onClose: () => void
  title?: string
  children: JSX.Element
}

const Modal = ({ isOpen, onClose, title, children }: Props) => {
  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      if (e.key === 'Escape') onClose()
    }

    if (isOpen) {
      document.body.style.overflow = 'hidden'
      window.addEventListener('keydown', handleKeyDown)
    } else {
      document.body.style.overflow = 'auto'
    }

    return () => {
      window.removeEventListener('keydown', handleKeyDown)
      document.body.style.overflow = 'auto'
    }
  }, [isOpen, onClose])

  if (!isOpen) return null

  return (
    <ModalContainer>
      <Overlay onClick={onClose} />
      <ModalCard>
        {title && (
          <header className="modal-header">
            <h3>{title}</h3>
            <button onClick={onClose} aria-label="Fechar">
              <i className="fa-solid fa-xmark"></i>
            </button>
          </header>
        )}
        <div className="modal-content">{children}</div>
      </ModalCard>
    </ModalContainer>
  )
}

export default Modal
