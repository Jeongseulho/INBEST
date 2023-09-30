export function formatNumberToWon(value: number): string {
  const formatter = new Intl.NumberFormat("ko-KR");
  return `₩${formatter.format(value)}`;
}

export function formatNumberToKoreanWon(value: number): string {
  const formatter = new Intl.NumberFormat("ko-KR");
  return `${formatter.format(value)}원`;
}

export function formatNumberToDollar(value: number): string {
  const formatter = new Intl.NumberFormat("en-US");
  return `$${formatter.format(value)}`;
}

export function numberFormat(x: number) {
  return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

export function formatKoreanNumber(number: number) {
  const unitWords = ["", "만", "억", "조", "경"];
  const splitUnit = 10000;
  const splitCount = unitWords.length;
  const resultArray = [];
  let resultString = "";

  for (let i = 0; i < splitCount; i++) {
    let unitResult = (number % Math.pow(splitUnit, i + 1)) / Math.pow(splitUnit, i);
    unitResult = Math.floor(unitResult);
    if (unitResult > 0) {
      resultArray[i] = unitResult;
    }
  }

  for (let i = 0; i < resultArray.length; i++) {
    if (!resultArray[i]) continue;
    resultString = String(numberFormat(resultArray[i])) + unitWords[i] + resultString;
  }

  return resultString + "원";
}
