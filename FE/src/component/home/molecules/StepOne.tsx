import in_game from "../../../asset/image/in_game.png";
import lobby from "../../../asset/image/lobby.png";
import { StepProps } from "../../../type/HomeStepProps";
import { motion } from "framer-motion";
import { useStep } from "./useStep";

const StepOne = ({ setCurStep }: StepProps) => {
  const { ref, animation, variants } = useStep(1, setCurStep);
  return (
    <motion.div ref={ref} initial="hidden" animate={animation} variants={variants} className=" h-screen top-40 z-50 ">
      <img src={in_game} alt="" className=" w-1/3 relative left-[60%] top-12" />
      <img src={lobby} alt="" className=" w-1/3  relative left-[55%] top-24" />
    </motion.div>
  );
};
export default StepOne;
