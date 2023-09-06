import { useEffect } from "react";
import { useAnimation } from "framer-motion";
import { useInView } from "react-intersection-observer";

export const useStepAnimation = (myStep: number, setCurStep: React.Dispatch<React.SetStateAction<number>>) => {
  const animation = useAnimation();
  const { ref, inView } = useInView({
    threshold: 0.81,
  });

  useEffect(() => {
    if (inView) {
      animation.start("visible");
      setCurStep(myStep);
    } else {
      animation.start("hidden");
    }
  }, [animation, inView, myStep, setCurStep]);

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
