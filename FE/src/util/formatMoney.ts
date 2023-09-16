export function formatNumberToWon(value: number): string {
  const formatter = new Intl.NumberFormat("ko-KR");
  return `${formatter.format(value)} 원`;
}
