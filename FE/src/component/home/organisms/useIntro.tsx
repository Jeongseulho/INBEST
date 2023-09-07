import { useEffect } from "react";
import { useInView } from "react-intersection-observer";

export const useIntro = (setCurStep: React.Dispatch<React.SetStateAction<number>>) => {
  const { ref, inView } = useInView({
    threshold: 0.1,
  });

  useEffect(() => {
    if (inView) {
      setCurStep(0);
    }
  }, [inView, setCurStep]);

  return { ref };
};
