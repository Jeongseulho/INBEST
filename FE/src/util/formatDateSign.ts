export const getTimeAgo = (dateString: string) => {
  const currentDateLocal = new Date();
  // `dateString`를 한국 시간대로 변환
  const dateObject = new Date(dateString);
  dateObject.setTime(dateObject.getTime() + 9 * 60 * 60 * 1000); // 9시간 추가

  const timeDifference = +currentDateLocal - +dateObject;
  const seconds = Math.floor(timeDifference / 1000);
  const minutes = Math.floor(seconds / 60);
  const hours = Math.floor(minutes / 60);
  const days = Math.floor(hours / 24);
  const months = Math.floor(days / 30.44); // 평균 월 길이
  const years = Math.floor(months / 12);

  if (years >= 1) {
    return `${years}년 전`;
  } else if (months >= 1) {
    return `${months}달 전`;
  } else if (days >= 1) {
    return `${days}일 전`;
  } else if (hours >= 1) {
    return `${hours}시간 전`;
  } else if (minutes >= 1) {
    return `${minutes}분 전`;
  } else {
    return "방금 전";
  }
};

export const getKorTime = (dateString: string) => {
  const dateObject = new Date(dateString);
  dateObject.setTime(dateObject.getTime() + 9 * 60 * 60 * 1000); // 9시간 추가

  const year = dateObject.getFullYear();
  const month = String(dateObject.getMonth() + 1).padStart(2, "0"); // 월은 0부터 시작하므로 1을 더하고 두 자리로 포맷합니다.
  const day = String(dateObject.getDate()).padStart(2, "0");
  const hours = String(dateObject.getHours()).padStart(2, "0");
  const minutes = String(dateObject.getMinutes()).padStart(2, "0");

  // 'YYYY-MM-DD HH:MM' 형태로 반환합니다.
  return `${year}-${month}-${day} ${hours}:${minutes}`;
};

export function calEndDateAndProceed(
  startDate: string,
  period: number
): {
  formattedStartDate: string;
  formattedEndDate: string;
  proceed: number;
  remainDays: number;
} {
  // startDate를 Date 객체로 변환
  const startDateObj = new Date(startDate);

  // formattedStartDate 계산
  const formattedStartDate =
    startDateObj.getFullYear() +
    "." +
    String(startDateObj.getMonth() + 1).padStart(2, "0") +
    "." +
    String(startDateObj.getDate()).padStart(2, "0");

  // endDate 계산 (startDate에 period일을 더함)
  const endDateObj = new Date(startDateObj.getTime() + period * 24 * 60 * 60 * 1000);

  // formattedEndDate 계산
  const formattedEndDate =
    endDateObj.getFullYear() +
    "." +
    String(endDateObj.getMonth() + 1).padStart(2, "0") +
    "." +
    String(endDateObj.getDate()).padStart(2, "0");

  // 현재 날짜와 startDate 사이의 경과 시간을 계산하여 proceed 계산
  const currentDate = new Date();
  const elapsedTime = currentDate.getTime() - startDateObj.getTime();
  const totalPeriod = period * 24 * 60 * 60 * 1000;
  const proceed = Math.round((elapsedTime / totalPeriod) * 100); // 반올림

  // 남은 일수 계산
  const remainDays = Math.max(Math.ceil((endDateObj.getTime() - currentDate.getTime()) / (24 * 60 * 60 * 1000)), 0);

  return {
    formattedStartDate,
    formattedEndDate,
    proceed,
    remainDays,
  };
}
