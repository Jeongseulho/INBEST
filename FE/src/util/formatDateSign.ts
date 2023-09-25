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
