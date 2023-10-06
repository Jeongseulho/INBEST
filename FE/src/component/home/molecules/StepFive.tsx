import my_page_screen from "../../../asset/image/my_page_screen.png";
import group_rank_screen from "../../../asset/image/group_rank_screen.png";
import { StepProps } from "../../../type/HomeStepProps";
import { motion } from "framer-motion";
import { useStep } from "./useStep";

const StepFive = ({ setCurStep }: StepProps) => {
  const { ref, animation, variants } = useStep(5, setCurStep);
  return (
    <motion.div ref={ref} initial="hidden" animate={animation} variants={variants} className=" h-screen top-40 z-50">
      <img src={my_page_screen} alt="" className=" w-1/3 relative left-[60%] top-12" />
      <img src={group_rank_screen} alt="" className=" w-1/3  relative left-[55%] top-24" />
    </motion.div>
  );
};
export default StepFive;
