import Intro from "../organisms/Intro";
import GuideSteps from "../organisms/GuideSteps";
import { useState } from "react";
import StepSide from "../organisms/StepSide";

const Home = () => {
  const [curStep, setCurStep] = useState(0);
  console.log(setCurStep);
  return (
    <main>
      <Intro />
      <StepSide curStep={curStep} />
      <GuideSteps />
    </main>
  );
};
export default Home;
