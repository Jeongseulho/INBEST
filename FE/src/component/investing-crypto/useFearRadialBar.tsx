import { useEffect, useState } from "react";

export const useFearRadialBar = (percentage: number) => {
  const [rotation, setRotation] = useState(90);
  const [displayedPercentage, setDisplayedPercentage] = useState(0); // 숫자를 표시하는 state

  useEffect(() => {
    setRotation(90 + (percentage * 180) / 100);

    // 숫자 애니메이션
    let currentPercentage = 0;
    const incrementInterval = setInterval(() => {
      currentPercentage++;
      setDisplayedPercentage(currentPercentage);
      if (currentPercentage >= percentage) {
        clearInterval(incrementInterval);
      }
    }, 10); // 10ms 간격으로 1씩 증가. 간격을 조절하여 애니메이션의 속도를 변경할 수 있습니다.

    return () => clearInterval(incrementInterval); // 컴포넌트가 언마운트될 때 interval을 정리
  }, [percentage]);

  return { rotation, displayedPercentage };
};
