/* eslint-disable @typescript-eslint/no-explicit-any */
import { useEffect, useState } from 'react'

import { apiRequest } from '../services/api'

type ApiResponse = {
  content: Produto[]
  totalPages: number
  totalElements: number
  number: number
  size: number
  first: boolean
  last: boolean
}

export function useApi<
  T extends ApiResponse | Produto | Statistics = ApiResponse
>(endpoint: string) {
  const [data, setData] = useState<
    T extends ApiResponse ? Produto[] : T extends Produto ? Produto : Statistics
  >(
    [] as unknown as T extends ApiResponse
      ? Produto[]
      : T extends Produto
      ? Produto
      : Statistics
  )
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    let active = true

    const fetchData = async () => {
      setData(
        [] as unknown as T extends ApiResponse
          ? Produto[]
          : T extends Produto
          ? Produto
          : Statistics
      )
      setLoading(true)
      setError(null)

      try {
        const response = await apiRequest<T>(endpoint)

        await new Promise(resolve => setTimeout(resolve, 300))

        if (active) {
          if ('content' in (response as ApiResponse)) {
            setData((response as ApiResponse).content as any)
          } else {
            setData(response as any)
          }
        }
      } catch (err: any) {
        if (active) {
          setError(err.message || 'Erro ao buscar dados')
        }
      } finally {
        if (active) {
          setLoading(false)
        }
      }
    }

    fetchData()

    return () => {
      active = false
    }
  }, [endpoint])

  return { data, loading, error }
}
