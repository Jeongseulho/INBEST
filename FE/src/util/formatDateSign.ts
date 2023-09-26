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
