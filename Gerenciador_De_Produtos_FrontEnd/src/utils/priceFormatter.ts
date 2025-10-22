export default (price: number) => {
  return (price / 100).toFixed(2).replace('.', ',')
}
