import { useEffect } from "react";
import { useAnimation } from "framer-motion";
import { useInView } from "react-intersection-observer";

export const useGuideStep = (
  myStep: number,
  curStep: number,
  setCurStep: React.Dispatch<React.SetStateAction<number>>
) => {
  const animation = useAnimation();
  const { ref, inView } = useInView({
    threshold: 0.81,
  });

  useEffect(() => {
    if (inView) {
      setCurStep(myStep);
    }
  }, [inView, setCurStep, myStep]);

  useEffect(() => {
    if (myStep === curStep) {
      animation.start("visible");
    } else {
      animation.start("hidden");
    }
  }, [animation, curStep, myStep]);

  const variants = {
    visible: {
      opacity: 1,
      transition: { duration: 0.5 },
    },
    hidden: {
      opacity: 0,
      transition: { duration: 0.5 },
    },
  };

  return { ref, animation, variants };
};
