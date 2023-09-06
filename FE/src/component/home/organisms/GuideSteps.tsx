import StepZero from "../molecules/StepZero";
import StepOne from "../molecules/StepOne";
import StepTwo from "../molecules/StepTwo";
import { StepProps } from "../../../type/HomeStepProps";

const GuideSteps = ({ setCurStep }: StepProps) => {
  return (
    <section className="bg-slate-800 text-white">
      <StepZero setCurStep={setCurStep} />
      <StepOne setCurStep={setCurStep} />
      <StepTwo setCurStep={setCurStep} />
    </section>
  );
};
export default GuideSteps;
