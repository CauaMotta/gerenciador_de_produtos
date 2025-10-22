import styled from 'styled-components'

import variables from '../../styles/variables'
import { Button } from '../../styles'

export const Container = styled.div`
  max-width: 1200px;
  margin: 0 auto;
  padding-block: 40px;

  .statistics {
    display: flex;
    justify-content: center;
    gap: 40px;
  }

  .menu {
    margin-top: 16px;
    display: flex;
    justify-content: space-around;
  }
`

export const Title = styled.h1`
  font-size: 24px;
  text-align: center;
`

export const StyledSelectWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;

  .select-title {
    font-size: 16px;
    font-weight: 400;
  }

  .custom-select__control {
    min-width: 256px;
    background-color: transparent;
    border-radius: 0;
    border: 1px solid transparent;
    min-height: 38px;
    box-shadow: none;
    transition: border 0.3s ease;
    cursor: pointer;
  }

  .custom-select__single-value {
    color: ${variables.fontColor};
  }

  .custom-select__control--is-focused {
    border-color: transparent;

    &:hover {
      border-color: ${variables.secondaryColor};
    }
  }

  .custom-select__menu {
    margin-top: 0;
    border-radius: 0;
    overflow: hidden;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    background-color: ${variables.backgroundColor};
  }

  .custom-select__option {
    padding: 10px 12px;
    cursor: pointer;
    transition: background 0.2s ease;
  }

  .custom-select__option--is-focused {
    background: ${variables.secondaryColor};
  }

  .custom-select__option--is-selected {
    background: ${variables.secondaryColor};
    color: ${variables.fontColor};
  }
`

export const Content = styled.main`
  margin-top: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  ${Button} {
    margin-bottom: 32px;
    background-color: ${variables.secondaryColor};
    &:hover {
      opacity: 0.9;
    }
  }

  .warning {
    text-align: center;
    font-size: 16px;
    opacity: 0.7;

    i {
      font-size: 24px;
    }
  }
`
