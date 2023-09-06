import { useEffect, useRef } from "react";

export const useGuideSteps = (setCurStep: React.Dispatch<React.SetStateAction<number>>) => {
  const stepZeroRef = useRef<HTMLDivElement>(null);
  const stepOneRef = useRef<HTMLDivElement>(null);
  const stepTwoRef = useRef<HTMLDivElement>(null);
  const stepThreeRef = useRef<HTMLDivElement>(null);
  const stepFourRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const handleScroll = () => {
      const stepZeroPosition = stepZeroRef.current?.offsetTop || 0;
      const stepOnePosition = stepOneRef.current?.offsetTop || 0;
      const stepTwoPosition = stepTwoRef.current?.offsetTop || 0;
      const stepThreePosition = stepThreeRef.current?.offsetTop || 0;
      const stepFourPosition = stepFourRef.current?.offsetTop || 0;

      const currentScroll = window.scrollY;

      if (currentScroll >= stepZeroPosition && currentScroll < stepOnePosition) {
        setCurStep(0);
      } else if (currentScroll >= stepOnePosition && currentScroll < stepTwoPosition) {
        setCurStep(1);
      } else if (currentScroll >= stepTwoPosition && currentScroll < stepThreePosition) {
        setCurStep(2);
      } else if (currentScroll >= stepThreePosition && currentScroll < stepFourPosition) {
        setCurStep(3);
      } else if (currentScroll >= stepFourPosition) {
        setCurStep(4);
      }
    };

    window.addEventListener("scroll", handleScroll);

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, [setCurStep]);

  return {
    stepZeroRef,
    stepOneRef,
    stepTwoRef,
    stepThreeRef,
    stepFourRef,
  };
};
