import StepFive from "../molecules/StepFive";
import StepOne from "../molecules/StepOne";
import StepTwo from "../molecules/StepTwo";
import StepThree from "../molecules/StepThree";
import StepFour from "../molecules/StepFour";
import { StepProps } from "../../../type/HomeStepProps";
import { useNavigate } from "react-router-dom";

const GuideSteps = ({ setCurStep }: StepProps) => {
  const navigate = useNavigate();
  return (
    <section className="bg-slate-800 text-white h-[530vh]">
      <StepOne setCurStep={setCurStep} />
      <StepTwo setCurStep={setCurStep} />
      <StepThree setCurStep={setCurStep} />
      <StepFour setCurStep={setCurStep} />
      <StepFive setCurStep={setCurStep} />
      <h3
        onClick={() => {
          navigate("/invest");
        }}
        className=" relative top-40 left-[600px] underline cursor-pointer text-green-300 hover:text-green-500 transition-colors duration-300"
      >
        지금 바로 모의투자 하러가기
      </h3>
    </section>
  );
};
export default GuideSteps;
