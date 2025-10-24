export default (
  selectedOption: Option | null,
  priceSort: boolean,
  showDeleted?: boolean
) => {
  const query = new URLSearchParams()

  if (selectedOption && selectedOption.value !== 'todas') {
    query.append('categoria', selectedOption.value)
  }

  query.append('sort', `preco,${priceSort ? 'desc' : 'asc'}`)

  const basePath = `/produtos${showDeleted ? '/apagados' : ''}`

  return `${basePath}?${query.toString()}`
}
