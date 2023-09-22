import StepFive from "../molecules/StepFive";
import StepOne from "../molecules/StepOne";
import StepTwo from "../molecules/StepTwo";
import StepThree from "../molecules/StepThree";
import StepFour from "../molecules/StepFour";
import { StepProps } from "../../../type/HomeStepProps";

const GuideSteps = ({ setCurStep }: StepProps) => {
  return (
    <section className="bg-slate-800 text-white h-[530vh]">
      <StepOne setCurStep={setCurStep} />
      <StepTwo setCurStep={setCurStep} />
      <StepThree setCurStep={setCurStep} />
      <StepFour setCurStep={setCurStep} />
      <StepFive setCurStep={setCurStep} />
    </section>
  );
};
export default GuideSteps;
