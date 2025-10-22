import styled from 'styled-components'
import variables from '../../styles/variables'
import { Button } from '../../styles'

export const Container = styled.form`
  .input {
    display: block;
    width: 100%;
    margin-bottom: 6px;
    padding: 4px 8px;
    color: ${variables.fontColor};
    font-size: 16px;
    font-weight: 400;
    background-color: transparent;
    border: none;
    border-bottom: 3px solid ${variables.secondaryColor};
    outline: none;
  }

  small {
    font-size: 12px;
    color: darkred;
  }

  .btn-group {
    display: flex;
    justify-content: center;

    ${Button} {
      margin-top: 4px;
      margin-bottom: 0;
      background-color: ${variables.secondaryColor};

      &:hover {
        opacity: 0.9;
      }
    }
  }
`

export const StyledSelectWrapper = styled.div`
  margin-bottom: 6px;

  .select-title {
    font-size: 16px;
    font-weight: 400;
    margin-bottom: 4px;
    display: block;
    color: ${variables.fontColor};
  }

  /* Control principal (área de input do react-select) */
  .custom-select__control {
    width: 100%;
    background-color: transparent;
    border: none;
    border-bottom: 3px solid ${variables.secondaryColor};
    border-radius: 0;
    box-shadow: none;
    min-height: 38px;
    padding: 0 4px;
    transition: border-color 0.3s ease;
    cursor: pointer;

    &:hover {
      border-bottom-color: ${variables.secondaryColor};
    }

    &--is-focused {
      border-bottom-color: ${variables.secondaryColor};
    }
  }

  /* Texto da opção selecionada */
  .custom-select__single-value {
    color: ${variables.fontColor};
  }

  /* Menu dropdown */
  .custom-select__menu {
    margin-top: 2px;
    border-radius: 0;
    overflow: hidden;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
    background-color: ${variables.backgroundColor};
  }

  /* Opções da lista */
  .custom-select__option {
    padding: 10px 12px;
    cursor: pointer;
    color: ${variables.fontColor};
    transition: background 0.2s ease;

    &--is-focused {
      background: ${variables.secondaryColor};
    }

    &--is-selected {
      background: ${variables.secondaryColor};
      color: ${variables.fontColor};
    }
  }

  small {
    font-size: 12px;
    color: darkred;
    margin-top: 2px;
    display: block;
  }
`
