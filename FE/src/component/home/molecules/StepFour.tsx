import compare_screen from "../../../asset/image/compare_screen.png";
import my_info_screen from "../../../asset/image/my_info_screen.png";
import { StepProps } from "../../../type/HomeStepProps";
import { motion } from "framer-motion";
import { useStep } from "./useStep";

const StepFour = ({ setCurStep }: StepProps) => {
  const { ref, animation, variants } = useStep(4, setCurStep);
  return (
    <motion.div ref={ref} initial="hidden" animate={animation} variants={variants} className=" h-screen top-40 z-50">
      <img src={compare_screen} alt="" className=" w-1/3 relative left-[60%] top-12" />
      <img src={my_info_screen} alt="" className=" w-1/3  relative left-[55%] top-24" />
    </motion.div>
  );
};
export default StepFour;
