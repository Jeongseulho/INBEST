import group_list_screen from "../../../asset/image/group_list_screen.png";
import buy_sell_screen from "../../../asset/image/buy_sell_screen.png";
import { StepProps } from "../../../type/HomeStepProps";
import { motion } from "framer-motion";
import { useStep } from "./useStep";

const StepOne = ({ setCurStep }: StepProps) => {
  const { ref, animation, variants } = useStep(1, setCurStep);
  return (
    <motion.div ref={ref} initial="hidden" animate={animation} variants={variants} className=" h-screen top-40 z-50 ">
      <img src={group_list_screen} alt="" className=" w-1/3 relative left-[60%] top-12" />
      <img src={buy_sell_screen} alt="" className=" w-1/3  relative left-[55%] top-24" />
    </motion.div>
  );
};
export default StepOne;
