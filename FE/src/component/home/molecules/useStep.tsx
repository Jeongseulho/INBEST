import { useEffect } from "react";
import { useAnimation } from "framer-motion";
import { useInView } from "react-intersection-observer";

export const useStep = (myStep: number, setCurStep: React.Dispatch<React.SetStateAction<number>>) => {
  const animation = useAnimation();
  const { ref, inView } = useInView({
    threshold: 0.6,
  });
  const isLastStep = myStep === 6;
  const isFirstStep = myStep === 0;

  useEffect(() => {
    if (inView) {
      animation.start("visible");
      setCurStep(myStep);
    } else {
      animation.start("hidden");
      setCurStep((prev) => (prev === 1 && isFirstStep ? 0 : prev));
      setCurStep((prev) => (prev === 5 && isLastStep ? 6 : prev));
    }
  }, [animation, inView, myStep, setCurStep, isFirstStep, isLastStep]);

  const variants = {
    visible: {
      opacity: 0.65,
      y: 0,
      transition: {
        duration: 0.5,
        type: "spring",
        stiffness: 100,
        damping: 20,
      },
    },
    hidden: {
      opacity: 0,
      y: 100,
      transition: { duration: 0.5 },
    },
  };

  return { ref, animation, variants };
};
