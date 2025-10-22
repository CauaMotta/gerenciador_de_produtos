export default (categoria: string): string => {
  switch (categoria) {
    case 'CLOTHES':
      return 'roupas'
    case 'UNDERWEAR':
      return 'roupas_intimas'
    case 'SHOES':
      return 'calcados'
    case 'ACCESSORIES':
      return 'acessorios'
    default:
      return 'Categoria desconhecida'
  }
}
