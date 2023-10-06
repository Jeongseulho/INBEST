import tutorial_screen from "../../../asset/image/tutorial_screen.png";
import company_screen from "../../../asset/image/company_screen.png";
import { StepProps } from "../../../type/HomeStepProps";
import { motion } from "framer-motion";
import { useStep } from "./useStep";

const StepTwo = ({ setCurStep }: StepProps) => {
  const { ref, animation, variants } = useStep(2, setCurStep);
  return (
    <motion.div ref={ref} initial="hidden" animate={animation} variants={variants} className=" h-screen top-40 z-50">
      <img src={company_screen} alt="" className=" w-1/3  relative left-[45%] top-12 " />
      <img src={tutorial_screen} alt="" className=" w-1/3 relative left-[65%] top-12" />
    </motion.div>
  );
};
export default StepTwo;
