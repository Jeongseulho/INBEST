import { useGuideSteps } from "./useGuideSteps";

interface GuideStepsProps {
  setCurStep: React.Dispatch<React.SetStateAction<number>>;
}

const GuideSteps = ({ setCurStep }: GuideStepsProps) => {
  const { stepZeroRef, stepOneRef, stepTwoRef, stepThreeRef, stepFourRef } = useGuideSteps(setCurStep);
  return (
    <section className="bg-slate-800 text-white">
      <div ref={stepZeroRef} className=" h-screen">
        STEP0
      </div>
      <div ref={stepOneRef} className=" h-screen">
        STEP1
      </div>
      <div ref={stepTwoRef} className=" h-screen">
        STEP2
      </div>
      <div ref={stepThreeRef} className=" h-screen">
        STEP3
      </div>
      <div ref={stepFourRef} className=" h-screen">
        STEP4
      </div>
    </section>
  );
};

export default GuideSteps;
