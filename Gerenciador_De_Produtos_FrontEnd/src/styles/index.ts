import styled, { createGlobalStyle, keyframes } from 'styled-components'
import { ClipLoader } from 'react-spinners'

import variables from './variables'

export const GlobalStyle = createGlobalStyle`
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        list-style: none;

        font-family: ${variables.fontFamily};
    }

    body {
        background-color: ${variables.backgroundColor};
        color: ${variables.fontColor};
    }

    .cliploader-container {
      width: 100%;
      display: flex;
      justify-content: center;
    }
`

const spin = keyframes`
  to {
    transform: rotate(360deg);
  }
`

export const StyledClipLoader = styled(ClipLoader)`
  border-width: 4px !important;
  animation: ${spin} 1s 0s linear infinite !important;
`

export const Button = styled.button`
  padding: 6px 16px;
  background-color: transparent;
  border: none;

  font-size: 16px;
  color: ${variables.fontColor};

  cursor: pointer;

  &.disabled {
    i {
      visibility: hidden;
    }
  }

  &.enable {
    background-color: ${variables.secondaryColor};

    i {
      visibility: visible;
    }
  }
`

export const Line = styled.hr`
  height: 2px;
  background-color: ${variables.fontColor};
  border: none;
  margin: 8px 0;
`
