import { useEffect } from "react";
import { useAnimation } from "framer-motion";
import { useInView } from "react-intersection-observer";

export const useStepPagination = (curStep: number) => {
  const animation = useAnimation();
  const { ref } = useInView();
  const isOnView = curStep > 0 && curStep < 6;

  useEffect(() => {
    if (isOnView) {
      animation.start("visible");
    } else {
      animation.start("hidden");
    }
  }, [animation, isOnView]);

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
