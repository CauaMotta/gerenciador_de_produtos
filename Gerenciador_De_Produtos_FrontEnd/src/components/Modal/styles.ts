import styled from 'styled-components'

import variables from '../../styles/variables'

export const ModalContainer = styled.div`
  position: fixed;
  inset: 0;
  z-index: 999;
  display: flex;
  justify-content: center;
  align-items: center;
`

export const Overlay = styled.div`
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.5);
`

export const ModalCard = styled.div`
  position: relative;
  background: ${variables.backgroundColor};
  border-radius: 12px;
  padding: 20px;
  z-index: 1000;
  max-width: 500px;
  width: 90%;
  animation: fadeIn 0.2s ease;

  .modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 12px;

    h3 {
      margin: 0;
      font-size: 20px;
      font-weight: 500;
    }

    button {
      background: none;
      border: none;
      font-size: 20px;
      cursor: pointer;
      color: ${variables.fontColor};
    }
  }

  @keyframes fadeIn {
    from {
      opacity: 0;
      transform: translateY(-10px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }
`
