export function fluctuationStringToNumber(str: string): number {
  return Number(str.replace("%", ""));
}
