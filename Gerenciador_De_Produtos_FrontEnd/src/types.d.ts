declare type Produto = {
  id: number
  nome: string
  preco: number
  categoria: string
  createdAt: string
  updatedAt: string
  deletedAt: string
}

declare type CreateProduto = {
  nome: string
  preco: number
  categoria: string
}

declare type Statistics = {
  qntProdutos: number
  precoMedio: number
}
