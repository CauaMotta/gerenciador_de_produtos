import styled from 'styled-components'

import variables from '../../styles/variables'
import { Button } from '../../styles'

export const Container = styled.div`
  padding: 8px;
  margin-bottom: 16px;
  max-width: 618px;
  width: 100%;

  background-color: ${variables.secondaryColor};

  display: flex;
  flex-direction: column;

  .card-content {
    display: flex;
    align-items: center;
    gap: 8px;

    .card-icon {
      width: 64px;
      height: 64px;
      display: flex;
      justify-content: center;
      align-items: center;

      span {
        font-size: 28px;
        opacity: 0.7;
      }
    }

    .card-info {
      flex: 1;
      display: flex;
      justify-content: space-between;
      align-items: center;

      .card-title {
        font-size: 18px;
        font-weight: 500;
      }

      .info-2 {
        text-align: right;
        margin-right: 8px;
      }
    }
  }

  .btn-group {
    width: 100%;
    display: flex;
    justify-content: flex-end;

    ${Button} {
      margin: 0;
      opacity: 0.7;
      font-size: 14px;

      &:hover {
        opacity: 1;
      }
    }
  }
`

export const ModalContent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;

  .delete-info {
    background-color: transparent;
  }

  .modal-btns {
    display: flex;
    justify-content: center;
    gap: 16px;

    ${Button} {
      min-width: 120px;
      margin: 0;
    }
  }
`
