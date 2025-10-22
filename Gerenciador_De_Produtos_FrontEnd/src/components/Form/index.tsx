import { useFormik } from 'formik'
import * as Yup from 'yup'
import { IMaskInput } from 'react-imask'
import Select from 'react-select'

import type { Option } from '../MainContent'

import { useApiMutation } from '../../hooks/useApiMutation'

import { Container, StyledSelectWrapper } from './styles'
import { Button, StyledClipLoader } from '../../styles'
import variables from '../../styles/variables'
import priceFormatter from '../../utils/priceFormatter'
import categoriaConverter from '../../utils/categoriaConverter'

const options: Option[] = [
  { value: 'roupas', label: 'Roupas' },
  { value: 'roupas_intimas', label: 'Roupas íntimas' },
  { value: 'calcados', label: 'Calçados' },
  { value: 'acessorios', label: 'Acessórios' }
]

type FormProps = {
  produto?: Produto | null
}

const Form = ({ produto }: FormProps) => {
  const { mutate, data, loading } = useApiMutation<CreateProduto, Produto>()

  const form = useFormik({
    initialValues: {
      nome: produto?.nome || '',
      preco: produto ? priceFormatter(produto.preco) : '',
      categoria: produto ? categoriaConverter(produto.categoria) : ''
    },
    validationSchema: Yup.object({
      nome: Yup.string()
        .min(3, 'O nome deve ter no mínimo 3 caracteres')
        .required('O campo de nome é obrigatório'),
      preco: Yup.string()
        .required('O campo de preço é obrigatório')
        .test('greater-than-zero', 'O preço deve ser maior que 0', value => {
          const num = parseFloat(value || '0')
          return num > 0
        }),
      categoria: Yup.string().required('O campo de categoria é obrigatório')
    }),
    onSubmit: async values => {
      const { preco, ...rest } = values

      const newPrice = Number(preco) * 100

      const payload = {
        ...rest,
        preco: newPrice
      }

      try {
        await mutate({
          method: produto ? 'PUT' : 'POST',
          endpoint: produto ? `/produtos/${produto.id}` : '/produtos',
          body: payload
        })
      } catch (err) {
        console.error('Erro ao fazer a requisição', err)
      }
    }
  })

  if (loading)
    return (
      <div className="cliploader-container">
        <StyledClipLoader color={variables.fontColor} />
      </div>
    )

  if (data) {
    window.location.reload()
    return null
  }

  return (
    <Container>
      {form.touched.nome && form.errors.nome && (
        <small className="error">* {form.errors.nome}</small>
      )}
      <input
        id="nome"
        name="nome"
        type="text"
        placeholder="Nome"
        className="input"
        value={form.values.nome}
        onChange={form.handleChange}
        onBlur={form.handleBlur}
      />

      {form.touched.preco && form.errors.preco && (
        <small className="error">* {form.errors.preco}</small>
      )}
      <IMaskInput
        id="preco"
        name="preco"
        mask={Number}
        scale={2}
        radix=","
        thousandsSeparator="."
        mapToRadix={[',']}
        padFractionalZeros={true}
        normalizeZeros={true}
        min={0}
        prefix="R$ "
        unmask={true}
        placeholder="Preço"
        className="input"
        value={form.values.preco}
        onAccept={(value: string) => form.setFieldValue('preco', value)}
        onBlur={form.handleBlur}
      />

      {form.touched.categoria && form.errors.categoria && (
        <small className="error">* {form.errors.categoria}</small>
      )}
      <StyledSelectWrapper>
        <Select
          classNamePrefix="custom-select"
          isSearchable={false}
          placeholder="Categorias"
          options={options}
          value={
            options.find(option => option.value === form.values.categoria) ||
            null
          }
          onChange={option =>
            form.setFieldValue('categoria', option?.value || null)
          }
        />
      </StyledSelectWrapper>

      <div className="btn-group">
        <Button type="button" onClick={() => form.handleSubmit()}>
          {produto ? (
            <>
              <i className="fa-solid fa-floppy-disk"></i> Salvar
            </>
          ) : (
            <>
              <i className="fa-solid fa-plus"></i> Cadastrar
            </>
          )}
        </Button>
      </div>
    </Container>
  )
}

export default Form
