import crypto_screen from "../../../asset/image/crypto_screen.png";
import abroad_screen from "../../../asset/image/abroad_screen.png";
import { StepProps } from "../../../type/HomeStepProps";
import { motion } from "framer-motion";
import { useStep } from "./useStep";

const StepThree = ({ setCurStep }: StepProps) => {
  const { ref, animation, variants } = useStep(3, setCurStep);
  return (
    <motion.div ref={ref} initial="hidden" animate={animation} variants={variants} className=" h-screen top-40 z-50">
      <img src={crypto_screen} alt="" className=" w-1/3 relative left-[60%] top-12" />
      <img src={abroad_screen} alt="" className=" w-1/3  relative left-[55%] top-24" />
    </motion.div>
  );
};
export default StepThree;
