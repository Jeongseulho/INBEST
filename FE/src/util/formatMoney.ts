export function formatNumberToWon(value: number): string {
  const formatter = new Intl.NumberFormat("ko-KR");
  return `${formatter.format(value)} 원`;
}

export function formatKoreanNumber(num: number): string {
  const units = [
    { unit: "억", value: 100000000 },
    { unit: "천", value: 10000000 },
    { unit: "백", value: 1000000 },
    { unit: "십", value: 100000 },
    { unit: "만", value: 10000 },
  ];

  // 만원 이하의 금액을 버림
  const truncated = Math.floor(num / 10000) * 10000;

  let result = "";
  let remaining = truncated;

  for (const { unit, value } of units) {
    const count = Math.floor(remaining / value);
    if (count) {
      result += `${count}${unit}`;
    }
    remaining %= value;
  }

  if (result) {
    result = result.trim() + "원";
  } else {
    result = "0원";
  }

  return result;
}
