import temp_logo from "../../../asset/image/temp_logo.png";
import { StepProps } from "../../../type/HomeStepProps";
import { motion } from "framer-motion";
import { useStep } from "./useStep";

const StepTwo = ({ setCurStep }: StepProps) => {
  const { ref, animation, variants } = useStep(2, setCurStep);
  return (
    <motion.div
      ref={ref}
      initial="hidden"
      animate={animation}
      variants={variants}
      className=" h-screen sticky top-40 z-50"
    >
      <img src={temp_logo} alt="" className=" w-1/2 h-1/2" />
    </motion.div>
  );
};
export default StepTwo;
