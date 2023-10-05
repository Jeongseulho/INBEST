import Intro from "../organisms/Intro";
import GuideSteps from "../organisms/GuideSteps";
import { useState } from "react";
import StepSide from "../organisms/StepSide";

const Home = () => {
  const [curStep, setCurStep] = useState(0);

  return (
    <main className=" bg-white">
      <Intro setCurStep={setCurStep} />
      <StepSide curStep={curStep} />
      <GuideSteps setCurStep={setCurStep} />
    </main>
  );
};
export default Home;
